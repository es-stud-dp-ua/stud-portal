package ua.dp.stud.eventPanel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.service.CourseService;
import ua.dp.stud.StudPortalLib.service.EventsService;
import ua.dp.stud.StudPortalLib.service.NewsService;
import ua.dp.stud.StudPortalLib.service.OrganizationService;

import javax.portlet.RenderRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pikus Vladislav
 */
@Component("eventPanelHelper")
public class EventPanelHelper {

    @Autowired
    @Qualifier(value = "courseService")
    private CourseService courseService;

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    @Autowired
    @Qualifier(value = "organizationService")
    private OrganizationService organizationService;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    @Autowired
    @Qualifier(value = "newsService")
    private NewsService newsService;

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    @Autowired
    @Qualifier(value = "eventsService")
    private EventsService eventsService;

    public void setEventsService(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    public EventsService getEventsService() {
        return eventsService;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private RenderRequest request;

    public RenderRequest getRequest() {

        return request;
    }

    public void setRequest(RenderRequest request) {
        this.request = request;
    }

    private Integer currentPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    private String direction;

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    private ModelAndView model;

    public ModelAndView getModel() {
        return model;
    }

    public void setModel(ModelAndView model) {
        this.model = model;
    }

    private Integer objectId;

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    private String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    private Boolean approved;

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getApproved() {
        return approved;
    }

    protected static final String NEWS_ARCHIVE_REFERENCE_NAME = "NewsArchive_WAR_studnewsArchive";
    protected static final String COMMUNITIES_REFERENCE_NAME = "Communities_WAR_studcommunity";
    private static final String EVENTS_REFERENCE_NAME = "Events_WAR_studevents";
    protected static final String COURSES_REFERENCE_NAME = "Studie_WAR_studstudie";

    private State state;
    private Map<String, State> stateMap;

    public EventPanelHelper() {
        stateMap = new HashMap<String, State>();
        stateMap.put("myNews", new MyNews(this, "myNewsSize", NEWS_ARCHIVE_REFERENCE_NAME));
        stateMap.put("myCommunity", new MyCommunity(this, "myOrgSize", COMMUNITIES_REFERENCE_NAME));
        stateMap.put("myEvents", new MyEvents(this, "myEventsSize", EVENTS_REFERENCE_NAME));
        stateMap.put("myCourses", new MyCourses(this, "myCoursesSize", COURSES_REFERENCE_NAME));
        stateMap.put("adminNews", new AdminNews(this, "adminNewsSize", NEWS_ARCHIVE_REFERENCE_NAME));
        stateMap.put("adminCommunity", new AdminCommunity(this, "adminOrgSize", COMMUNITIES_REFERENCE_NAME));
        stateMap.put("adminEvents", new AdminEvents(this, "adminEventsSize", EVENTS_REFERENCE_NAME));
        stateMap.put("adminCourses", new AdminCourses(this, "adminCoursesSize", COURSES_REFERENCE_NAME));
    }

    public ModelAndView getObjectsByPage() {
        return state.getObjectByPage();
    }

    public ModelAndView getPagesCount() {
        for(State item : stateMap.values()) {
            model.addObject(item.getCntDesc(), item.getPagesCount());
        }
        return model;
    }

    public void approve() {
        state.approve();
    }

    public void setState(String name) {
        state = stateMap.get(name);
    }
}