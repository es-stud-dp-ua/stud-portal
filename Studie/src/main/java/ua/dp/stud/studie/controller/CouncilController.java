package ua.dp.stud.studie.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;









import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;

import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.model.CouncilMembers;
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
    
    @ModelAttribute(value = "councilMembers")
    public CouncilMembers getСouncilMembersCommandObject() {
        return new CouncilMembers();
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

    
    
    @ResourceMapping(value = "addMember")
    public void addMember(ResourceResponse response, ResourceRequest request,String name, String position, String contact, Integer id) throws Exception {
    	CouncilMembers member = new CouncilMembers();
    	member.setMemberContact(contact);
    	member.setMemberName(name);
    	member.setMemberPosition(position);
    	Council council = councilService.getCouncilById(id);
    	member.setNameOfCouncil(council);
    	council.getCouncilMembers().add(member);
    	councilService.addCouncilMembers(member);
    	StringBuilder s = new StringBuilder();
    	s.append("<div id='member_").append(member.getId()).append("'><fieldset><div id='each_").append(member.getId());
    	s.append("' style='float:left; display:inline'><div><i>").append(position).append("</i></div><div><b>").append(name);
    	s.append("</b></div><div>").append(contact).append("</div></div><div style='display:inline; float:right' class='icon-pcppencil fs20' onclick='editMember(");
    	s.append(member.getId()).append(");' aria-hidden='true'></div><div style='display:inline; float:right'   class='icon-pcpremove fs20' onclick='removeMember(");
    	s.append(member.getId()).append(");' aria-hidden='true'></div><div id='edit_").append(member.getId()).append("'  style='display:none; float:left'>");
    	s.append("<div id='labels'><spring:message code='form.councilMemberName'/></div>");
    	s.append("<input id='edit_name_").append(member.getId()).append("' type='text' value='").append(name).append("'/>");
    	s.append("<div id='labels'><spring:message code='form.councilMemberPosition'/></div>");
    	s.append("<input id='edit_position_").append(member.getId()).append("' type='text' value='").append(position).append("'/>");
    	s.append("<div id='labels'><spring:message code='form.councilMemberContact'/></div>");
    	s.append("<textarea id='edit_contact_").append(member.getId()).append("' COLS='60' ROWS='5'>").append(contact).append("</textarea><br>");
    	s.append("<input type='button' value='Save' onclick='updateMember(").append(member.getId()).append(")'></div></fieldset><br/>");
    	response.getWriter().println(s);
    }
    
    @ResourceMapping(value = "editMember")
    public void editMember(ResourceResponse response, ResourceRequest request,
    		@RequestParam (required = true, defaultValue = "default")String name,
    		@RequestParam(required = true, defaultValue = "default") String position, 
    		@RequestParam(required = true, defaultValue = "default") String contact, 
    		@RequestParam(required = true) Integer memberId, 
    		@RequestParam (required = true)Integer cid) {
    	System.out.println("name:"+name );
    	System.out.println("position:"+position );
    	System.out.println("contct:"+contact );
    	System.out.println("id:"+memberId );
    	System.out.println("cid:"+cid );
    	CouncilMembers oldCouncilMember = councilService.getCouncilMembersById(memberId);
        oldCouncilMember.setMemberName(name);
        oldCouncilMember.setMemberContact(contact);
        oldCouncilMember.setMemberPosition(position);
        Council council = councilService.getCouncilById(cid);
        oldCouncilMember.setNameOfCouncil(council);
        councilService.updateCouncilMembers(oldCouncilMember);

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
        int councilID = Integer.valueOf(request.getParameter("councilID"));
        Council council = councilService.getCouncilById(councilID);
        imageService.deleteDirectory(council);
        //delete chosen council
        councilService.deleteCouncil(councilID);
        return showAddSuccess(request, response);
    }
    
        
    @ResourceMapping(value = "removeMember")
    public void removeMember(ResourceResponse response, ResourceRequest request,
                                   Integer memberId) {
        councilService.deleteCouncilMembers(memberId);
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
    public ModelAndView showSelectedCouncil(RenderRequest request, RenderResponse response){

        int councilId = Integer.valueOf(request.getParameter("id"));
        Council council = councilService.getCouncilById(councilId);
        ImageImpl mImage = council.getMainImage();
        String mainImageUrl;
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToMicroblogImage(mImage, council);
        }
       Collection <CouncilMembers> councilMem = councilService.getAllCouncilMembers();
        int currentPage;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingleCouncil");
        model.addObject(COUNCIL, council);
        model.addObject("councilMembers", councilMem);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("councilId", councilId);
        return model;
    }



}