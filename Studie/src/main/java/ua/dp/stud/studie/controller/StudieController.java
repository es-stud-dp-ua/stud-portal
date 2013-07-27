package ua.dp.stud.studie.controller;

/**
 *
 * @author Olga Ryzol
 */

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.service.StudieService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "view")
//TODO: COMMON LOGIC SHOULD BE MOVED TO PRIVATE HELPER METHOD
public class StudieController {

    private static final String MAIN_IMAGE_MOCK_URL = "images/no-logo-study.jpg";
    private static final String STR_FAIL = "fail";
    private static final String STR_NO_IMAGE = "no images";
    private static final String STR_EXEPT = "Exception ";
    private static final String MAIN_IMAGE = "mainImage";
    private static final String BUTTON_ID = "buttonId";
    private static final Logger LOG = Logger.getLogger(StudieController.class.getName());

    @Autowired
    @Qualifier(value = "studieService")
    private StudieService service;

    public void setService(StudieService service) {
        this.service = service;
    }

    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        Integer buttonId;
        if (request.getParameter(BUTTON_ID) == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter(BUTTON_ID));
        }
        model.setViewName("viewAll");
        Collection<Studie> studie = service.getAllStudies();
        model.addObject("studie", studie);
        model.addObject(BUTTON_ID, buttonId);
        return model;
    }

    @RenderMapping(params = "studieID")
    public ModelAndView showSelectedSrudie(RenderRequest request, RenderResponse response) {
        int studieID = Integer.valueOf(request.getParameter("studieID"));
        Integer buttonId = 0;
        if (request.getParameter(BUTTON_ID) == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter(BUTTON_ID));
        }
        Studie studie = service.getStudieById(studieID);
        ImageImpl mImage = studie.getMainImage();
        String mainImageUrl;
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, studie);
        }
        Collection<ImageImpl> additionalImages = studie.getAdditionalImages();
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingle");
        model.addObject("studie", studie);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        model.addObject(BUTTON_ID, buttonId);
        return model;
    }

    @ModelAttribute(value = "study")
    public Studie getCommandObject() {
        return new Studie();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("mainImage");
    }

    private static final List<String> ACCCRED_LEVELS;
    private static final List<String> TRAINING_FORMS;
    private static final List<String> STATUS;
    private static final List<String> LVL_QUALIF;
    private static final List<String> YEARS;
    private static final List<String> DOC;
    private static final Map<String, List<String>> FORM_PARAM_MAP;

    static {
        ACCCRED_LEVELS = Arrays.asList("I", "II", "III", "IV");
        TRAINING_FORMS = Arrays.asList("Дневная", "Заочная");
        STATUS = Arrays.asList("Государственный", "Частный");
        LVL_QUALIF = Arrays.asList("Бакалавр", "Специалист", "Магистр");
        DOC = Arrays.asList("док. гос. образца");
        YEARS = new ArrayList<String>();
        for (Integer i = 1850; i < DateTime.now().getYear(); i++) {
            YEARS.add(i.toString());
        }
        FORM_PARAM_MAP = new HashMap<String, List<String>>();
        FORM_PARAM_MAP.put("lvlAccredList", ACCCRED_LEVELS);
        FORM_PARAM_MAP.put("trainigFormsList", TRAINING_FORMS);
        FORM_PARAM_MAP.put("statusList", STATUS);
        FORM_PARAM_MAP.put("lvlQualifList", LVL_QUALIF);
        FORM_PARAM_MAP.put("yearsList", YEARS);
        FORM_PARAM_MAP.put("docList", DOC);
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddStuds(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addStudie");
        model.addAllObjects(FORM_PARAM_MAP);
        return model;
    }

    private Boolean UpdateStudy(Studie newStudie, CommonsMultipartFile mainImage, CommonsMultipartFile[] images,
                                ActionResponse actionResponse) {
        boolean successUpload = true;
        try {
            if (mainImage.getSize() > 0) {
                imageService.saveMainImage(mainImage, newStudie);
            }
            if (images != null && images.length > 0) {
                for (CommonsMultipartFile file : images) {
                    imageService.saveAdditionalImages(file, newStudie);
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            actionResponse.setRenderParameter(STR_EXEPT, "error");
            successUpload = false;
        }
        return successUpload;
    }

    @ActionMapping(value = "addStudie")
    public void addStudie(@ModelAttribute("study") @Valid Studie studie,
                          BindingResult bindingResult,
                          ActionRequest actionRequest,
                          ActionResponse actionResponse,
                          SessionStatus sessionStatus,
                          @RequestParam("mainImage") CommonsMultipartFile mainImage,
                          @RequestParam("images") CommonsMultipartFile[] images) {
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    System.out.println(fieldError.toString());
                }

                if (object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;

                    System.out.println(objectError.toString());
                }
            }
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
            return;
        }
        //CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
        //        Integer.parseInt(actionRequest.getParameter("l")),
        //        Integer.parseInt(actionRequest.getParameter("w")),
        //        Integer.parseInt(actionRequest.getParameter("h")));
        if (UpdateStudy(studie, mainImage, images, actionResponse)) {
            service.addStudie(studie);
            actionResponse.setRenderParameter("studieID", Integer.toString(studie.getId()));
            sessionStatus.setComplete();
        }
    }

    @ActionMapping(value = "editStudie")
    public void editStudie(@ModelAttribute("study") @Valid Studie studie,
                           BindingResult bindingResult,
                           ActionRequest actionRequest,
                           ActionResponse actionResponse,
                           SessionStatus sessionStatus,
                           @RequestParam("mainImage") CommonsMultipartFile mainImage,
                           @RequestParam("images") CommonsMultipartFile[] images) {
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    System.out.println(fieldError.toString());
                }

                if (object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;

                    System.out.println(objectError.toString());
                }
            }
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        System.out.println(studie.getId());
        /*Studie oldStudy = service.getStudieById(studie.getId());
        oldStudy.setAccreditacion();
        oldStudy.setAdress();
        oldStudy.setCity();
        oldStudy.setCountOfCandidates();
        oldStudy.setCountOfProfessors();
        oldStudy.setCountOfStudents();
        oldStudy.setCountOfTeachers();
        oldStudy.setEnrollees();
        oldStudy.setFormOfTraining();
        oldStudy.setFreeTrainig();
        oldStudy.setHostel();
        oldStudy.setMilitaryDepartment();
        oldStudy.setOnGraduation();
        oldStudy.setPaidTrainig();
        oldStudy.set*/
        if (!mainImage.getOriginalFilename().equals("")) {
            //CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
            //        Integer.parseInt(actionRequest.getParameter("l")),
            //        Integer.parseInt(actionRequest.getParameter("w")),
            //        Integer.parseInt(actionRequest.getParameter("h")));
        }
        if (UpdateStudy(studie, mainImage, images, actionResponse)) {
            service.saveOrUpdate(studie);
            actionResponse.setRenderParameter("studieID", Integer.toString(studie.getId()));
            sessionStatus.setComplete();
        }
    }

    @RenderMapping(params = "mode=delImage")
    public ModelAndView delImage(RenderRequest request, RenderResponse response) {
        long imageID = Long.valueOf(request.getParameter("imageId"));
        ImageImpl image = service.getImageById(imageID);
        imageService.deleteImage(image, image.getBase());
        service.deleteImage(image);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("editStudie");
        int studieID = Integer.valueOf(request.getParameter("studieId"));
        Studie studie = service.getStudieById(studieID);
        ImageImpl mImage = studie.getMainImage();
        String mainImageUrl;
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, studie);
        }
        Collection<ImageImpl> additionalImages = studie.getAdditionalImages();
        model.getModelMap().addAttribute("study", studie);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        model.addAllObjects(FORM_PARAM_MAP);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteOrganisation(RenderRequest request, RenderResponse response) {
        int studieID = Integer.valueOf(request.getParameter("studieId"));
        Studie studie = service.getStudieById(studieID);
        imageService.deleteDirectory(studie);
        service.deleteStudie(studie);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddStuds(request, response);
        SessionErrors.add(request, request.getParameter(STR_FAIL));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddStuds(request, response);
        model.addObject(STR_EXEPT, request.getParameter(STR_EXEPT));
        return model;
    }
}
