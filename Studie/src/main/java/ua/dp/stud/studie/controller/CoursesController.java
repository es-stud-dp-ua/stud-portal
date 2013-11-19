package ua.dp.stud.studie.controller;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.Course;
import ua.dp.stud.studie.model.CoursesType;
import ua.dp.stud.studie.model.KindOfCourse;
import ua.dp.stud.studie.service.CourseService;
import ua.dp.stud.studie.service.StudieService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller(value = "CoursesController")
@RequestMapping(value = "VIEW")

public class CoursesController {

    private static final String MAIN_IMAGE_MOCK_URL = "images/no-logo-study.jpg";
    private static final String BUTTON_ID = "buttonId";
    private static final String COURSE = "course";
    private static final String MAIN_IMAGE = "mainImage";
    private static final String STR_FAIL = "fail";
    private static final String ADMIN = "Administrator";
    private static final String STR_NO_IMAGE = "no images";
    private static final Logger LOG = Logger.getLogger(StudieController.class.getName());
    private static final String COURSE_ID = "courseId";
    private static final String STR_EXEPT = "Exception ";
    private static List<CoursesType> coursesType = Arrays.asList(CoursesType.values());

    @Autowired
    @Qualifier(value = "courseService")
    private CourseService courseService;

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @ModelAttribute(value = COURSE)
    public Course getCommandObject() {
        return new Course();
    }

    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RenderMapping(params = "view=allcourses")
    public ModelAndView viewAllCourses(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        Integer buttonId;
        if (request.getParameter(BUTTON_ID) == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter(BUTTON_ID));
        }
        List<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourse();
        model.setViewName("viewAllCourses");
        model.addObject("kindOfCourses", kindOfCourses);
        model.addObject("coursesType", coursesType);
        List<Course> courses = courseService.getAll();
        model.addObject("courses", courses);
        model.addObject(BUTTON_ID, buttonId);
        return model;
    }

    public void InitKindOfCourses()
    {
        KindOfCourse kindOfCourse1= new KindOfCourse("Английский язык");
        KindOfCourse kindOfCourse2= new KindOfCourse("Китайский язык");
        KindOfCourse kindOfCourse4= new KindOfCourse("Карате");
        KindOfCourse kindOfCourse3= new KindOfCourse("Математика");

        courseService.addKindOfCourse(kindOfCourse1);
        courseService.addKindOfCourse(kindOfCourse2);
        courseService.addKindOfCourse(kindOfCourse3);
        courseService.addKindOfCourse(kindOfCourse4);

    }
    
    public void InitCourseName()
    {
        Course course1= new Course("bla1");
        Course course2= new Course("bla2");
        Course course3= new Course("bla3");

        courseService.addCourse(course1);
        courseService.addCourse(course2);
        courseService.addCourse(course3);

    }

    @ResourceMapping(value = "coursesByKindAndType")
    public void renderCourses(ResourceResponse response,  ResourceRequest request,
                           @RequestParam(required = true) String kindOfCourse,
                           @RequestParam(required = true) String coursesType) throws Exception
    {
        StringBuilder s = new StringBuilder();
        List<Course> courses = courseService.getCoursesByKindAndType(kindOfCourse, coursesType);
        //s.append(kindOfCourse); s.append(" "); s.append(coursesType);
        for (Course c:courses) {
            PortletURL p = response.createRenderURL();
            p.setParameter("courseId", c.getId().toString());
            p.setParameter("view", "course");
            s.append("<p><a href='").append(p.toString()).append("'>").append(c.getCourseName()).append("</a></p>");
        }
        //response.createRenderURL();
        response.getWriter().println(s);
    }


    @RenderMapping(params = "add=course")
    public ModelAndView addCourse(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addCourse");
        Collection<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourse();
        model.addObject("kindOfCourse", kindOfCourses);
        model.addObject("coursesType", coursesType);
        return model;
    }

    @RenderMapping(params = "showEdit=course")
    public ModelAndView showEditCourse(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("editCourse");
        Collection<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourse();
        int courseID = Integer.valueOf(request.getParameter(COURSE_ID));
        Course course = courseService.getCourseByID(courseID);
        ImageImpl mImage = course.getMainImage();
        String mainImageUrl =imageService.getPathToLargeImage(mImage,course);
        model.addObject("mainImage", mainImageUrl);
        model.addObject(COURSE, course);
        model.addObject("kindOfCourse", kindOfCourses);
        model.addObject("coursesType", coursesType);
        return model;
    }

    @ActionMapping(value = "editCourse")
    public void editCourse(@ModelAttribute(COURSE) Course course,
                           ActionRequest actionRequest,
                           ActionResponse actionResponse,
                           SessionStatus sessionStatus,
                           @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage) {


        Course oldCourse = courseService.getCourseByID(course.getId());
        course.setMainImage(oldCourse.getMainImage());
        CommonsMultipartFile croppedImage=null;
        if (!mainImage.getOriginalFilename().equals("")) {
        	croppedImage = imageService.cropImage(mainImage,
                    Integer.parseInt(actionRequest.getParameter("t")),
                    Integer.parseInt(actionRequest.getParameter("l")),
                    Integer.parseInt(actionRequest.getParameter("w")),
                    Integer.parseInt(actionRequest.getParameter("h")));
        }

        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String screenName = user.getScreenName();
        course.setAuthorslogin(screenName);

        if (updateCourse(course, croppedImage, actionResponse)) {
            courseService.updateCourse(course);
            actionResponse.setRenderParameter(COURSE_ID, Integer.toString(course.getId()));
            sessionStatus.setComplete();
        }


    }

    @RenderMapping(params = "view=course")
    public ModelAndView viewCourse(RenderRequest request, RenderResponse response) {
        int courseID = Integer.valueOf(request.getParameter(COURSE_ID));
        Integer buttonId = 0;
        if (request.getParameter(BUTTON_ID) == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter(BUTTON_ID));
        }
        Course course = courseService.getCourseByID(courseID);
        ImageImpl mImage = course.getMainImage();
        String mainImageUrl;
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, course);
        }

        User user = (User) request.getAttribute(WebKeys.USER);
        boolean isShown=false;
        if (request.isUserInRole(ADMIN) || request.isUserInRole("User"))
        {
            if(request.isUserInRole(ADMIN)){
                isShown=true;
                }
            else{
            if(request.isUserInRole("User") && course.getAuthorslogin().equals(user.getScreenName())){
                isShown=true;
            }
            }
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("viewCourse");
        model.addObject("course", course);
        model.addObject("isShown",isShown);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject(BUTTON_ID, buttonId);
        return model;

    }

    @ActionMapping(params = "delete=course")
    public void deleteCourse(ActionRequest request, ActionResponse response) {

        int courseID = Integer.valueOf(request.getParameter("id"));
        Course course = courseService.getCourseByID(courseID);
        imageService.deleteDirectory(course);
        courseService.deleteCourse(course.getId());
        response.setRenderParameter("view","allcourses");
    }

    @ActionMapping(value = "saveCourse")
    public void saveCourse() {
    }

    @RenderMapping(params="view=coursesCategories")
    public ModelAndView viewCoursesCategories(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewCoursesCategories");
        List<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourseWithCount();
        model.addObject("KindOfCourses",kindOfCourses);
        return model;
    }

    /**
     * @param response
     * @param request
     * @param nameKindOfCourse name
     * @return ModelAndView
     */
    @ResourceMapping(value = "addKind")
    public void addKindOfCourse(ResourceResponse response, ResourceRequest request,
                                 String nameKindOfCourse) {
        courseService.addKindOfCourse(new KindOfCourse(nameKindOfCourse));
    }

    /**
     * @param response
     * @param request
     * @param kindOfCourseId      long Id of KindOfCourse
     * @param nameKindOfCourse new name for current ID
     * @return ModelAndView
     */
    @ResourceMapping(value = "editKind")
    public void editKindOfCourse(ResourceResponse response, ResourceRequest request,
                                 Integer kindOfCourseId,
                                 String nameKindOfCourse) {
        KindOfCourse kindOfCourse = courseService.getKindOfCourseById(kindOfCourseId);
        kindOfCourse.setKindOfCourse(nameKindOfCourse);
        courseService.updateKindOfCourse(kindOfCourse);
    }

    @ResourceMapping(value = "removeKind")
    public void removeKindOfCourse(ResourceResponse response, ResourceRequest request,
                                   Integer kindOfCourseId) {
        courseService.deleteKindOfCourse(kindOfCourseId);
    }


    private Boolean updateCourse(Course newCourse, CommonsMultipartFile mainImage,
                                ActionResponse actionResponse) {
        boolean successUpload = true;
        try {
            if (mainImage.getSize() > 0){
                imageService.saveMainImage(mainImage, newCourse);
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            actionResponse.setRenderParameter(STR_EXEPT, "error");
            successUpload = false;
        }

        return successUpload;
    }

	@ActionMapping(value="saveNewCourse")
	public void saveNewCourse(@ModelAttribute(COURSE)  Course course,
                              ActionRequest actionRequest,
                              ActionResponse actionResponse,
                              SessionStatus sessionStatus,
                              @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage)
    {
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
            return;
        }

        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String screenName = user.getScreenName();


        course.setAuthorslogin(screenName);


        CommonsMultipartFile f = imageService.cropImage(mainImage,
                Integer.parseInt(actionRequest.getParameter("t")),
                Integer.parseInt(actionRequest.getParameter("l")),
                Integer.parseInt(actionRequest.getParameter("w")),
                Integer.parseInt(actionRequest.getParameter("h"))  );
        if (updateCourse(course, f, actionResponse)) {
            courseService.addCourse(course);
            actionResponse.setRenderParameter(COURSE_ID, Integer.toString(course.getId()));
            sessionStatus.setComplete();
        }

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("mainImage");

    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        Integer buttonId;
        if (request.getParameter(BUTTON_ID) == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter(BUTTON_ID));
        }
        model.setViewName("viewAll");
        Collection<Course> course = courseService.getAll();
        model.addObject("course", course);
        model.addObject(BUTTON_ID, buttonId);
        return model;
    }

}