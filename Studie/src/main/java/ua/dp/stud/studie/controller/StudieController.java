package ua.dp.stud.studie.controller;

/**
 *
 * @author Olga Ryzol
 * @author Vladislav Pikus
 */

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.model.Specialties;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.service.FacultiesService;
import ua.dp.stud.studie.service.StudieService;
import ua.dp.stud.studie.validation.StudyValidator;

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
    private static final String STUDY_ID = "studieID";
    private static final String STUDY = "study";
    private static final Logger LOG = Logger.getLogger(StudieController.class.getName());

    @Autowired
    @Qualifier(value = "studieService")
    private StudieService studieService;

    public void setStudieService(StudieService studieService) {
        this.studieService = studieService;
    }

    @Autowired
    private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    @Qualifier(value = "facultiesService")
    private FacultiesService facultiesService;

    public void setService(FacultiesService facultiesService) {
        this.facultiesService = facultiesService;
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
        Collection<Studie> studie = studieService.getAllStudies();
        model.addObject("studie", studie);
        model.addObject(BUTTON_ID, buttonId);
        return model;
    }

    @RenderMapping(params = STUDY_ID)
    public ModelAndView showSelectedSrudie(RenderRequest request, RenderResponse response) {
        int studieID = Integer.valueOf(request.getParameter(STUDY_ID));
        Integer buttonId = 0;
        if (request.getParameter(BUTTON_ID) == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter(BUTTON_ID));
        }
        Studie studie = studieService.getStudieById(studieID);
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

    @ModelAttribute(value = STUDY)
    public Studie getCommandObject() {
        return new Studie();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(MAIN_IMAGE);
    }

    private static final List<String> ACCCRED_LEVELS;
    private static final List<String> YEARS;
    private Map<String, List<String>> formParamMap;
    private static final Integer MIN_AGE = 1850;
    private static final Integer THREE = 3;

    static {
        ACCCRED_LEVELS = Arrays.asList("I", "II", "III", "IV");
        YEARS = new ArrayList<String>();
        for (Integer i = MIN_AGE; i < DateTime.now().getYear(); i++) {
            YEARS.add(i.toString());
        }
    }

    private void setMap(RenderRequest request) {
        List<String> trainingForms = new ArrayList<String>();
        List<String> status = new ArrayList<String>();
        List<String> lvlQualif = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            trainingForms.add(messageSource.getMessage("form.TRAINING_FORMS" + i, null, request.getLocale()));
            status.add(messageSource.getMessage("form.STATUS" + i, null, request.getLocale()));
        }
        for (int i = 0; i < THREE; i++) {
            lvlQualif.add(messageSource.getMessage("form.LVL_QUALIF" + i, null, request.getLocale()));
        }
        List<String> doc = Arrays.asList(messageSource.getMessage("form.DOC", null, request.getLocale()));
        formParamMap = new HashMap<String, List<String>>();
        formParamMap.put("lvlAccredList", ACCCRED_LEVELS);
        formParamMap.put("trainigFormsList", trainingForms);
        formParamMap.put("statusList", status);
        formParamMap.put("lvlQualifList", lvlQualif);
        formParamMap.put("yearsList", YEARS);
        formParamMap.put("docList", doc);
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddStuds(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addStudie");
        setMap(request);
        model.addAllObjects(formParamMap);
        return model;
    }

    private Boolean updateStudy(Studie newStudie, CommonsMultipartFile mainImage, CommonsMultipartFile[] images,
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
        if (newStudie.getFaculties()!=null){
            for (Faculties faculties : newStudie.getFaculties()) {
                if (faculties.getSpecialties()!=null){
                    for (Specialties specialties : faculties.getSpecialties()) {
                        specialties.setNameOfFaculties(faculties);
                    }
                }
            }
        }
        return successUpload;
    }

    @Autowired
    @Qualifier(value = "studyValidator")
    private StudyValidator studyValidator;

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

    @ActionMapping(value = "addStudie")
    public void addStudie(@ModelAttribute(STUDY) @Valid Studie studie,
                          BindingResult bindingResult,
                          ActionRequest actionRequest,
                          ActionResponse actionResponse,
                          SessionStatus sessionStatus,
                          @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                          @RequestParam("images") CommonsMultipartFile[] images) {
        studyValidator.validate(studie, bindingResult);
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        if ("".equals(mainImage.getOriginalFilename())) {
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
            return;
        }
        CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                Integer.parseInt(actionRequest.getParameter("l")),
                Integer.parseInt(actionRequest.getParameter("w")),
                Integer.parseInt(actionRequest.getParameter("h")));
        if (updateStudy(studie, f, images, actionResponse)) {
            studieService.addStudie(studie);
            actionResponse.setRenderParameter(STUDY_ID, Integer.toString(studie.getId()));
            sessionStatus.setComplete();
        }
    }

    @ActionMapping(value = "editStudie")
    public void editStudie(@ModelAttribute("study") @Valid Studie studie,
                           BindingResult bindingResult,
                           ActionRequest actionRequest,
                           ActionResponse actionResponse,
                           SessionStatus sessionStatus,
                           @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                           @RequestParam("images") CommonsMultipartFile[] images) {
        studyValidator.validate(studie, bindingResult);
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        Studie oldStudy = studieService.getStudieById(studie.getId());
        studie.setMainImage(oldStudy.getMainImage());
        studie.setAdditionalImages(oldStudy.getAdditionalImages());
        studie.setYearMonthUniqueFolder(oldStudy.getYearMonthUniqueFolder());
        if (!"".equals(mainImage.getOriginalFilename())) {
            mainImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                    Integer.parseInt(actionRequest.getParameter("l")),
                    Integer.parseInt(actionRequest.getParameter("w")),
                    Integer.parseInt(actionRequest.getParameter("h")));
        }
        if (updateStudy(studie, mainImage, images, actionResponse)) {
            facultiesService.deleteList(oldStudy.getFaculties());
            studieService.updateStudie(studie);
            actionResponse.setRenderParameter(STUDY_ID, Integer.toString(studie.getId()));
            sessionStatus.setComplete();
        }
    }

    @RenderMapping(params = {"mode=delImage", STUDY_ID})
    public ModelAndView delImage(RenderRequest request, RenderResponse response) {
        long imageID = Long.valueOf(request.getParameter("imageId"));
        ImageImpl image = studieService.getImageById(imageID);
        imageService.deleteImage(image, image.getBase());
        studieService.deleteImage(image);
        return showSelectedSrudie(request, response);
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
    	if (!request.isUserInRole("Administrator")){
    		return showView(request,response);
    	}
        ModelAndView model = new ModelAndView("editStudie");
        int studieID = Integer.valueOf(request.getParameter("studieId"));
        Studie studie = studieService.getStudieById(studieID);
        ImageImpl mImage = studie.getMainImage();
        String mainImageUrl;
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, studie);
        }
        Collection<ImageImpl> additionalImages = studie.getAdditionalImages();
        model.getModelMap().addAttribute(STUDY, studie);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        setMap(request);
        model.addAllObjects(formParamMap);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteOrganisation(RenderRequest request, RenderResponse response) {
        int studieID = Integer.valueOf(request.getParameter("studieId"));
        Studie studie = studieService.getStudieById(studieID);
        imageService.deleteDirectory(studie);
        studieService.deleteStudie(studie);
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
