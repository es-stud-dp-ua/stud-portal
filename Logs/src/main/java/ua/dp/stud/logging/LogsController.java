package ua.dp.stud.logging;


import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.util.OrganizationType;
import ua.dp.stud.StudPortalLib.service.OrganizationService;
import ua.dp.stud.StudPortalLib.util.ImageService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public ModelAndView showView1(RenderRequest request, RenderResponse response) throws Exception{
        String realPath = request.getPortletSession().getPortletContext().getRealPath(File.separator);
        System.out.println(realPath);
        //return new ModelAndView("aaa");
        File file = new File(realPath);
        String pathToTomcat = file.getParentFile().getParentFile().getAbsolutePath();
        File catalina = new File(pathToTomcat+"/logs/catalina.out");

        ReversedLinesFileReader reader = new ReversedLinesFileReader(catalina);

        List<String> lines = new LinkedList<String>();

        int i = (request.getParameter("linesNumber") != null) ? Integer.valueOf(request.getParameter("linesNumber")) : 200;
        while (i > -1) {
            String line = reader.readLine();
            if (line == null){break;}
            lines.add(0,reader.readLine());
            i--;
        }
        ModelAndView mav = new ModelAndView("aaa");
        mav.addObject("lines", lines);
        return mav;
    }

    @ActionMapping
    public void showPage(ActionRequest request, ActionResponse response) {
        System.out.println("awdfsafsafsafsafdasfsadfasfd");
        System.out.println(request.getParameter("linesNumber"));
        response.setRenderParameter("linesNumber", request.getParameter("linesNumber"));
        /*int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
        } */
    }

}
