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
    private static String strFail = "fail";
    private static String strNoImage = "no-images";
    private static String strExept = "exception";
    private static String administratorRole = "Administrator";
    private static String userRole = "User";
    //news text limitations
    private static final int MINTITLESYMBOLS = 4;
    private static final int MINTEXTSYMBOLS = 100;

    @Autowired
    @Qualifier(value = "newsService")
    //todo: change variable's name
    private NewsService service;
    @Autowired
    @Qualifier(value = "orgsService")
    private OrganizationService orgsService;
    
   public void setServiceOrg(OrganizationService orgsService) {
        this.orgsService = orgsService;
    }
      
    public void setService(NewsService service) {
        this.service = service;
    }
    //todo: don't use variables that can be changed
    private int pagesCount;
    private int currentPage = 1;
    private static final int NEWS_BY_PAGE = 10;

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
        //todo: add view name as constructor arg to ModelAndView
        model.setViewName("viewAll");

        pagesCount = service.getPagesCount(NEWS_BY_PAGE);

        Collection<News> news = service.getNewsOnPage(true, currentPage, NEWS_BY_PAGE);
        model.addObject("news", news);
        model.addObject("currentPage", currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("newsByPage", NEWS_BY_PAGE);

        //reset to default if no actions will be made
        currentPage = 1;
        return model;
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
    private ModelAndView GetNewsWaitingForAprove(ActionRequest request, ActionResponse response){
    Collection<Organization> orgs=orgsService.getAllOrganizationByAuthor(request.getParameter("author"));
    Collection<Collection<News>> news=null;
    for (Organization org:orgs){
       news.add(service.getNewsByOrg(org.getId(),false)); 
    }
    ModelAndView model = new ModelAndView();
    model.addObject("newsForAprove", news);
    return model;
    }
     
    private ModelAndView getNews(Integer id)
    {
        News news = service.getNewsById(id);
        ImageService imageService = new ImageService();

        ImageImpl mImage = news.getMainImage();
        String mainImageUrl;

        if (mImage == null) {
            mainImageUrl = MAIN_IMAGE_MOCK_URL;
        } else {
            mainImageUrl = imageService.getPathToLargeImage(mImage,news);
        }
        Collection<ImageImpl> additionalImages = news.getAdditionalImages();


        ModelAndView model = new ModelAndView();
        //todo: add view name as constructor arg to ModelAndView
        model.setViewName("viewSingle");
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
        currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (currentPage > 1) {
            currentPage -= 1;
        }
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
                                     ActionResponse actionResponse, String strFail, String strNoImage, News somenews)
            throws IOException, SystemException, PortalException {

        boolean successUpload = true;

        ImageService imageService = new ImageService();
        //get topic from views
        if (frmTopic.length()< MINTITLESYMBOLS)
        {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        somenews.setTopic(frmTopic);
        //get text from views
        if (frmText.length()< MINTEXTSYMBOLS)
        {
            actionResponse.setRenderParameter(strFail, strFail);
            return false;
        }
        somenews.setText(frmText);
        //set in calendar instance from views
        somenews.setInCalendar((frmInCalendar) ? true : false);
        //if admin add then get on main page visibility from views
        somenews.setOnMainpage((frmOnMainPage) ? true : false);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //get date for calendar view from views
        //todo: use one try block
        try {
            Date startDate = df.parse(frmDateInCalendar);
            somenews.setPublicationInCalendar(startDate);
        } catch (ParseException ex) {
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(strExept, sw.toString());
        }
        //make decision for athor rules
        if (frmRole.equals(administratorRole)) {
            somenews.setAuthor(role);
            somenews.setApproved(true);
        } else
        if (frmRole.equals(userRole)) {
            somenews.setAuthor(role);
            somenews.setApproved(false);
        }
        //main image uploading
        try {
            if (mainImage.getSize() > 0) {
                try {
                    imageService.saveMainImage(mainImage, somenews);
                } catch (Exception ex) {
                    LOGGER.warning("Error while uploading " + mainImage.getOriginalFilename());
                    successUpload = false;
                }
            }
            //image collection uploading
            if (images != null && images.length > 0) {
                try {
                    for (CommonsMultipartFile file : images) {
                        imageService.saveAdditionalImages(file, somenews);
                    }
                } catch (Exception ex) {
                    LOGGER.warning("Error while uploading ");
                    successUpload = false;
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(strExept, sw.toString());
            sw.close();
        }
        //success upload message
        if (successUpload) {
            actionResponse.setRenderParameter("success", "success-add");
            return true;
        } else {
            actionResponse.setRenderParameter(strFail, strNoImage);
        }
        return false;
    }
    //todo:use try ... catch block and logging
    @ActionMapping(value = "addNews")
    public void addNews(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                        @RequestParam("images") CommonsMultipartFile[] images,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
        //path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(strFail, strNoImage);
            return;
        }
        //create new object News
        News news = new News();
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text =actionRequest.getParameter("text");
        String dateInCalendar = actionRequest.getParameter("startDate");
        String role;
        //todo: move code about resizing image to appropriate service
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
        InputStream input = new FileInputStream(temp);
        int ret;
        //todo ONE TRY BLOCK!
        try {
            DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("fileData", "image/jpeg", true, temp.getName());
            OutputStream os = fileItem.getOutputStream();
            while ((ret = input.read()) != -1) {
                os.write(ret);
            }
            os.flush();
            f = new CommonsMultipartFile(fileItem);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            actionResponse.setRenderParameter(strExept, sw.toString());
            sw.close();
        } finally {
            //closing stream for avoid memory leaks
            input.close();
        }
        if (actionRequest.isUserInRole(administratorRole))
        {
            role=administratorRole;
        }
        else
        {
            role=userRole;
        }
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String  usRole=user.getScreenName();
        Boolean inCalendar = actionRequest.getParameter("inCalendar") != null;
        Boolean onMainPage = actionRequest.getParameter("onMainPage") != null;

        //update fields for new news
        if (updateNewsFields(f, images, topic, text, inCalendar, onMainPage,
                         dateInCalendar, role, usRole, actionResponse, strFail, strNoImage, news))
        {
            news.setPublication(new Date());
            //updating info about loaded news images
            try
            {
                service.addNews(news);
                //close session
                sessionStatus.setComplete();
            } catch (Exception ex) {
                //exception controller
                //todo:use logging
                StringWriter sw = new StringWriter();
                actionResponse.setRenderParameter(strExept, sw.toString());
                sw.close();
            }
        }
    }

    @ActionMapping(value = "editNews")
    public void editNews(@RequestParam("mainImage") CommonsMultipartFile mainImage,
                        @RequestParam("images") CommonsMultipartFile[] images,
                        ActionRequest actionRequest,
                        ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
        //getting current news
        int newsID = Integer.valueOf(actionRequest.getParameter("newsId"));
        News news = service.getNewsById(newsID);
        //getting all parameters from form
        String topic = actionRequest.getParameter("topic");
        String text =actionRequest.getParameter("text");
        String dateInCalendar = actionRequest.getParameter("startDate");
        String role=null;
        if (actionRequest.isUserInRole(administratorRole))
        {
            role=administratorRole;
        }
        else
        {
            role=userRole;
        }
        Boolean inCalendar = actionRequest.getParameter("inCalendar") != null;
        Boolean onMainPage = actionRequest.getParameter("onMainPage") != null;
        //apdate author for edit
        String  usRole=news.getAuthor();
        //update fields for new news
        if (updateNewsFields(mainImage, images, topic, text, inCalendar, onMainPage,
                dateInCalendar, role,usRole, actionResponse, strFail, strNoImage, news))
        {
            //updating info about loaded news
            /*try
            { */
                service.updateNews(news);
                //close session
                sessionStatus.setComplete();
           /* } catch (Exception ex) {
                //exception controller
                StringWriter sw = new StringWriter();
                actionResponse.setRenderParameter(strExept, sw.toString());
                sw.close();
            }    */
        }
    }


    @RenderMapping(params = "mode=add")
    public ModelAndView showAddNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        //set view for add
        model.setViewName("addNews");
        return model;
    }

    @RenderMapping(params = "mode=edit")
     public ModelAndView showEditNews(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        String publicationInCalendar = "";
        //getting news
        int newsID = Integer.valueOf(request.getParameter("newsId"));
        News news = service.getNewsById(newsID);
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
        News news = service.getNewsById(newsID);
        ImageService imageService = new ImageService();
        imageService.deleteDirectory(news);
        //delete chosen news
        service.deleteNews(news);
        return showAddSuccess(request, response);
    }
	
	@RenderMapping(params = "mode=delImage")
    public ModelAndView delImage(RenderRequest request, RenderResponse response)
	{
        long imageID = Long.valueOf(request.getParameter("imageId"));
        ImageImpl image = service.getImageById(imageID);
        Integer newsID = image.getBase().getId();
        ImageService imageService = new ImageService();
		//delete image from folder
		imageService.deleteImage(image, image.getBase());
		//delete image from data base
		service.deleteImage(image);
        return getNews(newsID);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);
        String strSuccess="success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddNews(request, response);
        SessionErrors.add(request, request.getParameter(strFail));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddNews(request, response);
        model.addObject(strExept, request.getParameter(strExept));
        return model;
    }
}

