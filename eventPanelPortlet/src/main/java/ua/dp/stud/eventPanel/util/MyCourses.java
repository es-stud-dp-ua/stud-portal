package ua.dp.stud.eventPanel.util;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.service.CourseService;

import java.util.Collection;

/**
 * @author Nazarenko Kostya
 */
public class MyCourses extends State {

    public MyCourses(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        CourseService service = helper.getCourseService();
        return service.getPagesCountByAuthor(helper.getUserName(), 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        CourseService service = helper.getCourseService();
        String userName = helper.getUserName();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCountByAuthor(userName, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Course> coursesList = service.getPagesCourseByAuthor(userName, newCurrentPage,
                PER_PAGE);
        model.addObject("coursesList", coursesList);
        model.addObject(TYPE, "Courses");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }
}
