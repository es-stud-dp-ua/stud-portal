package ua.dp.stud.studie.controller;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;

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
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller(value="CoursesController")
@RequestMapping(value = "VIEW")

public class CoursesController {

    private static final String BUTTON_ID = "buttonId";
    private static final String COURSE = "course";
    private static final String MAIN_IMAGE ="mainImage";
    private static final String STR_FAIL = "fail";
    private static final String STR_NO_IMAGE = "no images";
    private static final Logger LOG = Logger.getLogger(StudieController.class.getName());
    private static final String COURSE_ID = "courseID";
    private static final String STR_EXEPT = "Exception ";
    private static List<CoursesType> coursesType = Arrays.asList(CoursesType.values());

    @Autowired
    @Qualifier(value = "courseService")
    private CourseService courseService;


    public void setCourseService(CourseService courseService)
    {
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

    @RenderMapping(params="view=allcourses")
	public ModelAndView viewAllCourses(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        Integer buttonId;
        if (request.getParameter(BUTTON_ID) == null) {
            buttonId = 0;
        } else {
            buttonId = Integer.valueOf(request.getParameter(BUTTON_ID));
        }
        //InitKindOfCourses();
        //InitCourseName();
        List<KindOfCourse> kindOfCourses = courseService.getAllKindOfCourse();
        model.setViewName("viewAllCourses");
        model.addObject("kindOfCourses", kindOfCourses);
        model.addObject("coursesType", coursesType);
        List<Course> courses = courseService.getAll();
        model.addObject("courses", courses);
        model.addObject(BUTTON_ID, buttonId);
        return model;
		//return "viewAllCourses";
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

    private Map<String, List<String>> formParamMap;
    private static final List<String> status;
    private static final List<String> types = new ArrayList<String>();


    static {
        status = Arrays.asList("Компания", "Репетирор", "Онлайн");

    }

    private void setMap(RenderRequest request) {
        formParamMap = new HashMap<String, List<String>>();
    }

	@RenderMapping(params="add=course")
	public ModelAndView addCourse(RenderRequest request, RenderResponse response)
    {
        //InitKindOfCourses();
        ModelAndView model=new ModelAndView("addCourse");
        Collection<KindOfCourse> kindOfCourses= courseService.getAllKindOfCourse() ;
        for (KindOfCourse u : kindOfCourses)
        {
            types.add(u.toString());
        }
        model.addObject("kindOfCourses",types);
        model.addObject("statusList", status);
        setMap(request);
		return  model;
	}

	@RenderMapping(params="edit=course")
	public void editCourse(@ModelAttribute(COURSE)  Course course,
                             //BindingResult bindingResult,
                             ActionRequest actionRequest,
                             ActionResponse actionResponse,
                             SessionStatus sessionStatus,
                             @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage)
    {

         /*    if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }     */

        Course oldCourse = courseService.getCourseByID(course.getId());
        course.setMainImage(oldCourse.getMainImage());
        if(!mainImage.getOriginalFilename().equals(""))
        {
            mainImage=imageService.cropImage(mainImage,
                    Integer.parseInt(actionRequest.getParameter("t")),
                    Integer.parseInt(actionRequest.getParameter("l")),
                    Integer.parseInt(actionRequest.getParameter("w")),
                    Integer.parseInt(actionRequest.getParameter("h"))  );
        }
        if (updateCourse(course, mainImage, actionResponse))
        {
            courseService.deleteKindOfCourse(oldCourse.getKindOfCourse().gettypeId());
            courseService.updateCourse(course);
            actionResponse.setRenderParameter(COURSE_ID, Integer.toString(course.getId()));
            sessionStatus.setComplete();
        }

	}


	@RenderMapping(params="view=course")
	public String viewCourse() {
		return "viewCourse";
	}
	
	@RenderMapping(params="delete=course")
	public String deleteCourse() {
		return "viewAllCourse";
	}
	@ActionMapping(value="saveCourse")
	public void saveCourse(){
	}


    private Boolean updateCourse(Course newCourse, CommonsMultipartFile mainImage,
                                ActionResponse actionResponse) {
        boolean successUpload = true;
        try {
            if (mainImage.getSize() > 0)
                imageService.saveMainImage(mainImage, newCourse);


        } catch (Exception ex) {
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            actionResponse.setRenderParameter(STR_EXEPT, "error");
            successUpload = false;
        }

        return successUpload;
    }

	@ActionMapping(value="saveNewCourse")
	public void saveNewCourse(@ModelAttribute(COURSE)  Course course,
                              //BindingResult bindingResult,
                              ActionRequest actionRequest,
                              ActionResponse actionResponse,
                              SessionStatus sessionStatus,
                              @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage)
    {
    /*    if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.toString());
                System.out.println();
            }
        }
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }     */
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
            return;
        }
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
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   */
        binder.setDisallowedFields("mainImage");

    }


}