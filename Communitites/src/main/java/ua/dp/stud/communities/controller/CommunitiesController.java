/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.communities.controller;

/**
 *
 * @author Игорь Лапко
 */
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
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
import ua.dp.stud.StudPortalLib.model.OrganizationType;
import ua.dp.stud.StudPortalLib.service.OrganizationService;
import ua.dp.stud.StudPortalLib.util.ImageService;

import javax.imageio.ImageIO;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;

@Controller
@RequestMapping(value = "view")
public class CommunitiesController {

    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static final String strFail = "fail";
    private static final String strNoImage = "no-images";
    private static final String strExept = "exception";
    private static final String strDuplicatTopic = "dplTopic";
    private static final String administratorRole = "Administrator";
    private static final String userRole = "User";
    private static final int MINTITLESYMBOLS = 4;
    private static final int MINTEXTSYMBOLS = 100;
    private static final int ORGS_BY_PAGE = 5;
    @Autowired
    @Qualifier(value = "OrganizationService")
    private OrganizationService service;

    public void setService(OrganizationService service) {
        this.service = service;
    }

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
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewAll");
        Collection<Organization> organisations;
//      set type of displayed organisations

        int pagesCount;
        int currentPage;
        OrganizationType type;
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } else {
            currentPage = 1;
        }

        if (request.getParameter("type") != null) {
            type = OrganizationType.valueOf(request.getParameter("type"));
        } else {
            type = null;
        }
        if (type == null) {
            pagesCount = service.getPagesCount(ORGS_BY_PAGE);
            organisations = service.getOrganizationsOnPage2(currentPage, ORGS_BY_PAGE, true);
        } else {
            pagesCount = service.getPagesCountOfType(ORGS_BY_PAGE, type);
            organisations = service.getOrganizationsOnPage(currentPage, ORGS_BY_PAGE, type.toString(), true);
        }

        model.addObject("organisations", organisations);
        model.addObject("currentPage", currentPage);
        model.addObject("pagesCount",  pagesCount);
        model.addObject("type", type);
        model.addObject("OrgsByPage", ORGS_BY_PAGE);
        return model;
    }

    /**
     * Method for rendering view mode for single organization
     *
     * @param request
     * @param response
     * @return model
     */
    @RenderMapping(params = "orgsID")
    public ModelAndView showSelectedOrgs(RenderRequest request, RenderResponse response) throws SystemException, PortalException {

        int orgsID = Integer.valueOf(request.getParameter("orgsID"));
        Organization organisation = service.getOrganizationById(orgsID);
        ImageService imageService = new ImageService();
        ImageImpl mImage = organisation.getMainImage();
        String mainImageUrl;

        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, organisation);
        }
        Collection<ImageImpl> additionalImages = organisation.getAdditionalImages();
        Collection<News> newsList = organisation.getNewsList();
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long groupId = themeDisplay.getScopeGroupId();
        long plid = LayoutLocalServiceUtil.getDefaultPlid(groupId, false, "NewsArchive_WAR_NewsArchivePortlet101");
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingle");
        model.addObject("organization", organisation);
        model.addObject("mainImage", mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        model.addObject("newsList", newsList);
        model.addObject("newsArchivePageID", plid);
        return model;
    }
    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination")
    public void showPage(ActionRequest request, ActionResponse response) {
        int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        response.setRenderParameter("currentPage", String.valueOf(currentPage));
        if (request.getParameter("type") != null) {
            response.setRenderParameter("type", request.getParameter("type"));
        }
    }
    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination", params = "direction=next")
    public void showNextPage(ActionRequest request, ActionResponse response) {
        int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (request.getParameter("type") == null) {
            if (currentPage < service.getPagesCount(ORGS_BY_PAGE)) {
                currentPage += 1;
            }} else if (currentPage < service.getPagesCountOfType(ORGS_BY_PAGE,OrganizationType.valueOf(request.getParameter("type")))) {
                currentPage += 1;
            }
        response.setRenderParameter("currentPage", String.valueOf(currentPage));
        if (request.getParameter("type") != null) {
            response.setRenderParameter("type", request.getParameter("type"));
        }
    }
    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination", params = "direction=prev")
    public void showPrevPage(ActionRequest request, ActionResponse response) {
        int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (currentPage > 1) {
            currentPage -= 1;
        }
        response.setRenderParameter("currentPage", String.valueOf(currentPage));
        if (request.getParameter("type") != null) {
            response.setRenderParameter("type", request.getParameter("type"));
        }
    }
    private boolean updateCommunityFields(@RequestParam("mainImage") CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            String frmTopic, String frmText, String frmRole, String role,
            ActionResponse actionResponse, String strFail, String strNoImage, Organization someorgs, OrganizationType type)
            throws IOException, SystemException, PortalException {
        boolean successUpload = true;
        ImageService imageService = new ImageService();
//        check the length of the title and text
        if (frmTopic.length() < MINTITLESYMBOLS || frmText.length() < MINTEXTSYMBOLS) {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        someorgs.setTitle(frmTopic);
        someorgs.setText(frmText);
        someorgs.setOrganizationType(type);
        if (frmRole.equals(administratorRole)) {
            someorgs.setAuthor(role);
            someorgs.setApproved(true);
        } else if (frmRole.equals(userRole)) {
            someorgs.setAuthor(role);
        }
        try {
//        main image uploading
            imageService.saveMainImage(mainImage, someorgs);
//        image collection uploading
            for (CommonsMultipartFile file : images) {
                imageService.saveAdditionalImages(file, someorgs);
            }
        } catch (Exception ex) {
            successUpload = false;
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(strExept, sw.toString());
        }
        //success upload message
        if (successUpload) {
            return true;
        }
        actionResponse.setRenderParameter(strFail, strNoImage);
        return false;
    }

    @ActionMapping(value = "addOrganisation")
    public void addOrganisation(@RequestParam("mainImage") CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
//        path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(strFail, strNoImage);
            return;
        }

        Organization organization = new Organization();
//        getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter("type"));

        BufferedImage sourceImage = ImageIO.read(mainImage.getInputStream());
//        Compressing an image to produce the necessary proportions
        sourceImage = ImageService.resize(sourceImage, 443, 253);
//        cut the selected user area
        sourceImage = sourceImage.getSubimage(Integer.parseInt(actionRequest.getParameter("t")),
                Integer.parseInt(actionRequest.getParameter("l")),
                Integer.parseInt(actionRequest.getParameter("w")),
                Integer.parseInt(actionRequest.getParameter("h")));
        File tempFile = new File(ImageService.getImagesFolderAbsolutePath() + mainImage.getOriginalFilename());
        ImageIO.write(sourceImage, "jpg", tempFile);
        CommonsMultipartFile croppedImage = null;
        try {
            DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("fileData", "image/jpeg", true, tempFile.getName());
            InputStream input = new FileInputStream(tempFile);
            OutputStream os = fileItem.getOutputStream();
            int ret = input.read();
            while (ret != -1) {
                os.write(ret);
                ret = input.read();
            }
            os.flush();
            croppedImage = new CommonsMultipartFile(fileItem);
        } catch (Exception e) {
        }
//        check the uniqueness of the name
        Collection<Organization> orgs = service.getAllOrganizations(true);
        Boolean isUnique = false;
        for (Organization currentOrgs : orgs) {
            if (currentOrgs.getTitle().trim().equalsIgnoreCase(topic.trim())) {
                isUnique = true;
            }
        }

        String role;
        if (actionRequest.isUserInRole(administratorRole)) {
            role = administratorRole;
        } else {
            role = userRole;
        }
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();
//        try to update fields for new organisation
        if (!isUnique) {
            if (updateCommunityFields(croppedImage, images, topic.trim(), text.trim(), role, usRole, actionResponse, strFail, strNoImage, organization, typeOrg)) {
                organization = service.addOrganization(organization);
                actionResponse.setRenderParameter("orgsID", Integer.toString(organization.getId()));
                sessionStatus.setComplete();
            }
        } else {
            actionResponse.setRenderParameter(strFail, strDuplicatTopic);
        }

    }

    @ActionMapping(value = "editCommunity")
    public void editCommunity(@RequestParam("mainImage") CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
//        getting current news
        int organisationID = Integer.valueOf(actionRequest.getParameter("orgsId"));
        Organization organization = service.getOrganizationById(organisationID);
//        getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter("type"));
        String role;
        if (actionRequest.isUserInRole(administratorRole)) {
            role = administratorRole;
        } else {
            role = userRole;
        }
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();

        if (updateCommunityFields(mainImage, images, topic.trim(), text.trim(), role, usRole, actionResponse, strFail, strNoImage, organization, typeOrg)) {
            service.updateOrganization(organization);
//            close session
            sessionStatus.setComplete();

        } else {
            actionResponse.setRenderParameter(strFail, strDuplicatTopic);
        }
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddOrgs(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        //set view for add
        model.setViewName("addOrganisation");
        return model;
    }

    @RenderMapping(params = "mode=delImage")
    public ModelAndView delImage(RenderRequest request, RenderResponse response) {
        long imageID = Long.valueOf(request.getParameter("imageId"));
        ImageImpl image = service.getImageById(imageID);
        ImageService imageService = new ImageService();
//        delete image from folder
        imageService.deleteImage(image, image.getBase());
//        delete image from data base
        service.deleteImage(image);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        //getting news
        int organisationID = Integer.valueOf(request.getParameter("orgsId"));
        Organization organisation = service.getOrganizationById(organisationID);
        ImageService imageService = new ImageService();
        ImageImpl mImage = organisation.getMainImage();
        String mainImageUrl;

        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, organisation);
        }
        Collection<ImageImpl> additionalImages = organisation.getAdditionalImages();
        //set view for edit
        model.setViewName("editCommunity");
        //send current news in view
        model.addObject("organisation", organisation);
        model.addObject("mainImage", mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteOrganisation(RenderRequest request, RenderResponse response) {
        //getting current news

        int organisationID = Integer.valueOf(request.getParameter("orgsId"));
        Organization organisation = service.getOrganizationById(organisationID);
        ImageService imageService = new ImageService();
        //delete chosen organization's image from folder
        imageService.deleteDirectory(organisation);
        // delete chosen news
        service.deleteOrganization(organisation);

        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddOrgs(request, response);
        SessionErrors.add(request, request.getParameter(strFail));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddOrgs(request, response);
        model.addObject(strExept, request.getParameter(strExept));
        return model;
    }
}
