package ua.dp.stud.studie.controller;

/**
 *
 * @author Olga Ryzol
 */

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Studie;
import ua.dp.stud.StudPortalLib.service.StudieService;
import ua.dp.stud.StudPortalLib.util.ImageService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
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
    private static final String DAY_FACULTY = "facultetDnevn";
    private static final String CORRESP_FACULTY = "facultetZaoch";
    private static final String ADRESS = "adress";
    private static final int MINTITLESYMBOLS = 4;
    private static final int MINTEXTSYMBOLS = 100;
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
        //todo: ternary operator
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
        //todo: ternary operator
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
    //todo: remove unused parameters
    private boolean updateCommunityFields(CommonsMultipartFile mainImage, CommonsMultipartFile[] images,
                                          String frmTopic, String frmText, String frmFaculteDnevn, String frmFacultetZaoch, String adress,
                                          ActionResponse actionResponse, Studie somestudie)
            throws IOException, SystemException, PortalException {
        boolean successUpload = true;
        if (frmTopic.length() < MINTITLESYMBOLS) {
            actionResponse.setRenderParameter(STR_FAIL, STR_FAIL);
            return false;
        }
        somestudie.setTitle(frmTopic);
        if (frmText.length() < MINTEXTSYMBOLS) {
            actionResponse.setRenderParameter(STR_FAIL, STR_FAIL);
            return false;
        }
        somestudie.setText(frmText);
        somestudie.setAdress(adress);
        try {
            if (mainImage.getSize() > 0) {
                try {
                    imageService.saveMainImage(mainImage, somestudie);
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, STR_EXEPT, ex);
                    successUpload = false;
                }
            }
            if (images != null && images.length > 0) {

                for (CommonsMultipartFile file : images) {
                    imageService.saveAdditionalImages(file, somestudie);
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(STR_EXEPT, sw.toString());
            successUpload = false;
        }
        if (successUpload) {
            return true;
        }
        return false;
    }

    @ActionMapping(value = "addStudie")
    public void addStudie(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                          @RequestParam("images") CommonsMultipartFile[] images,
                          ActionRequest actionRequest,
                          ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
            return;
        }
        Studie studie = new Studie();
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        String[] facultetDnevn = actionRequest.getParameterValues(DAY_FACULTY);
        String[] facultetZaoch = actionRequest.getParameterValues(CORRESP_FACULTY);
        String adress = actionRequest.getParameter(ADRESS);
        String facultetZ = "", facultetD = "";
        for (String strD : facultetDnevn) {
            if (!"".equals(strD)) {
                //todo: do you store all facultets in one string? - yes...
                StringBuilder sb = new StringBuilder();
                facultetD = sb.append(strD).append(";").toString();
            }
        }
        for (String strZ : facultetZaoch) {
            if (!"".equals(strZ)) {
                StringBuilder sb = new StringBuilder();
                facultetZ = sb.append(strZ).append(";").toString();
            }
        }
        facultetD = facultetD.substring(0, facultetD.length() - 1);
        facultetZ = facultetZ.substring(0, facultetZ.length() - 1);
        CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                Integer.parseInt(actionRequest.getParameter("l")),
                Integer.parseInt(actionRequest.getParameter("w")),
                Integer.parseInt(actionRequest.getParameter("h")));
        try {
            if (updateCommunityFields(f, images, topic.trim(), text.trim(), facultetD, facultetZ, adress, actionResponse, studie)) {
                service.addStudie(studie);
                actionResponse.setRenderParameter("orgsID", Integer.toString(studie.getId()));
                sessionStatus.setComplete();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(STR_EXEPT, sw.toString());
        }
    }

    @ActionMapping(value = "editStudie")
    public void editStudie(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                           @RequestParam("images") CommonsMultipartFile[] images,
                           ActionRequest actionRequest,
                           ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
        int organisationID = Integer.valueOf(actionRequest.getParameter("studieId"));
        Studie studie = service.getStudieById(organisationID);
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        String[] facultetDnevn = actionRequest.getParameterValues(DAY_FACULTY);
        String[] facultetZaoch = actionRequest.getParameterValues(CORRESP_FACULTY);
        String adress = actionRequest.getParameter(ADRESS);
        String facultetZ = "", facultetD = "";
        for (String strD : facultetDnevn) {
            if (!"".equals(strD)) {
                facultetD = facultetD.concat(strD);
                facultetD = facultetD.concat(";");
            }
        }
        for (String strZ : facultetZaoch) {
            if (!"".equals(strZ)) {
                facultetZ = facultetZ.concat(strZ);
                facultetZ = facultetZ.concat(";");
            }
        }
        facultetD = facultetD.substring(0, facultetD.length() - 1);
        facultetZ = facultetZ.substring(0, facultetZ.length() - 1);
        if (updateCommunityFields(mainImage, images, topic.trim(), text.trim(), facultetD, facultetZ, adress, actionResponse, studie)) {
            try {
                service.updateStudie(studie);
                sessionStatus.setComplete();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, STR_EXEPT, ex);
                StringWriter sw = new StringWriter();
                actionResponse.setRenderParameter(STR_EXEPT, sw.toString());
            }
        }

    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddStuds(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("addStudie");
        return model;
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
        ModelAndView model = new ModelAndView();
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
        model.setViewName("editStudie");
        model.addObject("studie", studie);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
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
