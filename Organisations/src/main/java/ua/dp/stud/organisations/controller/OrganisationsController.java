/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.organisations.controller;

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

import ua.dp.stud.StudPortalLib.dto.CommonDto;
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
public class OrganisationsController {

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
    private static final int ORGS_BY_PAGE = 10;
    private static final int NEARBY_PAGES = 2;
    private static final int OVERAL_PAGES = 7;
    private static final Logger LOG = Logger.getLogger(OrganisationsController.class.getName());
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
       
        
        
        if ((request.getParameter(CURRENT_PAGE) != null) && ("next".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage < organizationService.getPagesCount(ORGS_BY_PAGE)) {
                currentPage += 1;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("prev".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage > 1) {
                currentPage--;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("temp".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else{
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
                rightPageNumb = currentPage+NEARBY_PAGES;
            }
//if farther then nearby + '...' + last
            if (currentPage < pagesCount - (NEARBY_PAGES + 1)) {
                skippedEnding = true;
            } else {
                leftPageNumb = currentPage  -  NEARBY_PAGES;
                rightPageNumb = pagesCount;
            }
        }

        Collection<CommonDto> orgsDtoList = organizationService.getDtoOrganization(organisations);
        
        model.addObject("orgsDtoList", orgsDtoList);
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
    public ModelAndView showSelectedOrgs(RenderRequest request, RenderResponse response, SessionStatus sessionStatus) throws SystemException, PortalException {

        int orgsID = Integer.valueOf(request.getParameter("orgsID"));
        Organization organisation = organizationService.getOrganizationById(orgsID);
        ImageImpl mImage = organisation.getMainImage();
        String mainImageUrl;
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, organisation);
        }
        Collection<ImageImpl> additionalImages = organisation.getAdditionalImages();
        int currentPage;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingle");
        model.addObject("organization", organisation);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        return model;
    }


    private boolean updateOrganisationFields(CommonsMultipartFile mainImage, CommonsMultipartFile[] images,
                                          boolean isApproved, String author,
                                          Organization someorgs, OrganizationType type, Boolean changeImage) {

        someorgs.setOrganizationType(type);
        someorgs.setAuthor(author);
        someorgs.setApproved(isApproved);


        try {
            if (mainImage != null && mainImage.getSize() > 0 && changeImage) {

                    imageService.saveMainImage(mainImage, someorgs);

            }
            if (images != null && images.length > 0) {
                for (CommonsMultipartFile file : images) {
                    imageService.saveAdditionalImages(file, someorgs);
                }
            }
        }
        catch (IOException ex) {
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            return false;
        }
//success upload message
        return true;
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
//getting all parameters from form
            OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter(TYPE));
//crop main image
            CommonsMultipartFile croppedImage = null;
            if (!actionRequest.getParameter("t").equals("") || !"".equals(mainImage.getFileItem().getName())) {
                croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                      Integer.parseInt(actionRequest.getParameter("l")),
                                                      Integer.parseInt(actionRequest.getParameter("w")),
                                                      Integer.parseInt(actionRequest.getParameter("h")));
            } else {
                croppedImage = imageService.getDefaultImage(actionRequest.getPortletSession().getPortletContext().getRealPath(File.separator));
            }
//check the uniqueness of the name
            boolean role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE);
            User user = (User) actionRequest.getAttribute(WebKeys.USER);
            String author = user.getScreenName();
//try to update fields for new organisation
            if (organizationService.isUnique(organization.getTitle())) {
                if (updateOrganisationFields(croppedImage, images, role, author, organization, typeOrg, true)) {
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

    @ActionMapping(value = "editOrganisation")
    public void editOrganisation(@RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                              @RequestParam("images") CommonsMultipartFile[] images, @ModelAttribute(value = "organization") @Valid Organization organization,
                              BindingResult bindingResult,
                              ActionRequest actionRequest,
                              ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {

//getting current news


//getting all parameters from form
        if (bindingResult.hasFieldErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, " ");
        } else {
            Organization oldOrganization = organizationService.getOrganizationById(organization.getId());
            oldOrganization.setComment(organization.getComment());
            oldOrganization.setContacts(organization.getContacts());
            oldOrganization.setNewsList(organization.getNewsList());
            oldOrganization.setPublication(organization.getPublication());
            oldOrganization.setText(organization.getText());
            oldOrganization.setTitle(organization.getTitle());
            oldOrganization.setViews(organization.getViews());
            oldOrganization.setLat(organization.getLat());
            oldOrganization.setLng(organization.getLng());
            oldOrganization.setOrganizationType(organization.getOrganizationType());
            OrganizationType typeOrg = OrganizationType.valueOf(actionRequest.getParameter(TYPE));
            CommonsMultipartFile croppedImage = null;
            Boolean defImage = Boolean.valueOf(actionRequest.getParameter("defaultImage"));
            Boolean changeImage = true;
            boolean role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE) ;
            User user = (User) actionRequest.getAttribute(WebKeys.USER);
            String author = user.getScreenName();
            if (defImage == false) {
                if (actionRequest.getParameter("t").equals("100") && !"".equals(mainImage.getFileItem().getName())) {
                    croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                          Integer.parseInt(actionRequest.getParameter("l")),
                                                          Integer.parseInt(actionRequest.getParameter("w")),
                                                          Integer.parseInt(actionRequest.getParameter("h")));
                    if (updateOrganisationFields(croppedImage, images, role, author, oldOrganization, typeOrg, changeImage))
                        organizationService.updateOrganization(oldOrganization);
                } else {
                    organizationService.updateOrganization(oldOrganization);
                    changeImage = false;
                }

            }
            sessionStatus.setComplete();
        }
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddOrgs(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        Organization organisation = new Organization();
//set view for add
        ImageImpl mImage = organisation.getMainImage();
        String mainImageUrl =imageService.getPathToLargeImage(mImage,organisation);
        organisation.setMainImage(null);
        model.addObject("mainImage", mainImageUrl);
        model.setViewName("addOrganisation");
        model.addObject("organization", organisation);
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
    public ModelAndView editOrganisations(RenderRequest request, RenderResponse response) {
    	//getting organisations
        int organisationID = Integer.valueOf(request.getParameter("orgId"));
        Organization organisation = organizationService.getOrganizationById(organisationID);
    	User user = (User) request.getAttribute(WebKeys.USER);
        String userScreenName = user.getScreenName();
    	if (!(request.isUserInRole("Administrator") || userScreenName.equals(organisation.getAuthor())))
    	{
    		return showView(request,response);
    	}
        ModelAndView model = new ModelAndView();
        ImageImpl mImage = organisation.getMainImage();
        String mainImageUrl;
        mainImageUrl = (mImage == null) ? MAIN_IMAGE_MOCK_URL : imageService.getPathToLargeImage(mImage, organisation);
        Collection<ImageImpl> additionalImages = organisation.getAdditionalImages();
//set view for edit
        model.setViewName("editOrganisation");
//send current organisations in view
        model.addObject("organization", organisation);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);      
        
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteOrganisation(RenderRequest request, RenderResponse response) {
//getting current organisations
        int organisationID = Integer.valueOf(request.getParameter("orgId"));
        Organization organisation = organizationService.getOrganizationById(organisationID);
//delete chosen organization's image from folder
        imageService.deleteDirectory(organisation);
//delete chosen organisations

        organizationService.deleteOrganization(organisation);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);
        String strSuccess = "success";
        int currentPage;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        model.addObject(CURRENT_PAGE, currentPage);
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
