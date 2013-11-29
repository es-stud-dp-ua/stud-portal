package ua.dp.stud.eventPanel.util;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.service.CourseService;

import java.util.Collection;

/**
 * @author Nazarenko Kostya
 */

public class AdminCourses extends State{

    public AdminCourses(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        CourseService service = helper.getCourseService();
        return service.getPagesCount(false, 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        CourseService service = helper.getCourseService();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCount(false, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Course> orgList = service.getCoursesOnPage(false, newCurrentPage, PER_PAGE);
        model.addObject("coursesList", orgList);
        model.addObject(TYPE, "Courses");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }

    @Override
    public void approve() {
        Course currentCourse = helper.getCourseService().getCourseByID(helper.getObjectId());
        String comment = helper.getComment();
        if (comment.length() > 0) {
            currentCourse.setComment(comment);
        }
        currentCourse.setApproved(helper.getApproved());
        helper.getCourseService().updateCourse(currentCourse);
    }
}
