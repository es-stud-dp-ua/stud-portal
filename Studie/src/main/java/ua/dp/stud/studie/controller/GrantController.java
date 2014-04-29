package ua.dp.stud.studie.controller;

import java.io.IOException;
import java.util.Collection;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ua.dp.stud.studie.model.Grant;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;
import ua.dp.stud.studie.service.GrantService;

@Controller(value = "GrantController")
@RequestMapping(value = "VIEW")
public class GrantController {
	
	private static final String MAIN_IMAGE = "mainImage";
	private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
	private static final String STR_FAIL = "failGrant";
    private static final String JSP_NAME="jsp";
    private static final String NO_IMAGE = "no-images";
	
	@Autowired
	@Qualifier(value = "grantService")
	private GrantService grantService;

	public void setGrantService(GrantService grantService) {
		this.grantService = grantService;
	}

	@ModelAttribute(value = "grant")
	public Grant getGrantCommandObject() {
		return new Grant();
	}


	@Autowired
	@Qualifier(value = "imageService")
	private ImageService imageService;

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@RenderMapping(params = "view=allGrants")
	public ModelAndView showView(RenderRequest request, RenderResponse response) {
		return getMainView();
	}

	private ModelAndView getMainView() {
		ModelAndView model = new ModelAndView("viewAllGrants");
		Collection<Grant> grants = grantService.getAllGrants();
		model.addObject("grants", grants);
		return model;
	}
	
	@RenderMapping(params = "view=singleGrant")
    public ModelAndView viewSingleGrant(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingleGrant");
        Integer id = Integer.parseInt(request.getParameter("id"));
        Grant grant = grantService.getGrantById(id);
        model.addObject("grant",grant);
        return model;
    }


	@RenderMapping(params = "view=deleteGrant")
    public ModelAndView deleteGrant(RenderRequest request, RenderResponse response) {
    	Integer id = Integer.parseInt(request.getParameter("id"));
        grantService.deleteGrant(id);
        return  showAddSuccess(request,response);
    }

	@RenderMapping(params = "add=grant")
	public ModelAndView showAddGrant(RenderRequest request,
			RenderResponse response) {
	return new ModelAndView("addGrant");
    }
    
    @RenderMapping(params = "view=editGrant")
    public ModelAndView showEditGrant(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("editGrant");
        int ID = Integer.valueOf(request.getParameter("id"));
        Grant grant = grantService.getGrantById(ID);
        ImageImpl mImage = grant.getMainImage();
        String mainImageUrl = (mImage == null) ? MAIN_IMAGE_MOCK_URL : imageService.getPathToLargeImage(mImage, grant);
        model.getModelMap().addAttribute("grant", grant);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        return model;
    }
      
    @ActionMapping(value = "saveGrant")
    public void addGrant(@ModelAttribute("grant") @Valid Grant grant,
                        BindingResult bindingResult,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse,
                        @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                        SessionStatus sessionStatus) throws IOException {

        actionResponse.setRenderParameter(JSP_NAME,"add");

        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");

            return;
        }
        if ("".equals(mainImage.getOriginalFilename())) {
            actionResponse.setRenderParameter(STR_FAIL, "image");
            return;
        }
        if (grantService.isDuplicateTopic(grant.getUniversity(),null)){
            actionResponse.setRenderParameter(STR_FAIL, "dplTopic");
            return;
        }
        CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                        Integer.parseInt(actionRequest.getParameter("l")),
                                                        Integer.parseInt(actionRequest.getParameter("w")),
                                                        Integer.parseInt(actionRequest.getParameter("h")));
        
        if (mainImage.getSize() > 0) {
            imageService.saveMainImage(f, grant);
        	}
        	grantService.addGrant(grant);
            actionResponse.setRenderParameter("id",grant.getId().toString());
            actionResponse.setRenderParameter("view", "singleGrant");
            SessionMessages.add(actionRequest, "successAdd");
            sessionStatus.setComplete();

        
    }

    @ActionMapping(value="editGrant")
    public void editGrant(@ModelAttribute("grant") @Valid Grant grant,
                         BindingResult bindingResult,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse,
                         @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                         SessionStatus sessionStatus) throws IOException {

        actionResponse.setRenderParameter(JSP_NAME,"edit");
        actionResponse.setRenderParameter("id",grant.getId().toString());
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "image");
            return;
        }
        CommonsMultipartFile croppedImage = null;
        Grant oldGrant = grantService.getGrantById(grant.getId());
        if (grantService.isDuplicateTopic(grant.getUniversity(),oldGrant.getId())){
            actionResponse.setRenderParameter(STR_FAIL, "dplTopic");
            return;
        }
        oldGrant.setCity(grant.getCity());
        oldGrant.setCountry(grant.getCountry());
        oldGrant.setDescription(grant.getDescription());
        oldGrant.setDocuments(grant.getDocuments());
        oldGrant.setEducationLanguage(grant.getEducationLanguage());
        oldGrant.setEducationPeriod(grant.getEducationPeriod());
        oldGrant.setQualification(grant.getQualification());
        oldGrant.setReceiptOfDocuments(grant.getReceiptOfDocuments());
        oldGrant.setSpeciality(grant.getSpeciality());
        oldGrant.setUniversity(grant.getUniversity());
        if (!"".equals(mainImage.getOriginalFilename())) {
        	croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                               Integer.parseInt(actionRequest.getParameter("l")),
                                               Integer.parseInt(actionRequest.getParameter("w")),
                                               Integer.parseInt(actionRequest.getParameter("h")));
        }
        if (mainImage.getSize() > 0) {
            imageService.saveMainImage(croppedImage, oldGrant);
        }
        grantService.updateGrant(oldGrant);
        actionResponse.setRenderParameter("view", "singleGrant");
        SessionMessages.add(actionRequest, "successEdit");
        sessionStatus.setComplete();
    }

    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(MAIN_IMAGE);
    }

    @RenderMapping(params = "failGrant")
    public ModelAndView showAddFailed(RenderRequest request,
                                      RenderResponse response) {
        ModelAndView model;
        if("edit".equals(request.getParameter(JSP_NAME))){
            model =  new ModelAndView("editGrant");
        }else{
            model=new ModelAndView("addGrant");
        }
        SessionErrors.add(request, request.getParameter(STR_FAIL));
        return model;
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request,
                                       RenderResponse response) {
        ModelAndView model = getMainView();
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

}