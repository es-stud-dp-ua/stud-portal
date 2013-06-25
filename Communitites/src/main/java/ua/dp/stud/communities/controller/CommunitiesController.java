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
    //TODO: REWRITE ALL CODE! BREAK PREVIOUS AUTHOR'S HANDS!
    //todo: put image file to images folder
    //todo: add final key-word
    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static String strFail = "fail";
    private static String strNoImage = "no-images";
    private static String strExept = "exception";
    private static String strDuplicatTopic = "dplTopic";
    //todo: you shouldn't use non final variables in controllers, remove it
    private Boolean flag = false;
    private static OrganizationType defaultType = OrganizationType.YOUNGSTERS;
    private static final int MINTITLESYMBOLS = 4;
    private static final int MINTEXTSYMBOLS = 100;
    private static String administratorRole = "Administrator";
    private static String userRole = "User";

    @Autowired
    @Qualifier(value = "OrganizationService")
    private OrganizationService service;

    //Declare our service
    public void setService(OrganizationService service) {
        this.service = service;
    }
    //TODO: remove all changeable variables!
    private int pagesCount;
    private int currentPage = 1;
    private OrganizationType type = defaultType;
    private static final int ORGS_BY_PAGE = 5;

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
        //todo: you will burn in hell for this code!
        //todo: REFACTOR!
        if ((!flag) && (service.getType() == null ? request.getParameter("type") != null : !service.getType().equals(request.getParameter("type")))) {
            service.setType(request.getParameter("type"));
        } else if ((flag) && (request.getParameter("type") != null)) {
            service.setType(request.getParameter("type"));
            flag = false;
        }
        if (service.getType() == null) {
            pagesCount = service.getPagesCount(ORGS_BY_PAGE);
            organisations = service.getOrganizationsOnPage2(currentPage, ORGS_BY_PAGE, true);
        } else {
            if (!flag) {
                type = OrganizationType.valueOf(request.getParameter("type"));
                pagesCount = service.getPagesCountOfType(ORGS_BY_PAGE, type);
                organisations = service.getOrganizationsOnPage(currentPage, ORGS_BY_PAGE, type.toString(), true);
            } else {
                type = OrganizationType.valueOf(service.getType());
                pagesCount = service.getPagesCountOfType(ORGS_BY_PAGE, type);
                organisations = service.getOrganizationsOnPage(currentPage, ORGS_BY_PAGE, type.toString(), true);
            }
        }

        model.addObject("organisations", organisations);
        model.addObject("currentPage", currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("type", type);
        model.addObject("OrgsByPage", ORGS_BY_PAGE);

        //reset to default if no actions will be made
        currentPage = 1;
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
        if (service.getType() != null) {
            flag = true;
        }
        currentPage = Integer.valueOf(request.getParameter("pageNumber"));
    }

    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination", params = "direction=next")
    public void showNextPage(ActionRequest request, ActionResponse response) {

        if (service.getType() != null) {
            flag = true;
        }
        currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (currentPage < pagesCount) {
            currentPage += 1;
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

        if (service.getType() != null) {
            flag = true;
        }
        currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (currentPage > 1) {
            currentPage -= 1;
        }
    }


    private boolean updateCommunityFields(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                                          @RequestParam("images") CommonsMultipartFile[] images,
                                          String frmTopic, String frmText, String frmRole, String role,
                                          ActionResponse actionResponse, String strFail, String strNoImage, Organization someorgs, OrganizationType type)
            throws IOException, SystemException, PortalException {
        boolean successUpload = true;
        ImageService imageService = new ImageService();
        //get topic from views
        if (frmTopic.length() < MINTITLESYMBOLS) {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        someorgs.setTitle(frmTopic);
        //get text from views
        if (frmText.length() < MINTEXTSYMBOLS) {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        someorgs.setText(frmText);
        someorgs.setOrganizationType(type);
        if (frmRole.equals(administratorRole)) {
            someorgs.setAuthor(role);
            someorgs.setApproved(true);
        } else if (frmRole.equals(userRole)) {

            someorgs.setAuthor(role);
        }
        //main image uploading
        //todo: use only one try block
        try {
            if (mainImage.getSize() > 0) {
                try {
                    imageService.saveMainImage(mainImage, someorgs);
                } catch (Exception ex) {
                    successUpload = false;
                }
            }
            //image collection uploading
            if (images != null && images.length > 0) {
                try {
                    for (CommonsMultipartFile file : images) {
                        imageService.saveAdditionalImages(file, someorgs);
                    }
                } catch (Exception ex) {
                    successUpload = false;
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(strExept, sw.toString());
        }
        //success upload message
        if (successUpload) {
            return true;
        } else {
            actionResponse.setRenderParameter(strFail, strNoImage);
        }
        return false;
    }

    @ActionMapping(value = "addOrganisation")
    public void addOrganisation(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                                @RequestParam("images") CommonsMultipartFile[] images,
                                ActionRequest actionRequest,
                                ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
        //path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(strFail, strNoImage);
            return;
        }
        //create new object Organisation
        Organization organization = new Organization();
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        int t = Integer.parseInt(actionRequest.getParameter("t"));
        int l = Integer.parseInt(actionRequest.getParameter("l"));
        int w = Integer.parseInt(actionRequest.getParameter("w"));
        int h = Integer.parseInt(actionRequest.getParameter("h"));

        BufferedImage sourceImage = ImageIO.read(mainImage.getInputStream());
        sourceImage = ImageService.resize(sourceImage, 443, 253);
        sourceImage = sourceImage.getSubimage(t, l, w, h);
        File temp = new File(ImageService.getImagesFolderAbsolutePath() + mainImage.getOriginalFilename());
        ImageIO.write(sourceImage, "jpg", temp);
        CommonsMultipartFile f = null;
        //todo: USE ONLY ONE TRY-BLOCK!!!
        try {
            DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("fileData", "image/jpeg", true, temp.getName());
            InputStream input = new FileInputStream(temp);
            OutputStream os = fileItem.getOutputStream();
            int ret = input.read();
            while (ret != -1) {
                os.write(ret);
                ret = input.read();
            }
            os.flush();
            f = new CommonsMultipartFile(fileItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collection<Organization> orgs = service.getAllOrganizations(true);

        String text = actionRequest.getParameter("text");

        OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter("type"));
        //update fields for new organisation
        Boolean asTopic = false;
        for (Organization currentOrgs : orgs) {
            if (currentOrgs.getTitle().trim().equalsIgnoreCase(topic.trim())) {
                asTopic = true;
            }
        }
        String role = null;
        if (actionRequest.isUserInRole(administratorRole)) {
            role = administratorRole;
        } else {
            role = userRole;
        }
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();
        if (!asTopic) {
            if (updateCommunityFields(f, images, topic.trim(), text.trim(), role, usRole, actionResponse, strFail, strNoImage, organization, typeOrg)) {
                try {
                    organization = service.addOrganization(organization);
                    actionResponse.setRenderParameter("orgsID", Integer.toString(organization.getId()));
                    //close session
                    sessionStatus.setComplete();
                } catch (Exception ex) {
                    //exception controller
                    StringWriter sw = new StringWriter();
                    actionResponse.setRenderParameter(strExept, sw.toString());
                }
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
        //getting current news
        int organisationID = Integer.valueOf(actionRequest.getParameter("orgsId"));
        Organization organization = service.getOrganizationById(organisationID);
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter("type"));
        String role = null;
        if (actionRequest.isUserInRole(administratorRole)) {
            role = administratorRole;
        } else {
            role = userRole;
        }
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();

        if (updateCommunityFields(mainImage, images, topic.trim(), text.trim(), role, usRole, actionResponse, strFail, strNoImage, organization, typeOrg)) {
            //updating info about loaded organisation
            try {
                service.updateOrganization(organization);
                //close session
                sessionStatus.setComplete();
            } catch (Exception ex) {
                //exception controller
                StringWriter sw = new StringWriter();
                actionResponse.setRenderParameter(strExept, sw.toString());
            }
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
        //delete image from folder
        imageService.deleteImage(image, image.getBase());
        //delete image from data base
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
