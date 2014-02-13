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

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import ua.dp.stud.StudPortalLib.dto.NewsDto;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;
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
    private static final String STR_FAIL = "failOnline";
    private static final String NO_IMAGE = "no-images";
    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static final int COURSES_BY_PAGE = 10;
    private static final int NEARBY_PAGES = 2;
    private static final int OVERALL_PAGES = 7;
    private static final String CURRENT_PAGE = "currentPage";
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
        model.setViewName("viewAllOnlineCourses");
        
        Integer pagesCount = onlineCourseService.getPagesCount(COURSES_BY_PAGE);
        Integer currentPage;
        if ((request.getParameter(CURRENT_PAGE) != null) && ("next".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage < pagesCount) {
                currentPage++;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("prev".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage > 1) {
                currentPage--;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("temp".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else{
            currentPage = 1;
        }
        
        Collection<OnlineCourse> onlineCourses = onlineCourseService.getOnlineCoursesOnPage(currentPage, COURSES_BY_PAGE);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("coursesByPage", COURSES_BY_PAGE);
        
    
        List<OnlineCourseType> onlineCourseTypes = onlineCourseService.getAllOnlineCourseType();
        model.addObject("onlineCourseTypes", onlineCourseTypes);
        model.addObject("onlineCourses", onlineCourses);
        String[] names = onlineCourseService.getAutocomplete();
        model.addObject("names",names);
        createPagination(model, currentPage, pagesCount);
        return model;
    }

    private void createPagination(ModelAndView model, Integer currentPage, Integer pagesCount) {
        Integer leftPageNumb = Math.max(1, currentPage - NEARBY_PAGES),
                rightPageNumb = Math.min(pagesCount, currentPage + NEARBY_PAGES);
        Boolean skippedBeginning = false,
                skippedEnding = false;

        if (pagesCount <= OVERALL_PAGES) {
            //all pages are shown
            leftPageNumb = 1;
            rightPageNumb = pagesCount;
        } else {
            //if farther then page #1 + '...' + nearby pages
            if (currentPage > 2 + NEARBY_PAGES) {
                // will look like 1 .. pages
                skippedBeginning = true;
            } else {
                //shows all first pages
                leftPageNumb = 1;
                //#1 + nearby pages + current + nearby pages
                rightPageNumb = currentPage+NEARBY_PAGES;
            }
            //if farther then nearby + '...' + last
            if (currentPage < pagesCount - (NEARBY_PAGES + 1)) {
                //will look like pages .. lastPage
                skippedEnding = true;
            } else {
                //shows all last pages:
                leftPageNumb = currentPage  -  NEARBY_PAGES;
                rightPageNumb = pagesCount;
            }
        }
        model.addObject("nearbyPages", NEARBY_PAGES);
        model.addObject("overallPages", OVERALL_PAGES);
        model.addObject("leftPageNumb", leftPageNumb);
        model.addObject("rightPageNumb", rightPageNumb);
        model.addObject("skippedBeginning", skippedBeginning);
        model.addObject("skippedEnding", skippedEnding);
    }

    @ActionMapping(value = "searchOnlineCourses")
    public void viewAllOnlineFoundCourses(ActionRequest request, ActionResponse response) {
    	response.setRenderParameter("title", request.getParameter("title"));
    	response.setRenderParameter("view", "allOnlineFoundCourses");
    	response.setRenderParameter("switch", "title");
    }
   
    @ActionMapping(value = "getOnlineCoursesByType")
    public void viewFoundByTypeOnlineCourses(ActionRequest request, ActionResponse response) {
    	response.setRenderParameter("type", request.getParameter("type"));
    	response.setRenderParameter("view", "allOnlineFoundCourses");
    	response.setRenderParameter("switch", "type");
    }
    
    @RenderMapping(params = "view=allOnlineFoundCourses")
    public ModelAndView renderAllOnlineFoundCourses(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        List<OnlineCourseType> onlineCourseTypes = onlineCourseService.getAllOnlineCourseType();
        model.setViewName("viewAllOnlineCourses");
        
        Integer pagesCount = onlineCourseService.getPagesCount(COURSES_BY_PAGE);
        Integer currentPage;
        if ((request.getParameter(CURRENT_PAGE) != null) && ("next".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage < pagesCount) {
                currentPage++;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("prev".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage > 1) {
                currentPage--;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("temp".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else{
            currentPage = 1;
        }
        
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("coursesByPage", COURSES_BY_PAGE);
        
        model.addObject("onlineCourseTypes", onlineCourseTypes);
        List<OnlineCourse> onlineCourses=null;
        if (request.getParameter("switch").equals("title")){
        	onlineCourses = onlineCourseService.getOnlineCourseByTitle(request.getParameter("title"),currentPage, COURSES_BY_PAGE);
        }
        else if (request.getParameter("switch").equals("type")){
        	onlineCourses = onlineCourseService.getOnlineCourseByType(Long.parseLong(request.getParameter("type")),currentPage, COURSES_BY_PAGE);
        }
        model.addObject("onlineCourses", onlineCourses);
        String[] names = onlineCourseService.getAutocomplete();
        model.addObject("names",names);
        createPagination(model, currentPage, pagesCount);
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
            actionResponse.setRenderParameter(STR_FAIL, "found");
            return;
        }
        	if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, "image");
            actionResponse.setRenderParameter("no", "image");
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

    @RenderMapping(params = "failOnline=image")
	public ModelAndView showAddFailed(RenderRequest request,
			RenderResponse response) {
    	System.out.println("yahoo");
		ModelAndView model = new ModelAndView("addOnlineCourse");
		SessionErrors.add(request, request.getParameter("no"));
        Collection<OnlineCourseType> kindOfCourses = onlineCourseService.getAllKindOfCourseWithCount();
        model.addObject("onlineCourseType", kindOfCourses);
		return model;
	}
	
}
