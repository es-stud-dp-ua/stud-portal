package ua.dp.stud.studie.controller;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

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
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import ua.dp.stud.StudPortalLib.model.ImageImpl;

import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.model.CoursesType;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;
import ua.dp.stud.StudPortalLib.service.CourseService;
import ua.dp.stud.studie.model.Studie;

import javax.portlet.*;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller(value = "CoursesController")
@RequestMapping(value = "VIEW")

public class CoursesController {

    private static final String MAIN_IMAGE_MOCK_URL = "images/no-logo-study.jpg";
    private static final String BUTTON_ID = "buttonId";
    private static final String COURSE = "course";
    private static final String JSP_NAME="jsp";
    private static final String MAIN_IMAGE = "mainImage";
    private static final String STR_FAIL = "fails";
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
        List<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourse();
        model.setViewName("viewAllCourses");
        model.addObject("kindOfCourses", kindOfCourses);
        model.addObject("coursesType", coursesType);
        List<Course> courses = courseService.getAll();
        model.addObject("courses", courses);

        return model;
    }

    public void InitKindOfCourses() {
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
                           @RequestParam(required = true) String coursesType)
            throws Exception {
        StringBuilder s = new StringBuilder();
        List<Course> courses = courseService.getCoursesByKindAndType(kindOfCourse, coursesType);
        for (Course c:courses) {
            PortletURL p = response.createRenderURL();
            p.setParameter("courseId", c.getId().toString());
            p.setParameter("view", "course");
            s.append("<p><a href='").append(p.toString()).append("'>").append(c.getCourseName()).append("</a></p>");
        }
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
    public void editCourse(@ModelAttribute(COURSE) @Valid Course course,
                           BindingResult bindingResult,
                           ActionRequest actionRequest,
                           ActionResponse actionResponse,
                           SessionStatus sessionStatus,
                           @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage) throws IOException {

        actionResponse.setRenderParameter(JSP_NAME,"edit");
        actionResponse.setRenderParameter(COURSE_ID,course.getId().toString());
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter("coursefail", "found");
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }


        if (actionRequest.isUserInRole("Administrator"))
            course.setApproved(true);
        else   course.setApproved(false);

        Course oldCourse = courseService.getCourseByID(course.getId());
        if(courseService.isDuplicateTopic(course.getCourseName(),oldCourse.getId())){
            actionResponse.setRenderParameter("coursefail", "found");
            actionResponse.setRenderParameter(STR_FAIL, "dplTopic");
            return;
        }

        course.setMainImage(oldCourse.getMainImage());
        CommonsMultipartFile croppedImage=null;
        if (!"".equals(mainImage.getOriginalFilename())) {
        	croppedImage = imageService.cropImage(mainImage,
                    Integer.parseInt(actionRequest.getParameter("t")),
                    Integer.parseInt(actionRequest.getParameter("l")),
                    Integer.parseInt(actionRequest.getParameter("w")),
                    Integer.parseInt(actionRequest.getParameter("h")));
        }

        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String screenName = user.getScreenName();
        course.setAuthor(screenName);

        if (mainImage.getSize() > 0){
            imageService.saveMainImage(mainImage, course);
        }

        courseService.updateCourse(course);
            actionResponse.setRenderParameter(COURSE_ID, Integer.toString(course.getId()));
            actionResponse.setRenderParameter("view", "allcourses");
            SessionMessages.add(actionRequest,"successEdit");
            sessionStatus.setComplete();


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
        if (request.isUserInRole(ADMIN) || request.isUserInRole("User"))  {
            if(request.isUserInRole(ADMIN)){
                isShown=true;
                } else{
                    if(request.isUserInRole("User") && course.getAuthor().equals(user.getScreenName())){
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

        int courseID = Integer.valueOf(request.getParameter(COURSE_ID));
        Course course = courseService.getCourseByID(courseID);
        imageService.deleteDirectory(course);
        courseService.deleteCourse(course.getId());
        response.setRenderParameter("view","allcourses");
    }



    @RenderMapping(params="view=coursesCategories")
    public ModelAndView viewCoursesCategories(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewCoursesCategories");
        List<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourseWithCount();
        model.addObject("KindOfCourses",kindOfCourses);
        model.addObject("classOfCourses",COURSE);
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
                                 Long kindOfCourseId,
                                 String nameKindOfCourse) {
        KindOfCourse kindOfCourse = courseService.getKindOfCourseById(kindOfCourseId);
        kindOfCourse.setKindOfCourse(nameKindOfCourse);
        courseService.updateKindOfCourse(kindOfCourse);
    }

    @ResourceMapping(value = "removeKind")
    public void removeKindOfCourse(ResourceResponse response, ResourceRequest request,
                                   Long kindOfCourseId) {
        courseService.deleteKindOfCourse(kindOfCourseId);
    }



	@ActionMapping(value="saveNewCourse")
	public void saveNewCourse(@ModelAttribute(COURSE) @Valid Course course,
							  BindingResult bindingResult,
                              ActionRequest actionRequest,
                              ActionResponse actionResponse,
                              SessionStatus sessionStatus,
                              @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage) throws IOException {

        actionResponse.setRenderParameter(JSP_NAME,"add");
        if (bindingResult.hasErrors()) {
			actionResponse.setRenderParameter("coursefail", "found");
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        	if ("".equals(mainImage.getOriginalFilename())) {
        	actionResponse.setRenderParameter("coursefail", "found");
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
            return;
        }

        if(courseService.isDuplicateTopic(course.getCourseName(),null)){
            actionResponse.setRenderParameter("coursefail", "found");
            actionResponse.setRenderParameter(STR_FAIL, "dplTopic");
            return;
        }

        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String screenName = user.getScreenName();

        if (actionRequest.isUserInRole("Administrator"))
               course.setApproved(true);
        else   course.setApproved(false);

        course.setAuthor(screenName);

        CommonsMultipartFile f = imageService.cropImage(mainImage,
                Integer.parseInt(actionRequest.getParameter("t")),
                Integer.parseInt(actionRequest.getParameter("l")),
                Integer.parseInt(actionRequest.getParameter("w")),
                Integer.parseInt(actionRequest.getParameter("h"))  );
       
        if (mainImage.getSize() > 0){
            imageService.saveMainImage(mainImage, course);
        }
            courseService.addCourse(course);
            
            actionResponse.setRenderParameter("view", "course");
            actionResponse.setRenderParameter(COURSE_ID, Integer.toString(course.getId()));
            SessionMessages.add(actionRequest, "successAdd");
            sessionStatus.setComplete();
    }

	@RenderMapping(params = "coursefail=found")
	public ModelAndView showAddFailed(RenderRequest request,
			RenderResponse response) {
		ModelAndView model;
        if("edit".equals(request.getParameter(JSP_NAME))){
            model= showEditCourse(request, response);
        }else{
            model=addCourse(request, response);
        }
		SessionErrors.add(request, request.getParameter(STR_FAIL));
        Collection<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourse();
        model.addObject("kindOfCourse", kindOfCourses);
        model.addObject("coursesType", coursesType);
		return model;
	}

    @RenderMapping(params = "successCourse")
    public ModelAndView showAddSuccess(RenderRequest request,
                                       RenderResponse response) {
        ModelAndView model = viewCourse(request, response);
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("mainImage");

    }



}