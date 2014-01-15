package ua.dp.stud.studie.controller;

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
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.CourseService;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.service.OnlineCourseService;

import javax.portlet.*;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Kstin777 on 10.12.13.
 */
@Controller(value = "OnlineCoursesController")
@RequestMapping(value = "VIEW")
public class OnlineCourseController {
	private static final String ADMIN_ROLE = "Administrator";
	private static final String MAIN_IMAGE = "mainImage";
    private static final String ONLINE_COURSE = "onlineCourse";
    private static final String STR_FAIL = "fail";
    private static final String NO_IMAGE = "no-images";
    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";

    @Autowired
    @Qualifier(value = "onlineCourseService")
    private OnlineCourseService onlineCourseService;

    public void setOnlineCourseService(OnlineCourseService onlineCourseService) {
        this.onlineCourseService = onlineCourseService;
    }
    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }



    @RenderMapping(params = "view=singleOnlineCourse")
    public ModelAndView viewSingleOnlineCourses(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingleOnlineCourse");
        Integer id = Integer.parseInt(request.getParameter("id"));
        OnlineCourse course = onlineCourseService.getOnlineCourseById(id);
        model.addObject("onlineCourse",course);
        return model;
    }
    

    @ActionMapping(params = "view=deleteOnlineCourse")
    public void deleteOnlineCourse(ActionRequest request, ActionResponse response) {
    	Integer id = Integer.parseInt(request.getParameter("id"));
        onlineCourseService.deleteOnlineCourse(id);
        response.setRenderParameter("view", "allOnlineCourses");
    }
    
    @RenderMapping(params = "view=allOnlineCourses")
    public ModelAndView viewAllOnlineCourses(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        List<OnlineCourseType> onlineCourseTypes = onlineCourseService.getAllOnlineCourseType();
        model.setViewName("viewAllOnlineCourses");
        model.addObject("onlineCourseTypes", onlineCourseTypes);
        List<OnlineCourse> onlineCourses = onlineCourseService.getAll();
        model.addObject("onlineCourses", onlineCourses);
        return model;
    }

    @ModelAttribute(value ="onlineCourse")
    public OnlineCourse getOnlineCourseCommandObject() {
        return new OnlineCourse();
    }
   
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(MAIN_IMAGE);
    }

    
    @RenderMapping(params = "add=onlineCourse")
	public ModelAndView showAddOnlineCourse(RenderRequest request,
			RenderResponse response) {
    	ModelAndView model = new ModelAndView("addOnlineCourse");
    	 Collection <OnlineCourseType> onlineCourseType = onlineCourseService.getAllKindOfCourseWithCount();
         model.addObject("onlineCourseType",onlineCourseType);
	return model;
    }
    
      @RenderMapping(params = "view=editOnlineCourse")
    public ModelAndView showEditOnlineCourse(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("editOnlineCourse");
        int ID = Integer.valueOf(request.getParameter("id"));
        OnlineCourse onlineCourse = onlineCourseService.getOnlineCourseById(ID);
        ImageImpl mImage = onlineCourse.getMainImage();
        String mainImageUrl = (mImage == null) ? MAIN_IMAGE_MOCK_URL : imageService.getPathToLargeImage(mImage, onlineCourse);
        model.getModelMap().addAttribute("onlineCourse", onlineCourse);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        Collection <OnlineCourseType> onlineCourseType = onlineCourseService.getAllOnlineCourseType();
        model.addObject("onlineCourseType",onlineCourseType);
        return model;
    }
    
    @ActionMapping(value = "saveOnlineCourse")
    public void addOnlineCourse(@ModelAttribute("onlineCourse") @Valid OnlineCourse onlineCourse,
                        BindingResult bindingResult,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse,
                        @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                        SessionStatus sessionStatus) throws IOException {
    	if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        	if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, NO_IMAGE);
            return;
        }
        CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                        Integer.parseInt(actionRequest.getParameter("l")),
                                                        Integer.parseInt(actionRequest.getParameter("w")),
                                                        Integer.parseInt(actionRequest.getParameter("h")));
        
        if (mainImage.getSize() > 0) {
            imageService.saveMainImage(f, onlineCourse);
        	}
        	onlineCourseService.addOnlineCourse(onlineCourse);
            actionResponse.setRenderParameter("view", "allOnlineCourses");
            sessionStatus.setComplete();
        
    }

    @ActionMapping(value="editOnlineCourse")
    public void editOnlineCourse(@ModelAttribute("onlineCourse") @Valid OnlineCourse onlineCourse,
                         BindingResult bindingResult,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse,
                         @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                         SessionStatus sessionStatus) throws IOException {
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        CommonsMultipartFile croppedImage = null;
        OnlineCourse oldCourse = onlineCourseService.getOnlineCourseById(onlineCourse.getId());
        oldCourse.setOnlineCourseDescription(onlineCourse.getOnlineCourseDescription());
        oldCourse.setOnlineCourseName(onlineCourse.getOnlineCourseName());
        oldCourse.setOnlineCourseType(onlineCourse.getOnlineCourseType());
        if (!mainImage.getOriginalFilename().equals("")) {
        	croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                               Integer.parseInt(actionRequest.getParameter("l")),
                                               Integer.parseInt(actionRequest.getParameter("w")),
                                               Integer.parseInt(actionRequest.getParameter("h")));
        }
        if (mainImage.getSize() > 0) {
            imageService.saveMainImage(croppedImage, oldCourse);
        }
        onlineCourseService.updateOnlineCourse(oldCourse);
        actionResponse.setRenderParameter("view", "allOnlineCourses");
            sessionStatus.setComplete();
    }
    
    @RenderMapping(params="view=onlineCoursesCategories")
    public ModelAndView viewOnlineCoursesCategories(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewCoursesCategories");
        List<OnlineCourseType> onlineCourseTypes = onlineCourseService.getAllKindOfCourseWithCount();
        model.addObject("KindOfCourses",onlineCourseTypes);
        model.addObject("classOfCourses",ONLINE_COURSE);
        return model;
    }

    /**
     * @param response
     * @param request
     * @param nameKindOfCourse name
     * @return ModelAndView
     */
    @ResourceMapping(value = "addOnlineKind")
    public void addKindOfOnlineCourse(ResourceResponse response, ResourceRequest request,
                                String nameKindOfCourse) {
        onlineCourseService.addOnlineCourseType(new OnlineCourseType(nameKindOfCourse));
    }

    /**
     * @param response
     * @param request
     * @param kindOfCourseId      long Id of OnlineCourseType
     * @param nameKindOfCourse new name for current ID
     * @return ModelAndView
     */
    @ResourceMapping(value = "editOnlineKind")
    public void editKindOfCourse(ResourceResponse response, ResourceRequest request,
                                 Long kindOfCourseId,
                                 String nameKindOfCourse) {
        OnlineCourseType onlineCourseType = onlineCourseService.getOnlineCourseTypeById(kindOfCourseId);
        onlineCourseType.setKindOfCourse(nameKindOfCourse);
        onlineCourseService.updateOnlineCourseType(onlineCourseType);
    }

    @ResourceMapping(value = "removeOnlineKind")
    public void removeKindOfCourse(ResourceResponse response, ResourceRequest request,
                                   Long kindOfCourseId) {
        onlineCourseService.deleteOnlineCourseType(kindOfCourseId);
    }

}
