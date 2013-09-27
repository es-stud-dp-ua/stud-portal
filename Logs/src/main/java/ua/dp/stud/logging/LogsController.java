package ua.dp.stud.logging;


import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.File;

@Controller
@RequestMapping(value = "view")
public class LogsController {


    /**
     * Method for rendering view mode
     *
     * @param request
     * @param response
     * @return
     * @throws javax.portlet.PortletModeException
     *
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) throws Exception {
        String realPath = request.getPortletSession().getPortletContext().getRealPath(File.separator);
        File file = new File(realPath);
        String pathToTomcat = file.getParentFile().getParentFile().getAbsolutePath();
        File catalina = new File(pathToTomcat + "/logs/catalina.out");

        ReversedLinesFileReader reader = new ReversedLinesFileReader(catalina);
        StringBuffer sb = new StringBuffer();

        int i = (request.getParameter("linesNumber") != null) ? Integer.valueOf(request.getParameter("linesNumber")) : 200;
        while (i-- > -1) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            sb.insert(0, line + "<br>");
        }
        ModelAndView mav = new ModelAndView("aaa");
        mav.addObject("lines", sb.toString());
        return mav;
    }

    @ActionMapping
    public void showPage(ActionRequest request, ActionResponse response) {
        System.out.println(request.getParameter("linesNumber"));
        response.setRenderParameter("linesNumber", request.getParameter("linesNumber"));
        /*int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
        } */
    }

}
