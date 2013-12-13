package ua.dp.stud.studie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import ua.dp.stud.StudPortalLib.service.CourseService;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;
import ua.dp.stud.studie.service.OnlineCourseService;

import javax.portlet.*;
import java.util.List;

/**
 * Created by Kstin777 on 10.12.13.
 */
@Controller(value = "OnlineCoursesController")
@RequestMapping(value = "VIEW")
public class OnlineCourseController {

    private static final String ONLINE_COURSE = "onlineCourse";

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
