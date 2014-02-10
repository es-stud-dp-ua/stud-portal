package ua.dp.stud.news.archive.controller;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import ua.dp.stud.StudPortalLib.dto.NewsDto;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.service.NewsService;
import ua.dp.stud.StudPortalLib.service.OrganizationService;
import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.news.archive.validation.NewsValidation;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for view mode
 */
@Controller
@RequestMapping(value = "view")
public class NewsController {

    private static final Logger LOG = Logger.getLogger(NewsController.class.getName());
    //todo: put image mock to images directory
    private static final String MAIN_IMAGE_MOCK_URL = "http://www.princetonmn.org/vertical/Sites/%7BF37F81E8-174B-4EDB-91E0-1A3D62050D16%7D/uploads/News.gif";
    private static final String STR_FAIL = "fail";
    private static final String NO_IMAGE = "no-images";
    private static final String STR_EXEPT = "exception";
    private static final String ADMIN_ROLE = "Administrator";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String ADD_NEWS = "addNews";
    private static final String NEWS = "news";
    private static final String MAIN_IMAGE = "mainImage";
    //news text limitations
    private static final int NEWS_BY_PAGE = 10;
    private static final int NEARBY_PAGES = 2;
    private static final int OVERALL_PAGES = 7;
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
        
        //todo: use ternary operator
        if ((request.getParameter(CURRENT_PAGE) != null) && ("next".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage < pagesCount) {
                currentPage++;
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
        Collection<News> news = newsService.getNewsOnPage(true, currentPage, NEWS_BY_PAGE);
        Collection<NewsDto> newsDto = newsService.getDtoNews(news);
        model.addObject("newsDto", newsDto);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("newsByPage", NEWS_BY_PAGE);
        createPagination(model, currentPage, pagesCount);
        return model;
    }

    private void createPagination(ModelAndView model, Integer currentPage, Integer pagesCount) {
        Integer leftPageNumb = Math.max(1, currentPage - NEARBY_PAGES),
                rightPageNumb = Math.min(pagesCount, currentPage + NEARBY_PAGES);
        Boolean skippedBeginning = false,
                skippedEnding = false;

        if (pagesCount <= OVERALL_PAGES) {
            //all pages are shown
            leftPageNumb = 1;
            rightPageNumb = pagesCount;
        } else {
            //if farther then page #1 + '...' + nearby pages
            if (currentPage > 2 + NEARBY_PAGES) {
                // will look like 1 .. pages
                skippedBeginning = true;
            } else {
                //shows all first pages
                leftPageNumb = 1;
                //#1 + nearby pages + current + nearby pages
                rightPageNumb = currentPage+NEARBY_PAGES;
            }
            //if farther then nearby + '...' + last
            if (currentPage < pagesCount - (NEARBY_PAGES + 1)) {
                //will look like pages .. lastPage
                skippedEnding = true;
            } else {
                //shows all last pages:
                leftPageNumb = currentPage  -  NEARBY_PAGES;
                rightPageNumb = pagesCount;
            }
        }
        model.addObject("nearbyPages", NEARBY_PAGES);
        model.addObject("overallPages", OVERALL_PAGES);
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
        return getNews(newsID,request);
    }

    /**
     * Aprove
     *
     * @param request
     * @param response
     */
    @RenderMapping(params = "aproveByMyOrgs")
    public ModelAndView getNewsWaitingForAprove(ActionRequest request, ActionResponse response) {
        Collection<Organization> orgs = organizationService.getAllOrganizationByAuthor(request.getParameter("author"));
        Collection<Collection<News>> news = null;
        for (Organization org : orgs) {
            news.add(newsService.getNewsByOrg(org.getId(), false));
        }
        ModelAndView model = new ModelAndView("newsForAprove");
        model.addObject("newsForAprove", news);
        return model;
    }

    private ModelAndView getNews(Integer id,RenderRequest request) {
        News news = newsService.getNewsById(id);

        ImageImpl mImage = news.getMainImage();
        String mainImageUrl;
        //todo: ternary operator
        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage, news);
        }
        Collection<ImageImpl> additionalImages = news.getAdditionalImages();
        Integer numberView = news.getNumberOfViews();
        int currentPage;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        news.setNumberOfViews(((numberView == null) ? 0 : numberView) + 1);
        newsService.updateNews(news);
        ModelAndView model = new ModelAndView("viewSingle");
        model.addObject(NEWS, news);
        String mainImagePar = MAIN_IMAGE;
        model.addObject(mainImagePar, mainImageUrl);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("additionalImages", additionalImages);
        return model;
    }


    @ModelAttribute(value = NEWS)
    public News getCommandObject() {
        return new News();
    }
    @Autowired
    private NewsValidation newsValidation;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setDisallowedFields(MAIN_IMAGE);
        binder.setValidator(newsValidation);
    }

    private boolean updateNews(News news, CommonsMultipartFile mainImage, boolean role,
                               String screenName, ActionResponse actionResponse) {
        boolean successUpload = true;
        news.setApproved(role);
        news.setAuthor(screenName);
        //main image uploading
        try {
            if (mainImage.getSize() > 0) {
                imageService.saveMainImage(mainImage, news);
            }
        }
        catch (Exception ex) {
            successUpload = false;
            LOG.log(Level.SEVERE, "Exception: ", ex);
        }
        //success upload message
        if (successUpload) {
            actionResponse.setRenderParameter("success", "success-add");
            return true;
        } else {
            actionResponse.setRenderParameter(STR_FAIL, NO_IMAGE);
            return false;
        }
    }

    @ActionMapping(value = ADD_NEWS)
    public void addNews(@ModelAttribute(NEWS) @Valid News news,
                        BindingResult bindingResult,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse,
                        @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                        SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        //path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, NO_IMAGE);
            return;
        }

        CommonsMultipartFile f = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                        Integer.parseInt(actionRequest.getParameter("l")),
                                                        Integer.parseInt(actionRequest.getParameter("w")),
                                                        Integer.parseInt(actionRequest.getParameter("h")));
        boolean role = actionRequest.isUserInRole(ADMIN_ROLE);
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String screenName = user.getScreenName();
        //update fields for new news
        if (updateNews(news, f, role, screenName, actionResponse)) {
            news.setPublication(new Date());
            //updating info about loaded news images
            newsService.addNews(news);
            //close session
            sessionStatus.setComplete();
        }
    }

    @ActionMapping(value = "editNews")
    public void editNews(@ModelAttribute(NEWS) @Valid News news,
                         BindingResult bindingResult,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse,
                         @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                         SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            actionResponse.setRenderParameter(STR_FAIL, "msg.fail");
            return;
        }
        CommonsMultipartFile croppedImage = null;
        News oldNews = newsService.getNewsById(news.getId());
        oldNews.setText(news.getText());
        oldNews.setTopic(news.getTopic());
        oldNews.setPublicationInCalendar(news.getPublicationInCalendar());
        oldNews.setOnMainpage(news.getOnMainpage());
        oldNews.setInCalendar(news.getInCalendar());
        boolean role = oldNews.getApproved();
        //apdate author for edit
        String author = oldNews.getAuthor();
        //update fields for new news
        if (!mainImage.getOriginalFilename().equals("")) {
        	croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                               Integer.parseInt(actionRequest.getParameter("l")),
                                               Integer.parseInt(actionRequest.getParameter("w")),
                                               Integer.parseInt(actionRequest.getParameter("h")));

            if (updateNews(oldNews, croppedImage, role, author, actionResponse)) {
                newsService.updateNews(oldNews);
                //close session

            }
        }
        else
        {
            newsService.updateNews(oldNews);
        }
        sessionStatus.setComplete();
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddNews(RenderRequest request, RenderResponse response) {
        return new ModelAndView(ADD_NEWS);
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView("editNews");
        //getting news   
        int newsID = Integer.valueOf(request.getParameter("newsId"));
        News news = newsService.getNewsById(newsID);
        ImageImpl mImage = news.getMainImage();
        String mainImageUrl = (mImage == null) ? MAIN_IMAGE_MOCK_URL : imageService.getPathToLargeImage(mImage, news);
        model.getModelMap().addAttribute(NEWS, news);
        model.addObject(MAIN_IMAGE, mainImageUrl);
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
        return getNews(newsID, request);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = getMainView(request);
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
        ModelAndView model = new ModelAndView(ADD_NEWS);
        SessionErrors.add(request, request.getParameter(STR_FAIL));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView(ADD_NEWS);
        model.addObject(STR_EXEPT, request.getParameter(STR_EXEPT));
        return model;
    }
}
