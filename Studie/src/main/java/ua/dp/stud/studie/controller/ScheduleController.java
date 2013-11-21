package ua.dp.stud.studie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.util.FileService;
import ua.dp.stud.StudPortalLib.model.FileSaver;
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

    @RenderMapping(params="view=schedule")
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        return getMainView();
    }

    public ModelAndView getMainView() {

        ModelAndView model = new ModelAndView("viewSchedule");
        return  model;
    }

@RenderMapping(params = "view=upload")
public String uploadFiles(RenderRequest request, RenderResponse response) throws IOException {
    String path = (String)request.getParameter("filepath");
    FileSaver f=new FileSaver();
    fileService.uploadFile(f,"D:\\123.xls");
    fileService.downloadFile(f, "C:\\124.xls");
    return "viewUpload";
}

}
