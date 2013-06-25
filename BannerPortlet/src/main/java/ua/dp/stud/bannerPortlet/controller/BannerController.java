package ua.dp.stud.bannerPortlet.controller;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
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
import ua.dp.stud.bannerPortlet.model.BannerImage;
import ua.dp.stud.bannerPortlet.service.BannerImageService;
import ua.dp.stud.StudPortalLib.util.ImageService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * @author Vladislav Pikus
 */

@Controller
@RequestMapping(value = "view")
public class BannerController
{
    //todo: use final key-word
    private static final Logger LOGGER = Logger.getLogger(BannerController.class.getName());
    private static String strFail = "fail";
    private static String strNoImage = "no-images";
    private static String strExept = "exception";
    private static String strDuplicat = "dplBanner";
    private static final int URLSIMBOL = 4;

    @Autowired
    @Qualifier(value = "bannerImageService")
    //todo: change variable`s name
    private BannerImageService service;

    public  void setService(BannerImageService service)
    {
        this.service = service;
    }
    //todo: it must be private method
    public boolean updateBannerImage(CommonsMultipartFile mainImage,
                                     String url, ActionResponse actionResponse, BannerImage banner)
    {
        if (url.length() < URLSIMBOL)
        {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        banner.setUrl(url);
        ImageService imageService = new ImageService();
        boolean successUpload = true;
        //todo: use one try block
        try
        {
            if (mainImage.getSize() > 0)
            {
                try
                {
                    imageService.saveMainImage(mainImage, banner);
                }
                //todo: catch IOException
                catch (Exception ex)
                {
                    LOGGER.warning("Error while uploading " + mainImage.getOriginalFilename());
                    successUpload = false;
                }
            }
        }
        catch (Exception ex)
        {
            //todo: why do we need string writer there?
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(strExept, sw.toString());
        }
        //success upload message
        if (successUpload)
        {
            actionResponse.setRenderParameter("success", "success-add");
            return true;
        } else {
            actionResponse.setRenderParameter(strFail, strNoImage);
        }
        return false;
    }

    /*
    * Method for rendering VIEW mode
    * @param request
    * @param response
    * @return PortletModeException
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response)
    {
        //todo: add view name as constructor arg to ModelAndView
        ModelAndView model = new ModelAndView();
        model.setViewName("view");
        //todo: change variable`s name
        Collection<BannerImage> banner = service.getAll();
        model.addObject("bannerImages", banner);

        return model;
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddBanner(RenderRequest request, RenderResponse response)
    {
        //todo: add view name as constructor arg to ModelAndView
        ModelAndView model = new ModelAndView();
        model.setViewName("addImages");
        return model;
    }

    @ActionMapping(value = "addImage")
    public void addImage(@RequestParam("ban-mainImage") CommonsMultipartFile mainImage,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException
    {   //todo: why do you throw IOException? just add it to catch block
        BannerImage banner = new BannerImage();
        String url = actionRequest.getParameter("url");
       if (this.updateBannerImage(mainImage, url, actionResponse, banner))
       {
           try
           {
               service.addBannerImage(banner);
               sessionStatus.setComplete();
           }
           catch (Exception ex)
           {
               StringWriter sw = new StringWriter();
               actionResponse.setRenderParameter(strExept, sw.toString());
           }
       }
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditBanner(RenderRequest request, RenderResponse response)
    {
        ModelAndView model = new ModelAndView();
        //todo: variable`s name and view name
        Collection<BannerImage> banner = service.getAll();
        model.addObject("bannerImages", banner);
        model.setViewName("edit");
        return model;
    }

    @ActionMapping(value = "updateImage")
    public void updateImage(@RequestParam("ban-mainImage") CommonsMultipartFile mainImage,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException
    {   //todo: same shit with IOException
        int id = Integer.valueOf(actionRequest.getParameter("id"));
        BannerImage banner = service.getBannerImageById(id);
        String url = actionRequest.getParameter("url");
        if (!url.equals(banner.getUrl()) || mainImage.getSize() > 0)
        {
            if (this.updateBannerImage(mainImage, url, actionResponse, banner))
            {
                try
                {
                    service.updateBannerImage(banner);
                    sessionStatus.setComplete();
                }
                catch (Exception ex)
                {
                    StringWriter sw = new StringWriter();
                    actionResponse.setRenderParameter(strExept, sw.toString());
                }
            }
        }
        else
        {
            actionResponse.setRenderParameter(strFail, strDuplicat);
        }
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteImage(RenderRequest request, RenderResponse response)
    {
        int imgId = Integer.valueOf(request.getParameter("imgId"));
        BannerImage banner = service.getBannerImageById(imgId);
        ImageService imageService = new ImageService();
        imageService.deleteDirectory(banner);
        try
        {
            service.deleteBannerImage(banner);
        }
        catch (Exception ex)
        {
            LOGGER.warning("Error while deleting banner id= " + imgId);
        }
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);//todo: if you need logic from showView method you should replace it to new private method
        String strSuccess="success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
		ModelAndView model = null;
        //todo: replace common logic to new private methods
		if (!request.getParameter(strFail).equals(strDuplicat))
		{
			model = showAddBanner(request, response);
		}
		else
		{
			model = showEditBanner(request, response);
		}
        SessionErrors.add(request, request.getParameter(strFail));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        //todo: replace common logic to new private methods
        ModelAndView model = showAddBanner(request, response);
        model.addObject(strExept, request.getParameter(strExept));
        return model;
    }
}
