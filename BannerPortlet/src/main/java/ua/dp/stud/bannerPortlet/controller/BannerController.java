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
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.bannerPortlet.model.BannerImage;
import ua.dp.stud.bannerPortlet.service.BannerImageService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vladislav Pikus
 */

@Controller
@RequestMapping(value = "view")
public class BannerController {
    private static final Logger LOG = Logger.getLogger(BannerController.class.getName());
    private static final String STR_FAIL = "fail";
    private static final String STR_NO_IMAGE = "error.no_images";
    private static final String STR_DUBLICAT = "error.dplBanner";
    private static final int URLSIMBOL = 4;
    private static final String SUCCESS = "success";

    @Autowired
    @Qualifier(value = "bannerImageService")
    private BannerImageService bannerImageService;

    public void setService(BannerImageService bannerImageService) {
        this.bannerImageService = bannerImageService;
    }

    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    private boolean updateBannerImage(CommonsMultipartFile mainImage,
                                      String url, ActionResponse actionResponse, BannerImage banner) {
        if (url.length() < URLSIMBOL) {
            actionResponse.setRenderParameter(STR_FAIL, "error.toLow");
            return false;
        }

        banner.setUrl(url);
        boolean successUpload = true;
        if (mainImage != null) {
            if (mainImage.getSize() > 0) {
                try {
                    imageService.saveMainImage(mainImage, banner);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, "Exception: ", ex);
                    successUpload = false;
                }
            }
        } else if (banner.getId() == null) {
            successUpload = false;
        }
        //success upload message
        if (successUpload) {
            actionResponse.setRenderParameter(SUCCESS, SUCCESS);
            return true;
        } else {
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
        }
        return false;
    }

    private ModelAndView getModel(String modelName) {
        ModelAndView model = new ModelAndView(modelName);
        Collection<BannerImage> bannerImages = bannerImageService.getAll();
        model.addObject("bannerImages", bannerImages);
        return model;
    }

    /*
    * Method for rendering VIEW mode
    * @param request
    * @param response
    * @return PortletModeException
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        return getModel("view");
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddBanner(RenderRequest request, RenderResponse response) {
        return new ModelAndView("addImages");
    }

    @ActionMapping(value = "addImage")
    public void addImage(@RequestParam("ban_mainImage") CommonsMultipartFile mainImage,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse, SessionStatus sessionStatus) {
        BannerImage banner = new BannerImage();
        String url = actionRequest.getParameter("url");
        BannerImage other = bannerImageService.getByURL(url);
        if (other == null) {
            if (this.updateBannerImage(mainImage, url, actionResponse, banner)) {
                try {
                    bannerImageService.addBannerImage(banner);
                    sessionStatus.setComplete();
                } catch (Exception unused) {
                    actionResponse.setRenderParameter(STR_FAIL, "error.unknown");
                }
            }
        } else {
            actionResponse.setRenderParameter(STR_FAIL, "error.dplBannerAdd");
        }
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditBanner(RenderRequest request, RenderResponse response) {
        return getModel("edit");
    }

    @ActionMapping(value = "updateImage")
    public void updateImage(@RequestParam("ban_mainImage") CommonsMultipartFile mainImage,
                            ActionRequest actionRequest,
                            ActionResponse actionResponse, SessionStatus sessionStatus) {
        int id = Integer.valueOf(actionRequest.getParameter("id"));
        BannerImage banner = bannerImageService.getBannerImageById(id);
        String url = actionRequest.getParameter("url");
        BannerImage other = bannerImageService.getByURL(url);
        if (other != null && other.getId() != banner.getId()) {
            actionResponse.setRenderParameter(STR_FAIL, STR_DUBLICAT);
            return;
        }
        if (this.updateBannerImage(mainImage, url, actionResponse, banner)) {
            try {
                bannerImageService.updateBannerImage(banner);
                sessionStatus.setComplete();
            } catch (Exception unused) {
                actionResponse.setRenderParameter(STR_FAIL, "error.unknown");
            }
        }
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteImage(RenderRequest request, RenderResponse response) {
        int imgId = Integer.valueOf(request.getParameter("imgId"));
        BannerImage banner = bannerImageService.getBannerImageById(imgId);
        imageService.deleteDirectory(banner);
        try {
            bannerImageService.deleteBannerImage(banner);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception: ", ex);
        }
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = getModel("view");
        SessionMessages.add(request, request.getParameter(SUCCESS));
        return model;
    }

    private ModelAndView checkErrorMsg(RenderRequest request) {
        ModelAndView model;
        if (!request.getParameter(STR_FAIL).equals(STR_DUBLICAT)) {
            model = new ModelAndView("addImages");
        } else {
            model = getModel("edit");
        }
        return model;
    }

    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = checkErrorMsg(request);
        SessionErrors.add(request, request.getParameter(STR_FAIL));
        return model;
    }
}
