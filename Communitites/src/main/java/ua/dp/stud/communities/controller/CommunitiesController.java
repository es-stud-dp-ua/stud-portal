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
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

//todo: rename portlet to OrganizationPortket
@Controller
@RequestMapping(value = "view")
public class CommunitiesController {

    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static final String STR_FAIL = "fail";
    private static final String STR_NO_IMAGE = "no images";
    private static final String STR_BAD_IMAGE = "Failed to load image";
    private static final String STR_EXEPT = "Exception";
    private static final String STR_DUPLICAT_TOPIC = "duplicating topic";
    private static final String ADMINISTRATOR_ROLE = "Administrator";
    private static final String USER_ROLE = "User";
    private static final String MAIN_IMAGE = "mainImage";
    private static final String TYPE = "type";
    private static final String CURRENT_PAGE = "currentPage";
    private static final int ORGS_BY_PAGE = 5;
    private static final int NEARBY_PAGES = 2;
    private static final int OVERAL_PAGES = 7;
    private static final Logger LOG = Logger.getLogger(CommunitiesController.class.getName());
    @Autowired
    @Qualifier(value = "organizationService")
    private OrganizationService organizationService;

    public void setServiceOrg(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
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
//set type of displayed organisations
        int pagesCount;
        int currentPage;
        OrganizationType type;
        //todo: use ternary operator for currentPage and type variables
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
//PAGINATION 
        if (request.getParameter(TYPE) != null) {
            type = OrganizationType.valueOf(request.getParameter(TYPE));
        } else {
            type = null;
        }
        if (type == null) {
            pagesCount = organizationService.getPagesCount(ORGS_BY_PAGE);
            organisations = organizationService.getOrganizationsOnPage2(currentPage, ORGS_BY_PAGE, true);
        } else {
            pagesCount = organizationService.getPagesCountOfType(ORGS_BY_PAGE, type);
            organisations = organizationService.getOrganizationsOnPage(currentPage, ORGS_BY_PAGE, type.toString(), true);
        }

        int leftPageNumb = Math.max(1, currentPage - NEARBY_PAGES);
        int rightPageNumb = Math.min(pagesCount, currentPage + NEARBY_PAGES);
        boolean skippedBeginning = false;
        boolean skippedEnding = false;

        if (pagesCount <= OVERAL_PAGES) {
//all pages are shown
            leftPageNumb = 1;
            rightPageNumb = pagesCount;
        } else {
//if farther then page #1 + '...' + nearby pages  will look like 1 .. pages
            if (currentPage > 2 + NEARBY_PAGES) {
                skippedBeginning = true;
            } else {
//#1 + nearby pages + current + nearby pages   
                leftPageNumb = 1;
                rightPageNumb = 2 + 2 * NEARBY_PAGES;
            }
//if farther then nearby + '...' + last
            if (currentPage < pagesCount - (NEARBY_PAGES + 1)) {
                skippedEnding = true;
            } else {
                leftPageNumb = (pagesCount - 1) - 2 * NEARBY_PAGES;
                rightPageNumb = pagesCount;
            }
        }

        model.addObject("leftPageNumb", leftPageNumb);
        model.addObject("rightPageNumb", rightPageNumb);
        model.addObject("skippedBeginning", skippedBeginning);
        model.addObject("skippedEnding", skippedEnding);
        model.addObject("organisations", organisations);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject(TYPE, type);
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
        Organization organisation = organizationService.getOrganizationById(orgsID);
        organizationService.incViews(organisation);
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
        model.addObject(MAIN_IMAGE, mainImageUrl);
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
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
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
        if (request.getParameter(TYPE) == null) {
            if (currentPage < organizationService.getPagesCount(ORGS_BY_PAGE)) {
                currentPage += 1;
            }
        } else if (currentPage < organizationService.getPagesCountOfType(ORGS_BY_PAGE, OrganizationType.valueOf(request.getParameter(TYPE)))) {
            currentPage += 1;
        }
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
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
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
        }
    }
    //todo: remove @RequestParam and throws part

    private boolean updateCommunityFields(@RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            String frmTopic, String frmText, String frmRole, String role,
            ActionResponse actionResponse, Organization someorgs, OrganizationType type)
            throws SystemException, PortalException {
        boolean successUpload = true;
        someorgs.setTitle(frmTopic);
        someorgs.setText(frmText);
        someorgs.setOrganizationType(type);
        if (frmRole.equals(ADMINISTRATOR_ROLE)) {
            someorgs.setAuthor(role);
            someorgs.setApproved(true);
        } else if (frmRole.equals(USER_ROLE)) {
            someorgs.setAuthor(role);
        }  
        try {
            if (mainImage.getSize() > 0) {
                imageService.saveMainImage(mainImage, someorgs);
            }
            if (images != null && images.length > 0) {
                for (CommonsMultipartFile file : images) {
                    imageService.saveAdditionalImages(file, someorgs);
                }
            }
        } catch (IOException ex) {
            successUpload = false;
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            actionResponse.setRenderParameter(STR_EXEPT, "");
        }
//success upload message
        if (successUpload) {
            return true;
        }
        actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
        return false;
    }

        @ModelAttribute("organization")
        public Organization getCommandObject() {
            return new Organization();
        }

        @InitBinder
        public void initBinder(WebDataBinder binder) {
            binder.setDisallowedFields("mainImage");
        }

    @InitBinder("organization")
    @ActionMapping(value = "addOrganisation")
    public void addOrganisation(@ModelAttribute(value = "organization") @Valid Organization organization,
            BindingResult bindingResult,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus, @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images)
            throws SystemException, PortalException {
        if (bindingResult.hasFieldErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, " ");
        } else {
//path for main image is not empty
            if (mainImage.getOriginalFilename().equals("")) {
                actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
                return;
            }
//getting all parameters from form
            OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter(TYPE));
//crop main image
            CommonsMultipartFile croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                    Integer.parseInt(actionRequest.getParameter("l")),
                    Integer.parseInt(actionRequest.getParameter("w")),
                    Integer.parseInt(actionRequest.getParameter("h")));
            if (croppedImage == null) {
                actionResponse.setRenderParameter(STR_FAIL, STR_BAD_IMAGE);
                return;
            }
//check the uniqueness of the name
            //todo: use another approach
            Collection<Organization> orgs = organizationService.getAllOrganizations(true);
            Boolean isUnique = false;
            for (Organization currentOrgs : orgs) {
                if (currentOrgs.getTitle().trim().equalsIgnoreCase(organization.getTitle())) {
                    isUnique = true;
                }
            }
            String role;
            role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE) ? ADMINISTRATOR_ROLE : USER_ROLE;
            User user = (User) actionRequest.getAttribute(WebKeys.USER);
            String usRole = user.getScreenName();
//try to update fields for new organisation
            if (!isUnique) {
                if (updateCommunityFields(croppedImage, images, organization.getTitle(), organization.getText(), role, usRole, actionResponse, organization, typeOrg)) {
                    Date date = new Date();
                    organization.setPublication(date);

                    organization = organizationService.addOrganization(organization);
                    actionResponse.setRenderParameter("orgsID", Integer.toString(organization.getId()));
                    sessionStatus.setComplete();
                }
            } else {
                actionResponse.setRenderParameter(STR_FAIL, STR_DUPLICAT_TOPIC);
            }
        }
    }

    @ActionMapping(value = "editCommunity")
    public void editCommunity(@RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images, @ModelAttribute(value = "organization") @Valid Organization organization,
            BindingResult bindingResult,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
//getting current news
        int organisationID = Integer.valueOf(actionRequest.getParameter("orgsId"));
        Organization org = organizationService.getOrganizationById(organisationID);
//getting all parameters from form
        if (bindingResult.hasFieldErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, " ");
        } else {
            OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter(TYPE));
            CommonsMultipartFile croppedImage;
            if (!actionRequest.getParameter("t").equals("")) {
                croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                        Integer.parseInt(actionRequest.getParameter("l")),
                        Integer.parseInt(actionRequest.getParameter("w")),
                        Integer.parseInt(actionRequest.getParameter("h")));
            } else {
                croppedImage = mainImage;
            }
            if (croppedImage == null) {
                actionResponse.setRenderParameter(STR_FAIL, STR_BAD_IMAGE);
                return;
            }
            String role;
            role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE) ? ADMINISTRATOR_ROLE : USER_ROLE;
            User user = (User) actionRequest.getAttribute(WebKeys.USER);
            String usRole = user.getScreenName();
            if (updateCommunityFields(croppedImage, images, organization.getTitle(), organization.getText(), role, usRole, actionResponse, org, typeOrg)) {
                organizationService.updateOrganization(org);
//close session
                sessionStatus.setComplete();
            } else {
                actionResponse.setRenderParameter(STR_FAIL, STR_DUPLICAT_TOPIC);
            }
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
        ImageImpl image = organizationService.getImageById(imageID);
//delete image from folder
        imageService.deleteImage(image, image.getBase());
//delete image from data base
        organizationService.deleteImage(image);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
//getting news
        int organisationID = Integer.valueOf(request.getParameter("orgsId"));
        Organization organisation = organizationService.getOrganizationById(organisationID);
        ImageImpl mImage = organisation.getMainImage();
        String mainImageUrl;
        mainImageUrl = (mImage == null) ? MAIN_IMAGE_MOCK_URL : imageService.getPathToLargeImage(mImage, organisation);
        Collection<ImageImpl> additionalImages = organisation.getAdditionalImages();
//set view for edit
        model.setViewName("editCommunity");
//send current news in view
        model.addObject("organization", organisation);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteOrganisation(RenderRequest request, RenderResponse response) {
//getting current news
        int organisationID = Integer.valueOf(request.getParameter("orgsId"));
        Organization organisation = organizationService.getOrganizationById(organisationID);
//delete chosen organization's image from folder
        imageService.deleteDirectory(organisation);
//delete chosen news
        organizationService.deleteOrganization(organisation);
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
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddOrgs(request, response);
        model.addObject(STR_EXEPT, request.getParameter(STR_EXEPT));
        return model;
    }
}
