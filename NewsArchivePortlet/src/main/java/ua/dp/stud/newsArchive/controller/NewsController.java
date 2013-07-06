package ua.dp.stud.newsArchive.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
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
import ua.dp.stud.StudPortalLib.service.NewsService;
import ua.dp.stud.StudPortalLib.service.OrganizationService;
import ua.dp.stud.StudPortalLib.util.ImageService;

import javax.imageio.ImageIO;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Controller for view mode
 */
@Controller
@RequestMapping(value = "view")
public class NewsController {
    private static final Logger LOGGER = Logger.getLogger(NewsController.class.getName());
    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static final String STR_FAIL = "fail";
    private static final String NO_IMAGE = "no-images";
    private static final String STR_EXEPT = "exception";
    private static final String ADMIN_ROLE = "Administrator";
    private static final String USER_ROLE = "User";
    //news text limitations
    private static final int MINTITLESYMBOLS = 4;
    private static final int MINTEXTSYMBOLS = 100;
    private static final int NEWS_BY_PAGE = 10;

    @Autowired
    @Qualifier(value = "newsService")
    private NewsService newsService;

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

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
        return getMainView(request);
    }

    private ModelAndView getMainView(RenderRequest request) {
        ModelAndView model = new ModelAndView("viewAll");

        Integer pagesCount = newsService.getPagesCount(NEWS_BY_PAGE);
        Integer currentPage;
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } else {
            currentPage = 1;
        }
        Collection<News> news = newsService.getNewsOnPage(true, currentPage, NEWS_BY_PAGE);
        model.addObject("news", news);
        model.addObject("currentPage", currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("newsByPage", NEWS_BY_PAGE);
        createPagination(model, currentPage, pagesCount);
        return model;
    }

    private void createPagination(ModelAndView model, Integer currentPage, Integer pagesCount) {
        Integer nearbyPages = 2; //number of pages to show to left and right of current
        Integer overallPages = 7; //overall number of pages
        Integer leftPageNumb = Math.max(1, currentPage - nearbyPages),
                rightPageNumb = Math.min(pagesCount, currentPage + nearbyPages);
        Boolean skippedBeginning = false,
                skippedEnding = false;

        if (pagesCount <= overallPages) {
            leftPageNumb = 1;                 //all pages are shown
            rightPageNumb = pagesCount;
        } else {
            if (currentPage > 2 + nearbyPages) { //if farther then page #1 + '...' + nearby pages
                skippedBeginning = true;        // will look like 1 .. pages
            } else {
                leftPageNumb = 1;             //shows all first pages
                rightPageNumb = 2 + 2 * nearbyPages; //#1 + nearby pages + current + nearby pages
            }

            if (currentPage < pagesCount - (nearbyPages + 1)) { //if farther then nearby + '...' + last
                skippedEnding = true;         //will look like pages .. lastPage
            } else {
                leftPageNumb = (pagesCount - 1) - 2 * nearbyPages;  //shows all last pages:
                rightPageNumb = pagesCount;
            }
        }
        model.addObject("nearbyPages", nearbyPages);
        model.addObject("overallPages", overallPages);
        model.addObject("leftPageNumb", leftPageNumb);
        model.addObject("rightPageNumb", rightPageNumb);
        model.addObject("skippedBeginning", skippedBeginning);
        model.addObject("skippedEnding", skippedEnding);
    }

    /**
     * Method for rendering view mode for single news
     *
     * @param request
     * @param response
     * @return model
     */
    @RenderMapping(params = "newsID")
    public ModelAndView showSelectedNews(RenderRequest request, RenderResponse response) {
        int newsID = Integer.valueOf(request.getParameter("newsID"));
        return getNews(newsID);
    }

    /**
     * Aprove
     *
     * @param request
     * @param response
     */
    @RenderMapping(params = "aproveByMyOrgs")
    private ModelAndView GetNewsWaitingForAprove(ActionRequest request, ActionResponse response) {
        Collection<Organization> orgs = organizationService.getAllOrganizationByAuthor(request.getParameter("author"));
        Collection<Collection<News>> news = null;
        for (Organization org : orgs) {
            news.add(newsService.getNewsByOrg(org.getId(), false));
        }
        ModelAndView model = new ModelAndView("newsForAprove");
        model.addObject("newsForAprove", news);
        return model;
    }

    private ModelAndView getNews(Integer id) {
        News news = newsService.getNewsById(id);

        ImageImpl mImage = news.getMainImage();
        String mainImageUrl;

        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, news);
        }
        Collection<ImageImpl> additionalImages = news.getAdditionalImages();


        ModelAndView model = new ModelAndView("viewSingle");
        model.addObject("news", news);
        String mainImagePar = "mainImage";
        model.addObject(mainImagePar, mainImageUrl);
        model.addObject("additionalImages", additionalImages);

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
    }

    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination", params = "direction=next")
    public void showNextPage(ActionRequest request, ActionResponse response) {
        Integer currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        Integer pagesCount = newsService.getPagesCount(NEWS_BY_PAGE);
        if (currentPage < pagesCount) {
            currentPage++;
        }
        response.setRenderParameter("currentPage", String.valueOf(currentPage));
    }

    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination", params = "direction=prev")
    public void showPrevPage(ActionRequest request, ActionResponse response) {
        Integer currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (currentPage > 1) {
            currentPage--;
        }
        response.setRenderParameter("currentPage", String.valueOf(currentPage));
    }

    /**
     * * Update all fields for parameter somenews
     *
     * @param mainImage
     * @param images
     * @param frmTopic
     * @param frmText
     * @param frmInCalendar
     * @param frmOnMainPage
     * @param frmDateInCalendar
     * @param frmRole
     * @param actionResponse
     * @param strFail
     * @param strNoImage
     * @param somenews
     * @throws IOException
     * @throws SystemException
     * @throws PortalException
     */

    private boolean updateNewsFields(CommonsMultipartFile mainImage,
                                     CommonsMultipartFile[] images,
                                     String frmTopic, String frmText, Boolean frmInCalendar, Boolean frmOnMainPage,
                                     String frmDateInCalendar, String frmRole, String role,
                                     ActionResponse actionResponse, String strFail, String strNoImage, News somenews) {

        boolean successUpload = true;

        //get topic from views
        if (frmTopic.length() < MINTITLESYMBOLS) {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        somenews.setTopic(frmTopic);
        //get text from views
        if (frmText.length() < MINTEXTSYMBOLS) {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        somenews.setText(frmText);
        //set in calendar instance from views
        somenews.setInCalendar(frmInCalendar);
        //if admin add then get on main page visibility from views
        somenews.setOnMainpage(frmOnMainPage);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //get date for calendar view from views
        //make decision for athor rules
        somenews.setApproved(frmRole.equals(ADMIN_ROLE));
        somenews.setAuthor(role);
        //main image uploading
        try {
            if (frmInCalendar) {
                Date startDate = df.parse(frmDateInCalendar);
                somenews.setPublicationInCalendar(startDate);
            }
            if (mainImage.getSize() > 0) {
                imageService.saveMainImage(mainImage, somenews);
            }
            //image collection uploading
            if (images != null && images.length > 0) {
                for (CommonsMultipartFile file : images) {
                    imageService.saveAdditionalImages(file, somenews);
                }
            }
        } catch (Exception ex) {
            successUpload = false;
            LOGGER.warning(ex.getMessage());
        }
        //success upload message
        if (successUpload) {
            actionResponse.setRenderParameter("success", "success-add");
            return true;
        } else {
            actionResponse.setRenderParameter(strFail, strNoImage);
            return false;
        }
    }

    @ActionMapping(value = "addNews")
    public void addNews(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                        @RequestParam("images") CommonsMultipartFile[] images,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse, SessionStatus sessionStatus) {
        //path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, NO_IMAGE);
            return;
        }
        //create new object News
        News news = new News();
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        String dateInCalendar = actionRequest.getParameter("startDate");
        String role;
        int t = Integer.parseInt(actionRequest.getParameter("t"));
        int l = Integer.parseInt(actionRequest.getParameter("l"));
        int w = Integer.parseInt(actionRequest.getParameter("w"));
        int h = Integer.parseInt(actionRequest.getParameter("h"));
        CommonsMultipartFile f = imageService.cropImage(mainImage, t, l, w, h);

        role = actionRequest.isUserInRole(ADMIN_ROLE) ? ADMIN_ROLE : USER_ROLE;
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();
        Boolean inCalendar = actionRequest.getParameter("inCalendar") != null;
        Boolean onMainPage = actionRequest.getParameter("onMainPage") != null;
        //update fields for new news
        if (updateNewsFields(f, images, topic, text, inCalendar, onMainPage,
                dateInCalendar, role, usRole, actionResponse, STR_FAIL, NO_IMAGE, news)) {
            news.setPublication(new Date());
            //updating info about loaded news images
            try {
                newsService.addNews(news);
                //close session
                sessionStatus.setComplete();
            } catch (Exception ex) {
                //exception controller
                LOGGER.warning("Error wile add news");
                actionResponse.setRenderParameter(STR_EXEPT, "");
            }
        }
    }

    @ActionMapping(value = "editNews")
    public void editNews(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                         @RequestParam("images") CommonsMultipartFile[] images,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse, SessionStatus sessionStatus) {
        //getting current news
        int newsID = Integer.valueOf(actionRequest.getParameter("newsId"));
        News news = newsService.getNewsById(newsID);
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text = actionRequest.getParameter("text");
        String dateInCalendar = actionRequest.getParameter("startDate");
        String role = null;
        if (actionRequest.isUserInRole(ADMIN_ROLE)) {
            role = ADMIN_ROLE;
        } else {
            role = USER_ROLE;
        }
        Boolean inCalendar = actionRequest.getParameter("inCalendar") != null;
        Boolean onMainPage = actionRequest.getParameter("onMainPage") != null;
        //apdate author for edit
        String author = news.getAuthor();
        //update fields for new news
        if (updateNewsFields(mainImage, images, topic, text, inCalendar, onMainPage,
                dateInCalendar, role, author, actionResponse, STR_FAIL, NO_IMAGE, news)) {
            newsService.updateNews(news);
            //close session
            sessionStatus.setComplete();
        }
    }


    @RenderMapping(params = "mode=add")
    public ModelAndView showAddNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addNews");
        return model;
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        String publicationInCalendar = "";
        //getting news
        int newsID = Integer.valueOf(request.getParameter("newsId"));
        News news = newsService.getNewsById(newsID);
        //set view for edit
        model.setViewName("editNews");
        if (news.getPublicationInCalendar() != null) {
            publicationInCalendar = news.getPublicationInCalendar().toString().split(" ")[0];
        }

        model.addObject("pubDate", publicationInCalendar);
        model.addObject("news", news);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteNews(RenderRequest request, RenderResponse response) {
        //getting current news
        int newsID = Integer.valueOf(request.getParameter("newsId"));
        News news = newsService.getNewsById(newsID);
        imageService.deleteDirectory(news);
        //delete chosen news
        newsService.deleteNews(news);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "mode=delImage")
    public ModelAndView delImage(RenderRequest request, RenderResponse response) {
        long imageID = Long.valueOf(request.getParameter("imageId"));
        ImageImpl image = newsService.getImageById(imageID);
        Integer newsID = image.getBase().getId();
        //delete image from folder
        imageService.deleteImage(image, image.getBase());
        //delete image from data base
        newsService.deleteImage(image);
        return getNews(newsID);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = getMainView(request);
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addNews");
        SessionErrors.add(request, request.getParameter(STR_FAIL));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("addNews");
        model.addObject(STR_EXEPT, request.getParameter(STR_EXEPT));
        return model;
    }
}

