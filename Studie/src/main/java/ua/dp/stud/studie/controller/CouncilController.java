package ua.dp.stud.studie.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;

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


@Controller(value="CouncilController")
@RequestMapping(value = "VIEW")

public class CouncilController{
	
    private static final Logger LOG = Logger.getLogger(CouncilController.class.getName());
	
    private static final String COUNCIL = "council";
    private static final String STR_FAIL = "fail";
    private static final String NO_IMAGE = "no-images";
    
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
        return getMainView(request);
    }

    private ModelAndView getMainView(RenderRequest request) {
        ModelAndView model = new ModelAndView("viewAllCouncils");
        return model;
    }

    private boolean updateCouncil(Council council, CommonsMultipartFile mainImage,
             ActionResponse actionResponse, BindingResult bindingResult) {
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
    public void addCouncil(@ModelAttribute("council") @Valid Council council,
                        BindingResult bindingResult,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse,
                        @RequestParam("mainImage") CommonsMultipartFile mainImage,
                        SessionStatus sessionStatus) {
    		if (bindingResult.hasErrors()) {

            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.toString());
                System.out.println();
            }
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
        if (updateCouncil(council, f, actionResponse, bindingResult)) {
            //updating info about loaded councils images
            councilService.addCouncil(council);
            //close session
            sessionStatus.setComplete();
        }
    }

    @ActionMapping(value = "editCouncil")
    public void editCouncil(@ModelAttribute("council") @Valid Council council,
                         BindingResult bindingResult,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse,
                         @RequestParam("mainImage") CommonsMultipartFile mainImage,
                         SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.toString());
                System.out.println();
            }
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        Council oldCouncil = councilService.getCouncilById(council.getId());
        oldCouncil.setCouncilName(council.getCouncilName());
        oldCouncil.setCouncilContact(council.getCouncilContact());
        oldCouncil.setCouncilDescription(council.getCouncilDescription());
        if (!mainImage.getOriginalFilename().equals("")) {
            mainImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                               Integer.parseInt(actionRequest.getParameter("l")),
                                               Integer.parseInt(actionRequest.getParameter("w")),
                                               Integer.parseInt(actionRequest.getParameter("h")));
        }
        if (updateCouncil(oldCouncil, mainImage,actionResponse, bindingResult)) {
            councilService.updateCouncil(oldCouncil);
            //close session
            sessionStatus.setComplete();
        }
    }
    
    @RenderMapping(params = "mode=add")
    public ModelAndView showAddCouncil(RenderRequest request, RenderResponse response) {
        return new ModelAndView("addCouncil");
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditCouncil(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("editCouncil");
        //getting council   
       /* int councilID = Integer.valueOf(request.getParameter("councilId"));
        Council council = councilService.getCouncilById(councilID);
        ImageImpl mImage = council.getMainImage();
        String mainImageUrl =imageService.getPathToLargeImage(mImage,council);
        model.getModelMap().addAttribute("council", council);
        model.addObject("mainImage", mainImageUrl);*/
        return model;
    }

    @RenderMapping(params = "mode=delete")
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
        binder.setDisallowedFields("mainImage");

    }
    
    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addCouncil");
        SessionErrors.add(request, request.getParameter(STR_FAIL));
        return model;
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = getMainView(request);
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }



}