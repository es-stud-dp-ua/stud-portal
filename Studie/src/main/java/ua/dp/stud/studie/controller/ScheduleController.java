package ua.dp.stud.studie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.util.FileService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


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

@RenderMapping(params = "view=upload")
public String uploadFiles(RenderRequest request, RenderResponse response)
{
    String path = (String)request.getParameter("filepath");

//    fileService.uploadFile();
    return "viewUpload";
}

}
