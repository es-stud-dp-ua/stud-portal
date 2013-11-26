package ua.dp.stud.studie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.util.FileService;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.service.FacultiesService;
import ua.dp.stud.studie.service.StudieService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Collection;


/**

 */
@Controller
@RequestMapping(value = "VIEW")
public class ScheduleController {

    @Autowired
    @Qualifier(value =  "fileService")
    private FileService fileService;

    void setFileService(FileService fileService)
    {
        this.fileService=fileService;
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

    @RenderMapping(params = "view=upload")
    public String uploadFiles(RenderRequest request, RenderResponse response)
    {
        String path = (String)request.getParameter("filepath");
//    fileService.uploadFile();
        return "viewUpload";
    }

    @RenderMapping(params = "view=schedule")
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        Collection<Studie> studie = studieService.getAllStudies();
        model.addObject("study", studie);

        model.setViewName("schedule");
        return model;
    }

}
