package ua.dp.stud.studie.controller;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.util.PortalUtil;
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
import ua.dp.stud.StudPortalLib.model.FileSaver;
import ua.dp.stud.studie.model.*;
import ua.dp.stud.StudPortalLib.util.FileService;
import ua.dp.stud.studie.service.FacultiesService;
import ua.dp.stud.studie.service.ScheduleService;
import ua.dp.stud.studie.service.StudieService;

import javax.portlet.*;
import java.io.File;
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

    @ModelAttribute(value = "schedule")
    public Schedule getCommandObject() {
        return new Schedule();
    }



    @ActionMapping(value = "uploadSchedule")
        public void uploadFiles(@ModelAttribute() Schedule schedule,
                ActionRequest actionRequest,
                ActionResponse actionResponse,
                SessionStatus sessionStatus,
                @RequestParam(FILESCHEDULE) CommonsMultipartFile scheduleFile) throws IOException
    {

        schedule.setFaculty(facultiesService.getFacultyByID(schedule.getFaculty().getId()));
        FileSaver file = fileService.uploadFile(schedule.getScheduleFile(), scheduleFile, schedule);
        schedule.setFile(file);
        scheduleService.addSchedule(schedule);

        actionResponse.setRenderParameter("status", "true");
        actionResponse.setRenderParameter("view","schedule");

    }


    @RenderMapping(params = "view=schedule")
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        boolean status = false;
        status=Boolean.valueOf(request.getParameter("status"));
        Collection<Studie> studies = studieService.getAllStudies();
        model.addObject("study", studies);

        model.setViewName("schedule");
        model.addObject("status",status);
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
    public void getSchedule( ResourceResponse response,  ResourceRequest request,
                            @RequestParam(required = true) Integer facultyId,
                            @RequestParam(required = true) String year) throws Exception
    {
        StringBuilder s = new StringBuilder();
        String pathToFile="";
        Faculties faculty = facultiesService.getFacultyByID(facultyId);
        Schedule schedule1 = scheduleService.getScheduleByFacultyAndYear(faculty, Course.valueOf(year));
        pathToFile = fileService.downloadFile(schedule1.getScheduleFile(), schedule1);
        s.append(pathToFile);
        response.getWriter().println(s);
    }
}
