package ua.dp.stud.studie.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.validation.Valid;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.model.CouncilMembers;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.service.CouncilService;
import ua.dp.stud.studie.service.StudieService;
import ua.dp.stud.studie.service.impl.CouncilServiceImpl;

@Controller(value = "CouncilController")
@RequestMapping(value = "VIEW")
public class CouncilController {

	private static final Logger LOG = Logger.getLogger(CouncilController.class
			.getName());

	private static final String COUNCIL = "council";
	private static final String STR_FAIL = "fail";
	private static final String NO_IMAGE = "no-images";
	private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
	private static final String CURRENT_PAGE = "currentPage";
	private static final String MAIN_IMAGE = "mainImage";
	@Autowired
	@Qualifier(value = "councilService")
	private CouncilService councilService;

    @Autowired
    @Qualifier(value = "studieService")
    private StudieService studieService;

	public void setCouncilService(CouncilService councilService) {
		this.councilService = councilService;
	}

    public void setStudieService(StudieService studieService) {
        this.studieService = studieService;
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

	@RenderMapping(params = "view=allcouncils")
	public ModelAndView showView(RenderRequest request, RenderResponse response) {
		return getMainView();
	}

	private ModelAndView getMainView() {
		ModelAndView model = new ModelAndView("viewAllCouncils");
		Collection<Council> councils = councilService.getAll();
		model.addObject("councils", councils);
		return model;
	}


	@ActionMapping(value = "addCouncil")
	public void addCouncil(@ModelAttribute(COUNCIL) @Valid Council council,
			BindingResult bindingResult, ActionRequest actionRequest,
			ActionResponse actionResponse,
			SessionStatus sessionStatus) {
		if (bindingResult.hasErrors()) {

			actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
			return;
		}

        council.setStudie(studieService.getStudieById(council.getStudie().getId()));
        councilService.addCouncil(council);
        // close session
        actionResponse.setRenderParameter("view", "allcouncils");
        sessionStatus.setComplete();


    }

	

	@ResourceMapping(value = "addMember")
	public void addMember(ResourceResponse response, ResourceRequest request) throws IOException {
		
		UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
		String name = upload.getParameter("name");
		String position = upload.getParameter("position");
		String contact = upload.getParameter("contact");
		Integer council_id = Integer.parseInt(upload.getParameter("council_id"));
		File file = upload.getFile("file");
		 
		CouncilMembers member = new CouncilMembers();

        if (file != null) {
		 imageService.saveMemberImage(file, member);
		 }
        
		member.setMemberContact(contact);
        member.setMemberName(name);
        member.setMemberPosition(position);
        member.setListPosition(council_id);
        Council council = councilService.getCouncilById(council_id);
        member.setNameOfCouncil(council);
        council.getCouncilMembers().add(member);
        councilService.addCouncilMembers(member);
        Integer newId = member.getId();
       
        CouncilMembers oldCouncilMember = councilService
				.getCouncilMembersById(newId);
        oldCouncilMember.setListPosition(newId);
        councilService.updateCouncilMembers(oldCouncilMember);
        StringBuilder s = new StringBuilder();
        s.append("<div id='member_").append(member.getId()).append("' class='memberList'><fieldset id='memberField_");
        s.append(member.getId()).append("'><div id='each_").append(member.getId());
        s.append("' style='float:left; display:inline'>");
        if (member.getMainImage() != null){
        s.append("<img id='image_").append(member.getId()).append("' src='").append(imageService.getPathToMicroblogImage(member.getMainImage(),member));
        s.append("' style='float: left'>");
        }
        s.append("<div style='float: left'><div id='position_").append(member.getId()).append("'><i>").append(position);
        s.append("</i></div><div id='name_").append(member.getId()).append("'><b>").append(name);
        s.append("</b></div><div id='contact_").append(member.getId()).append("'>").append(contact).append("</div></div></div><div style='display:inline; float:right' class='icon-pcpremove fs20' onclick='removeMember(");
        s.append(member.getId()).append(");' aria-hidden='true'></div><div style='display:inline; float:right'   class='icon-pcppencil fs20' onclick='editMember(");
        s.append(member.getId()).append(");' aria-hidden='true'></div>");
        s.append("<div style='display: inline; float: right' class='icon-pcparrow-up fs20' onclick='memberUp(").append(member.getId());
        s.append(");' aria-hidden='true'></div><div style='display: inline; float: right' class='icon-pcparrow-down fs20'");
		s.append(" onclick='memberDown(").append(member.getId()).append(");' aria-hidden='true'></div>");
        
		s.append("<div id='edit_").append(member.getId()).append("'  style='display:none; float:left'>");
        s.append("<div style='left: 30%;' style='width: 460px;'><div style='height: 300px;'>");
        if (member.getMainImage() != null){
        s.append("<div id='mainPic'>");
        s.append("<img id='img_").append(member.getId()).append("' style='vertical-align: top; ' src='").append(imageService.getPathToLargeImage(member.getMainImage(),member)).append("'/>");
        s.append("<output id='edit_list_").append(member.getId()).append("' class='edit_list'></output></div>");
        } else {
        s.append("<div id='mainPic'>");
        s.append("<img id='img_").append(member.getId()).append("' style='vertical-align: top; ' src='").append("${pageContext.request.contextPath}/images/mainpic_443x253.png").append("'/>");
        s.append("<output id='edit_list_").append(member.getId()).append("' class='edit_list'></output></div>");
        }
        s.append("</div></div><div style='left: 30%;' id='mainImageLoader'><div id='mainImgloaderBtn'>");
        s.append("<input type='file' id='editImage_").append(member.getId()).append("' class='editImage' accept='image/jpeg,image/png,image/gif'/><div id='nt'><p>...</p></div></div></div>");
        s.append("<div id='labels_edit_name'><spring:message code='form.councilMemberName'/></div>");
        s.append("<input id='edit_name_").append(member.getId()).append("' type='text' value='").append(name).append("'/>");
        s.append("<div id='labels_edit_position'><spring:message code='form.councilMemberPosition'/></div>");
        s.append("<input id='edit_position_").append(member.getId()).append("' type='text' value='").append(position).append("'/>");
        s.append("<div id='labels_edit_contact'><spring:message code='form.councilMemberContact'/></div>");
        s.append("<textarea id='edit_contact_").append(member.getId()).append("' COLS='60' ROWS='5'>").append(contact).append("</textarea><br>");
        s.append("<input type='button' value='Save' onclick='updateMember(").append(member.getId()).append(")'>");
        s.append("  <input type='button' value='Cancel' onclick='cancelUpdateMember(").append(member.getId()).append(")'>");
        s.append("</div></fieldset><br/>");
        response.getWriter().println(s);
	}

	@ResourceMapping(value = "editMember")
	public void editMember(ResourceResponse response,ResourceRequest request) throws Exception {

		UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
		String name = upload.getParameter("name");
		String position = upload.getParameter("position");
		String contact = upload.getParameter("contact");
		Integer council_id = Integer.parseInt(upload.getParameter("council_id"));
		Integer member_id = Integer.parseInt(upload.getParameter("member_id"));
		File file = upload.getFile("file");
		CouncilMembers oldCouncilMember = councilService
				.getCouncilMembersById(member_id);
		if (!(file==null)){
				if (file.length() > 0) {
			 imageService.saveMemberImage(file, oldCouncilMember);
			 }
		}
		
		oldCouncilMember.setMemberName(name);
		oldCouncilMember.setMemberContact(contact);
		oldCouncilMember.setMemberPosition(position);
		Council council = councilService.getCouncilById(council_id);
		oldCouncilMember.setNameOfCouncil(council);
		councilService.updateCouncilMembers(oldCouncilMember);
		String path = imageService.getPathToMicroblogImage(oldCouncilMember.getMainImage(),oldCouncilMember);
		System.out.println(path);
		if (path != null){
		response.getWriter().println(path);
		}
	}


	@ResourceMapping(value = "memberUp")
	public void MemberUp(ResourceResponse response,ResourceRequest request,Integer member_id,Integer council_id) throws Exception {

		CouncilMembers member = councilService.getCouncilMembersById(member_id);
		Council council = councilService.getCouncilById(council_id);
		List<CouncilMembers> list = council.getCouncilMembers();
		int index=-1;
		for(CouncilMembers obj : list ){
			if(obj.getId().equals(member_id)){
				index = list.indexOf(obj);
			}
		}
		
		if(index>0){	
			System.out.println("Enter");
		CouncilMembers prevMember = list.get(index-1);
		Integer posList = member.getListPosition();
		System.out.println(posList);
		Integer prevList = prevMember.getListPosition();
		System.out.println(prevList);
		Integer temp = posList; 
		member.setListPosition(prevList);
		prevMember.setListPosition(temp);
		councilService.updateCouncilMembers(member);
		councilService.updateCouncilMembers(prevMember);
			}
		
	}

	@ResourceMapping(value = "memberDown")
	public void MemberDown(ResourceResponse response,ResourceRequest request, Integer member_id, Integer council_id) throws Exception {

		CouncilMembers member = councilService.getCouncilMembersById(member_id);
		Council council = councilService.getCouncilById(council_id);
		List<CouncilMembers> list = council.getCouncilMembers();
		int index=-1;
		for(CouncilMembers obj : list ){
			if(obj.getId().equals(member_id)){
				index = list.indexOf(obj);
			}
		}
		
		if(index!=(list.size()-1)){	
			System.out.println("Enter");
		CouncilMembers nextMember = list.get(index+1);
		Integer posList = member.getListPosition();
		Integer nextList = nextMember.getListPosition();
		Integer temp = posList; 
		member.setListPosition(nextList);
		nextMember.setListPosition(temp);
		councilService.updateCouncilMembers(member);
		councilService.updateCouncilMembers(nextMember);
			}
	}

	
	@ActionMapping(value = "editCouncil")
	public void editCouncil(@ModelAttribute(COUNCIL) @Valid Council council,
			BindingResult bindingResult, ActionRequest actionRequest,
			ActionResponse actionResponse,
			SessionStatus sessionStatus) {
		if (bindingResult.hasErrors()) {
			actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
			return;
		}
		Council oldCouncil = councilService.getCouncilById(council.getId());
        oldCouncil.setStudie(studieService.getStudieById(council.getStudie().getId()));
		oldCouncil.setCouncilContact(council.getCouncilContact());
		oldCouncil.setCouncilDescription(council.getCouncilDescription());
        councilService.updateCouncil(oldCouncil);
		// close session
		actionResponse.setRenderParameter("view", "allcouncils");
		sessionStatus.setComplete();


	}

	@RenderMapping(params = "add=council")
	public ModelAndView showAddCouncil(RenderRequest request,
			RenderResponse response) {

		ModelAndView model = new ModelAndView("addCouncil");
        Collection <Studie> studies = studieService.getAllStudies();
        model.addObject("studie",studies);
	return model;
    }

	@RenderMapping(params = "edit=council")
	public ModelAndView showEditCouncil(RenderRequest request,
			RenderResponse response) {
		ModelAndView model = new ModelAndView("editCouncil");
		// getting council
		int councilID = Integer.valueOf(request.getParameter("councilId"));
		Council council = councilService.getCouncilById(councilID);
		model.getModelMap().addAttribute(COUNCIL, council);
        Collection <Studie> studies = studieService.getAllStudies();
        model.addObject("studie",studies);

		return model;
	}

	@RenderMapping(params = "delete=council")
	public ModelAndView deleteCouncil(RenderRequest request,
			RenderResponse response) {
		int councilID = Integer.valueOf(request.getParameter("councilID"));
		Council council = councilService.getCouncilById(councilID);
		councilService.deleteCouncil(councilID);
		return showAddSuccess(request, response);
	}

	@ResourceMapping(value = "removeMember")
	public void removeMember(ResourceResponse response,
			ResourceRequest request, Integer memberId) {
		CouncilMembers member = councilService.getCouncilMembersById(memberId);
		imageService.deleteDirectory(member);
		councilService.deleteCouncilMembers(memberId);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(MAIN_IMAGE);

	}

	@RenderMapping(params = "fail")
	public ModelAndView showAddFailed(RenderRequest request,
			RenderResponse response) {
		ModelAndView model = new ModelAndView("addCouncil");
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

	/**
	 * Method for rendering view mode for single organization
	 * 
	 * @param request
	 * @param response
	 * @return model
	 */
	@RenderMapping(params = "mode=showCouncil")
	public ModelAndView showSelectedCouncil(RenderRequest request,
			RenderResponse response) {

		int councilId = Integer.valueOf(request.getParameter("id"));
		Council council = councilService.getCouncilById(councilId);
     	ImageImpl mImage = council.getStudie().getMainImage();
		String mainImageUrl;
		if (mImage == null) {
			mainImageUrl = MAIN_IMAGE_MOCK_URL;
		} else {
			mainImageUrl = imageService
					.getPathToMicroblogImage(mImage, council.getStudie());
		}
		Collection<CouncilMembers> councilMem = councilService
				.getAllCouncilMembers();
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