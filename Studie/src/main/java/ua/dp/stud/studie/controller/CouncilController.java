package ua.dp.stud.studie.controller;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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







import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;

import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.service.CouncilService;
import ua.dp.stud.studie.service.impl.CouncilServiceImpl;


@Controller(value="CouncilController")
@RequestMapping(value = "VIEW")

public class CouncilController{
	
    private static final Logger LOG = Logger.getLogger(CouncilController.class.getName());
	
    private static final String COUNCIL = "council";
    private static final String STR_FAIL = "fail";
    private static final String NO_IMAGE = "no-images";
    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String MAIN_IMAGE = "mainImage";
    @Autowired
    @Qualifier(value = "councilService")
    private CouncilService councilService;

    public void setCouncilService(CouncilService councilService)
    {
        this.councilService = councilService;
    }

    @ModelAttribute(value = COUNCIL)
    public Council getCommandObject() {
        return new Council();
    }
    
    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RenderMapping(params="view=allcouncils")
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        return getMainView();
    }

    private ModelAndView getMainView() {
        ModelAndView model = new ModelAndView("viewAllCouncils");
        Collection<Council> councils = councilService.getAll();
        model.addObject("councils", councils);
        return model;
    }

    private boolean updateCouncil(Council council, CommonsMultipartFile mainImage,
             ActionResponse actionResponse) {
    	boolean successUpload = true;
    	//main image uploading
    		try {
    				if (mainImage.getSize() > 0) {
    					imageService.saveMainImage(mainImage, council);
    					}
    			}
    		catch (Exception ex) {
    			successUpload = false;
    			LOG.log(Level.SEVERE, "Exception: ", ex);
    			}
    		//success upload message
    		if (successUpload) {
    			actionResponse.setRenderParameter("success", "success-add");
    			return true;
    		} else {
    			actionResponse.setRenderParameter(STR_FAIL, NO_IMAGE);
    			return false;
    		}
    }

    
    @ActionMapping(value = "addCouncil")
    public void addCouncil(@ModelAttribute(COUNCIL) @Valid Council council,
                        BindingResult bindingResult,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse,
                        @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                        SessionStatus sessionStatus) {
    		if (bindingResult.hasErrors()) {

            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        //path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, NO_IMAGE);
            return;
        }

        CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                        Integer.parseInt(actionRequest.getParameter("l")),
                                                        Integer.parseInt(actionRequest.getParameter("w")),
                                                        Integer.parseInt(actionRequest.getParameter("h")));
        //update fields for new councils
        if (updateCouncil(council, f, actionResponse)) {
            //updating info about loaded councils images
            councilService.addCouncil(council);
            //close session
            actionResponse.setRenderParameter("view", "allcouncils");
            sessionStatus.setComplete();
            
        }
    }

    @ActionMapping(value = "editCouncil")
    public void editCouncil(@ModelAttribute(COUNCIL) @Valid Council council,
                         BindingResult bindingResult,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse,
                         @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                         SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        Council oldCouncil = councilService.getCouncilById(council.getId());
        oldCouncil.setCouncilName(council.getCouncilName());
        oldCouncil.setCouncilContact(council.getCouncilContact());
        oldCouncil.setCouncilDescription(council.getCouncilDescription());
        CommonsMultipartFile croppedImage = null;
        if (!mainImage.getOriginalFilename().equals("")) {
        	croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                    Integer.parseInt(actionRequest.getParameter("l")),
                    Integer.parseInt(actionRequest.getParameter("w")),
                    Integer.parseInt(actionRequest.getParameter("h")));
        }
        if (updateCouncil(oldCouncil, croppedImage,actionResponse)) {
            councilService.updateCouncil(oldCouncil);
            //close session
            actionResponse.setRenderParameter("view", "allcouncils");
            sessionStatus.setComplete();
            
        }
    }
    
    @RenderMapping(params = "add=council")
    public ModelAndView showAddCouncil(RenderRequest request, RenderResponse response) {
        return new ModelAndView("addCouncil");
    }

    @RenderMapping(params = "edit=council")
    public ModelAndView showEditCouncil(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("editCouncil");
        //getting council   
        int councilID =Integer.valueOf(request.getParameter("councilId"));
        Council council = councilService.getCouncilById(councilID);
        ImageImpl mImage = council.getMainImage();
        String mainImageUrl =imageService.getPathToLargeImage(mImage,council);
        model.getModelMap().addAttribute(COUNCIL, council);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        return model;
    }

    @RenderMapping(params = "delete=council")
    public ModelAndView deleteCouncil(RenderRequest request, RenderResponse response) {
        //getting current councils
        int councilID = Integer.valueOf(request.getParameter("councilId"));
        Council council = councilService.getCouncilById(councilID);
        imageService.deleteDirectory(council);
        //delete chosen council
        councilService.deleteCouncil(councilID);
        return showAddSuccess(request, response);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(MAIN_IMAGE);

    }
    
    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addCouncil");
        SessionErrors.add(request, request.getParameter(STR_FAIL));
        return model;
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = getMainView();
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }
    /**
     * Method for rendering view mode for single organization
     *
     * @param request
     * @param response
     * @return model
     */
    @RenderMapping(params = "mode=showCouncil")
    public ModelAndView showSelectedCouncil(RenderRequest request, RenderResponse response, SessionStatus sessionStatus) throws SystemException, PortalException {

        int councilId = Integer.valueOf(request.getParameter("id"));
        Council council = councilService.getCouncilById(councilId);
        ImageImpl mImage = council.getMainImage();
        String mainImageUrl;
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToMicroblogImage(mImage, council);
        }


        int currentPage;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingleCouncil");
        model.addObject(COUNCIL, council);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject(MAIN_IMAGE, mainImageUrl);

        return model;
    }



}