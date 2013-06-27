/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.controller;

/**
 *
 * @author Olga Ryzol
 */
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
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

import javax.imageio.ImageIO;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;

@Controller
@RequestMapping(value = "view")
//TODO: COMMON LOGIC SHOULD BE MOVED TO PRIVATE HELPER METHOD
public class StudieController {

    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static String strFail = "fail";
    private static String strNoImage = "no-images";
    private static String strExept = "exception";
    private static String mainImagePar = "mainImage";
    //todo: remove this variable
    private Integer buttonId = 0;
    private static final int MINTITLESYMBOLS = 4;
    private static final int MINTEXTSYMBOLS = 100;
    @Autowired
    @Qualifier(value = "StudieService")
    //todo:change variable name
    private StudieService service;
    //Declare our service

    public void setService(StudieService service) {
        this.service = service;
    }

    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        if (request.getParameter("buttonId") == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter("buttonId"));
        }
        //todo: add view name as constructor arg to ModelAndView
        model.setViewName("viewAll");
        Collection<Studie> studie = service.getAllStudies();


        model.addObject("studie", studie);
        model.addObject("buttonId", buttonId);
        return model;
    }

    @RenderMapping(params = "studieID")
    public ModelAndView showSelectedSrudie(RenderRequest request, RenderResponse response) {

        int studieID = Integer.valueOf(request.getParameter("studieID"));
        Studie studie = service.getStudieById(studieID);
        ImageService imageService = new ImageService();
        ImageImpl mImage = studie.getMainImage();
        String mainImageUrl;
        String facultetD = studie.getFacultetsDnev();
        String facultetZ = studie.getFacultetsZaoch();
        String adress = studie.getAdress();

        //todo: use ArrayList instead of array
        String[] facultetDnevn = facultetD.split(";");
        String[] facultetZaoch = facultetZ.split(";");
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, studie);
        }
        Collection<ImageImpl> additionalImages = studie.getAdditionalImages();

        //todo: add view name as constructor arg to ModelAndView
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingle");
        model.addObject("studie", studie);
        model.addObject(mainImagePar, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        model.addObject("facultetDnevn", facultetDnevn);
        model.addObject("facultetZaoch", facultetZaoch);
        model.addObject("adress", adress);
        model.addObject("buttonId", buttonId);

        return model;
    }
    //todo: use @RequestParams only in action and render methods
    //todo: remove actionResponse from params
    private boolean updateCommunityFields(@RequestParam("mainImage") CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            String frmTopic, String frmText, String frmFaculteDnevn, String frmFacultetZaoch, String adress,
            ActionResponse actionResponse, String strFail, String strNoImage, Studie somestudie)
            throws IOException, SystemException, PortalException {
        boolean successUpload = true;
        ImageService imageService = new ImageService();
        //get topic from views
        if (frmTopic.length() < MINTITLESYMBOLS) {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        somestudie.setTitle(frmTopic);
        //get text from views
        if (frmText.length() < MINTEXTSYMBOLS) {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        somestudie.setText(frmText);
        somestudie.setFacultetsDnev(frmFaculteDnevn);
        somestudie.setFacultetsZaoch(frmFacultetZaoch);
        somestudie.setAdress(adress);

        //main image uploading
        //todo: ONE TRY BLOCK
        try {
            if (mainImage.getSize() > 0) {
                try {
                    imageService.saveMainImage(mainImage, somestudie);
                } catch (Exception ex) {
                    successUpload = false;
                }
            }
            //image collection uploading
            if (images != null && images.length > 0) {
                try {
                    for (CommonsMultipartFile file : images) {
                        imageService.saveAdditionalImages(file, somestudie);
                    }
                } catch (Exception ex) {
                    successUpload = false;
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(strExept, sw.toString());
        }
        //success upload message
        if (successUpload) {
            return true;
        }//todo: redundant else-block
        else {
            actionResponse.setRenderParameter(strFail, strNoImage);
        }
        return false;
    }

    //todo: use try ... catch block and logging
    @ActionMapping(value = "addStudie")
    public void addStudie(@RequestParam("mainImage") CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
        //path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(strFail, strNoImage);
            return;
        }
        //create new object Organisation
        Studie studie = new Studie();
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");


        String text = actionRequest.getParameter("text");
        String[] facultetDnevn = actionRequest.getParameterValues("facultetDnevn");
        String[] facultetZaoch = actionRequest.getParameterValues("facultetZaoch");
        String adress = actionRequest.getParameter("adress");
        String facultetZ = "", facultetD = "";
        for (String strD : facultetDnevn) {
            if (!"".equals(strD)) {
                //todo: use StringBuilder for concatenation
                //todo: do you store all facultets in one string?
                facultetD = facultetD.concat(strD);
                facultetD = facultetD.concat(";");
            }
        }
        for (String strZ : facultetZaoch) {
            if (!"".equals(strZ)) {
                //todo: same
                facultetZ = facultetZ.concat(strZ);
                facultetZ = facultetZ.concat(";");
            }
        }
        facultetD = facultetD.substring(0, facultetD.length() - 1);
        facultetZ = facultetZ.substring(0, facultetZ.length() - 1);
        //update fields for new organisation
        //todo: move code about resizing to service
        int t = Integer.parseInt(actionRequest.getParameter("t"));
        int l = Integer.parseInt(actionRequest.getParameter("l"));
        int w = Integer.parseInt(actionRequest.getParameter("w"));
        int h = Integer.parseInt(actionRequest.getParameter("h"));
        BufferedImage sourceImage = ImageIO.read(mainImage.getInputStream());
        sourceImage = ImageService.resize(sourceImage, 443, 253);
        sourceImage = sourceImage.getSubimage(t, l, w, h);
        File temp = new File(ImageService.getImagesFolderAbsolutePath() + mainImage.getOriginalFilename());
        ImageIO.write(sourceImage, "jpg", temp);
        CommonsMultipartFile f = null;
        //todo: one try block!
        try {
            DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("fileData", "image/jpeg", true, temp.getName());
            InputStream input = new FileInputStream(temp);
            OutputStream os = fileItem.getOutputStream();
            int ret = input.read();
            while (ret != -1) {
                os.write(ret);
                ret = input.read();
            }
            os.flush();
            f = new CommonsMultipartFile(fileItem);
        } catch (Exception e) {}
        if (updateCommunityFields(f, images, topic.trim(), text.trim(), facultetD, facultetZ, adress, actionResponse, strFail, strNoImage, studie)) {
            try {
                service.addStudie(studie);
                actionResponse.setRenderParameter("orgsID", Integer.toString(studie.getId()));
                //close session
                sessionStatus.setComplete();
            } catch (Exception ex) {
                //exception controller
                //todo:use logging
                StringWriter sw = new StringWriter();
                actionResponse.setRenderParameter(strExept, sw.toString());
            }
        }
    }

    @ActionMapping(value = "editStudie")
    public void editStudie(@RequestParam("mainImage") CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
        //getting current news
        int organisationID = Integer.valueOf(actionRequest.getParameter("studieId"));
        Studie studie = service.getStudieById(organisationID);
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        String[] facultetDnevn = actionRequest.getParameterValues("facultetDnevn");
        String[] facultetZaoch = actionRequest.getParameterValues("facultetZaoch");
        String adress = actionRequest.getParameter("adress");
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
        //update fields for new organisation

        if (updateCommunityFields(mainImage, images, topic.trim(), text.trim(), facultetD, facultetZ, adress, actionResponse, strFail, strNoImage, studie)) {
            //updating info about loaded organisation
            try {
                service.updateStudie(studie);
                //close session
                sessionStatus.setComplete();
            } catch (Exception ex) {
                //exception controller
                StringWriter sw = new StringWriter();
                actionResponse.setRenderParameter(strExept, sw.toString());
            }
        }

    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddStuds(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        //set view for add
        model.setViewName("addStudie");
        return model;
    }

    @RenderMapping(params = "mode=delImage")
    public ModelAndView delImage(RenderRequest request, RenderResponse response) {
        long imageID = Long.valueOf(request.getParameter("imageId"));
        ImageImpl image = service.getImageById(imageID);
        ImageService imageService = new ImageService();
        //delete image from folder
        imageService.deleteImage(image, image.getBase());
        //delete image from data base
        service.deleteImage(image);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        //getting news
        int studieID = Integer.valueOf(request.getParameter("studieId"));
        Studie studie = service.getStudieById(studieID);
        ImageService imageService = new ImageService();
        ImageImpl mImage = studie.getMainImage();
        String mainImageUrl;
        String facultetD = studie.getFacultetsDnev();
        String facultetZ = studie.getFacultetsZaoch();
        String adress = studie.getAdress();

        String[] facultetDnevn = facultetD.split(";");
        String[] facultetZaoch = facultetZ.split(";");
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, studie);
        }
        Collection<ImageImpl> additionalImages = studie.getAdditionalImages();
        //set view for edit
        model.setViewName("editStudie");
        //send current news in view
        model.addObject("studie", studie);
        model.addObject(mainImagePar, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        model.addObject("facultetDnevn", facultetDnevn);
        model.addObject("facultetZaoch", facultetZaoch);
        model.addObject("adress", adress);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteOrganisation(RenderRequest request, RenderResponse response) {
        //getting current news

        int studieID = Integer.valueOf(request.getParameter("studieId"));
        Studie studie = service.getStudieById(studieID);
		ImageService imageService = new ImageService();
		//delete chosen studie's image from folder
        imageService.deleteDirectory(studie);
        // delete chosen news
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
        SessionErrors.add(request, request.getParameter(strFail));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddStuds(request, response);
        model.addObject(strExept, request.getParameter(strExept));
        return model;
    }
}
