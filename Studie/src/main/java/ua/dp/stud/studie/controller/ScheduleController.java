package ua.dp.stud.studie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import ua.dp.stud.studie.model.Course;
import ua.dp.stud.StudPortalLib.util.FileService;
import ua.dp.stud.studie.model.Schedule;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.service.FacultiesService;
import ua.dp.stud.studie.service.ScheduleService;
import ua.dp.stud.studie.service.StudieService;

import javax.portlet.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**

 */
@Controller
@RequestMapping(value = "VIEW")
public class ScheduleController {

    private static final String FILESCHEDULE="fileschedule";

    @Autowired
    @Qualifier(value =  "fileService")
    private FileService fileService;

    void setFileService(FileService fileService)
    {
        this.fileService=fileService;
    }

    @Autowired
    @Qualifier(value =  "scheduleService")
    private ScheduleService scheduleService;

    void setScheduleService(ScheduleService scheduleService)
    {
        this.scheduleService=scheduleService;
    }

    @Autowired
    @Qualifier(value = "studieService")
    private StudieService studieService;

    public void setStudieService(StudieService studieService) {
        this.studieService = studieService;
    }

    @Autowired
    @Qualifier(value = "facultiesService")
    private FacultiesService facultiesService;

    public void setService(FacultiesService facultiesService) {
        this.facultiesService = facultiesService;
    }

    @RenderMapping(params = "view=download")
    public ModelAndView downloadFiles(RenderRequest request, RenderResponse response) throws IOException
    {
        ModelAndView model = new ModelAndView("schedule");
        Faculties faculty=facultiesService.getFacultyByID(Integer.parseInt(request.getParameter("faculty_id")));
        Course year=Course.valueOf(request.getParameter("year"))  ;
        Schedule schedule=scheduleService.getScheduleByFacultyAndYear(faculty,year);
        String pathToFile=fileService.downloadFile(schedule.getScheduleFile(),schedule);
        model.addObject("pathToFile",pathToFile);
        return model;
    }
    @ActionMapping(value = "uploadSchedule")
        public void uploadFiles(@ModelAttribute() Schedule schedule,
                ActionRequest actionRequest,
                ActionResponse actionResponse,
                SessionStatus sessionStatus,
                @RequestParam(FILESCHEDULE) CommonsMultipartFile scheduleFile) throws IOException
    {
        schedule.setFaculty(facultiesService.getFacultyByID(Integer.parseInt(actionRequest.getParameter("faculty_id"))));
        schedule.setYear(ua.dp.stud.studie.model.Course.valueOf(actionRequest.getParameter("year")));
        fileService.uploadFile(schedule.getScheduleFile(),scheduleFile,schedule);
    }

    @RenderMapping(params = "view=schedule")
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        Collection<Studie> studies = studieService.getAllStudies();
        model.addObject("study", studies);
        model.setViewName("schedule");
        return model;
    }

    @ResourceMapping(value = "facultiesByStudy")
    public void renderFaculties(ResourceResponse response,  ResourceRequest request,
                                @RequestParam(required = true) Integer studyId) throws Exception
    {
        StringBuilder s = new StringBuilder();
        List<Faculties> faculties = studieService.getStudieById(studyId).getFaculties();
        for (Faculties f:faculties) {
            s.append("<option value='").append(f.getId()).append("'>").append(f.getNameOfFaculties()).append("</option>");
        }
        response.getWriter().println(s);
    }

    @ResourceMapping(value = "getSchedule")
    public void getSchedule(ResourceResponse response,  ResourceRequest request,
                            @RequestParam(required = true) Integer facultyId,
                            @RequestParam(required = true) String year) throws Exception
    {
        StringBuilder s = new StringBuilder();
        Faculties faculty = facultiesService.getFacultyByID(facultyId);
        Course course = Course.valueOf(year);
        Schedule schedule = scheduleService.getScheduleByFacultyAndYear(faculty, course);
        String pathToFile = fileService.downloadFile(schedule.getScheduleFile(), schedule);
        s.append(pathToFile);
        response.getWriter().println(s);
    }
}
