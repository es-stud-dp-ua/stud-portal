package ua.dp.stud.newsArchive.controller;

import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.NewsService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IGImageLocalServiceUtil.class,
                 IGFolderLocalServiceUtil.class,
                 ImageServletTokenUtil.class,
                 UserLocalServiceUtil.class,
                 SessionMessages.class,
                 SessionErrors.class})
public class NewsArchiveControllerTest {
    private static final int NEWS_BY_PAGE = 10;
    private int pagesCount, currentPage;
    private NewsController controller  = new NewsController();
    private ThemeDisplay mockThemeDisplay;
    private NewsService mockService;
    private Collection<News> news;
    private CommonsMultipartFile mainImage;
    private CommonsMultipartFile image1, image2;
    private User mockUser;
    private IGFolder mockParentFolder, mockFolder;
    private IGImage mockMainImage;
    private News mockNews;

    @Before
    public void setUp(){
        PowerMockito.mockStatic(IGImageLocalServiceUtil.class);
        PowerMockito.mockStatic(ImageServletTokenUtil.class);
        PowerMockito.mockStatic(UserLocalServiceUtil.class);
        PowerMockito.mockStatic(IGFolderLocalServiceUtil.class);
        PowerMockito.mockStatic(SessionMessages.class);
        PowerMockito.mockStatic(SessionErrors.class);

        mockService = mock(NewsService.class);

        pagesCount = 3;
        currentPage = 1;
        news = new LinkedList<News>(Collections.nCopies(10,new News()));
        when(mockService.getPagesCount(NEWS_BY_PAGE)).thenReturn(pagesCount);
        when(mockService.getNewsOnPage(true, currentPage,NEWS_BY_PAGE)).thenReturn(news);
        controller.setService(mockService);

        mockThemeDisplay = mock(ThemeDisplay.class);

        IGImage imageMock1 = mock(IGImage.class);
        when(imageMock1.getLargeImageId()).thenReturn((long)1);
        IGImage imageMock2 = mock(IGImage.class);
        when(imageMock2.getLargeImageId()).thenReturn((long) 2);
        PowerMockito.when(ImageServletTokenUtil.getToken(anyLong())).thenReturn("token");

        PowerMockito.doNothing().when(SessionMessages.class);
        SessionMessages.add(any(RenderRequest.class),any(String.class));
        PowerMockito.doNothing().when(SessionErrors.class);
        SessionErrors.add(any(RenderRequest.class),any(String.class));
        
        mainImage = mock(CommonsMultipartFile.class);
        image1 = mock(CommonsMultipartFile.class);
        image2 = mock(CommonsMultipartFile.class);

        mockUser = mock(User.class);
        mockParentFolder = mock(IGFolder.class);
        when(mockParentFolder.getFolderId()).thenReturn(0l);
        mockFolder = mock(IGFolder.class);
        mockMainImage = mock(IGImage.class);
        when(mockMainImage.getLargeImageId()).thenReturn(0l);
        mockNews = mock(News.class);
        when(mockNews.getId()).thenReturn(0);

        try {
            PowerMockito.when(IGImageLocalServiceUtil.getImage(1)).thenReturn(imageMock1);
            PowerMockito.when(IGImageLocalServiceUtil.getImage(2)).thenReturn(imageMock2);
            when(UserLocalServiceUtil.getUser(0l)).thenReturn(mockUser);
            when(IGFolderLocalServiceUtil.getFolder(anyLong(),anyLong(),anyString()))
                    .thenReturn(mockParentFolder);
            when(IGFolderLocalServiceUtil.addFolder(anyLong(),anyLong(),anyString(),anyString(),any(ServiceContext.class)))
                    .thenReturn(mockFolder);
            when(IGImageLocalServiceUtil.addImage(anyLong(), anyLong(), anyLong(),
                    anyString(), anyString(), anyString(),
                    any(InputStream.class), anyString(), any(ServiceContext.class))).thenReturn(mockMainImage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testShowView(){
        ModelAndView model = showView();

        assertEquals(news,model.getModel().get("news"));
        assertEquals(currentPage,model.getModel().get("currentPage"));
        assertEquals(pagesCount,model.getModel().get("pagesCount"));
        assertEquals(NEWS_BY_PAGE,model.getModel().get("newsByPage"));
        assertEquals("viewAll",model.getViewName());
    }

    @Test
    public void testShowSelectedNews(){
        RenderResponse response = new MockRenderResponse();
        RenderRequest request = mock(RenderRequest.class);
        
        News mockNews = new News();
        mockNews.setId(1);
//        mockNews.setMainImageId(0l);
        Collection<Long> photosIDs = new LinkedList<Long>(Arrays.asList((long)1, (long)2));
//        mockNews.setAdditionalPicturesHolder(photosIDs);
        ThemeDisplay mockThemeDisplay = mock(ThemeDisplay.class);

        when(request.getParameter("newsID")).thenReturn("1");
        when(request.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
        when(mockService.getNewsById(1)).thenReturn(mockNews);
        when(mockThemeDisplay.getPortalURL()).thenReturn("http://portal");
        when(mockThemeDisplay.getPathImage()).thenReturn("images");

        ModelAndView model = controller.showSelectedNews(request,response);

        assertEquals("viewSingle",model.getViewName());
        assertEquals(mockNews.getId(),((News)model.getModel().get("news")).getId());
//        assertEquals(photosIDs.size(),((Collection)model.getModel().get("additionalImages")).size());
//        assertEquals(true,((String)model.getModel().get("mainImage")).endsWith("img_id=0&t=token"));
    }

    @Test
    public void testShowPage(){
        MockActionResponse actionResponse = new MockActionResponse();
        ActionRequest actionRequest = mock(ActionRequest.class);

        when(actionRequest.getParameter("pageNumber")).thenReturn("2");
        controller.showPage(actionRequest,actionResponse);

        RenderResponse response = new MockRenderResponse();
        RenderRequest request = mock(RenderRequest.class);

        int pagesCount = 3, currentPage = 2;
        when(mockService.getNewsOnPage(true, currentPage,NEWS_BY_PAGE)).thenReturn(new LinkedList<News>());

        ModelAndView model = controller.showView(request, response);
        assertEquals(currentPage,model.getModel().get("currentPage"));
    }

    @Test
    public void testShowNextPage(){
        //applying params
        showView();

        MockActionResponse actionResponse = new MockActionResponse();
        ActionRequest actionRequest = mock(ActionRequest.class);
        when(actionRequest.getParameter("pageNumber")).thenReturn("2");

        controller.showPage(actionRequest,actionResponse);
        controller.showNextPage(actionRequest,actionResponse);
        ModelAndView model = showView();
        assertEquals(3,model.getModel().get("currentPage"));
    }

    @Test
    public void testShowNextAfterLastPage(){
        //applying params
        showView();

        MockActionResponse actionResponse = new MockActionResponse();
        ActionRequest actionRequest = mock(ActionRequest.class);
        when(actionRequest.getParameter("pageNumber")).thenReturn("3");

        controller.showNextPage(actionRequest, actionResponse);
        ModelAndView model = showView();
        assertEquals(3,model.getModel().get("currentPage"));
    }

    @Test
    public void testShowPrevPage(){
        //applying params
        showView();

        MockActionResponse actionResponse = new MockActionResponse();
        ActionRequest actionRequest = mock(ActionRequest.class);
        when(actionRequest.getParameter("pageNumber")).thenReturn("2");

        controller.showPrevPage(actionRequest, actionResponse);
        ModelAndView model = showView();
        assertEquals(1,model.getModel().get("currentPage"));
    }

    @Test
    public void testShowPrevBeforeFirstPage(){
        //applying params
        showView();

        MockActionResponse actionResponse = new MockActionResponse();
        ActionRequest actionRequest = mock(ActionRequest.class);
        when(actionRequest.getParameter("pageNumber")).thenReturn("1");

        controller.showPrevPage(actionRequest, actionResponse);
        ModelAndView model = showView();
        assertEquals(1,model.getModel().get("currentPage"));
    }

    private ModelAndView showView(){
        RenderResponse response = new MockRenderResponse();
        RenderRequest request = new MockRenderRequest();
        return controller.showView(request, response);
    }

    @Test
    public void testShowAddNewsPage(){
        RenderResponse response = new MockRenderResponse();
        RenderRequest request = new MockRenderRequest();
        ModelAndView model = controller.showAddNews(request, response);

        assertEquals("addNews",model.getViewName());
    }

    @Test
    public void testShowAddNewsSuccessPage(){
        RenderResponse response = new MockRenderResponse();
        RenderRequest request = mock(RenderRequest.class);
        when(request.getParameter("success")).thenReturn("message");

        ModelAndView model = controller.showAddSuccess(request, response);
        assertEquals("viewAll",model.getViewName());

        PowerMockito.verifyStatic();
        SessionMessages.add(request,"message");
    }

    @Test
     public void testShowAddNewsFailPage(){
        RenderResponse response = new MockRenderResponse();
        RenderRequest request = mock(RenderRequest.class);
        when(request.getParameter("fail")).thenReturn("message");

        ModelAndView model = controller.showAddFailed(request, response);
        assertEquals("addNews",model.getViewName());

        PowerMockito.verifyStatic();
        SessionErrors.add(request,"message");
    }

    @Test
    public void testAddNewsNoMainImage(){
        ActionRequest  request = mock(ActionRequest.class);
        ActionResponse response = mock(ActionResponse.class);
        SessionStatus sessionStatus = mock(SessionStatus.class);
        when(mainImage.getOriginalFilename()).thenReturn("");

        try{
            controller.addNews(mainImage, null, request, response, sessionStatus);
            verify(response).setRenderParameter("fail","no-images");
        }
        catch(Exception ex){
            ex.printStackTrace();    
        }
    }

//    @Test
//    public void testAddNewsNoAdditionalImages(){
//        ActionRequest  request = mock(ActionRequest.class);
//        ActionResponse response = mock(ActionResponse.class);
//        SessionStatus sessionStatus = mock(SessionStatus.class);
//        HashMap<String,String> userInfo = new HashMap<String, String>();
//        userInfo.put("liferay.user.id","0");
//
//        when(mainImage.getOriginalFilename()).thenReturn("mainImage");
//        when(request.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
//        when(request.getAttribute(ActionRequest.USER_INFO)).thenReturn(userInfo);
//        when(request.getParameter("topic")).thenReturn("topic");
//        when(request.getParameter("text")).thenReturn("text");
//        when(request.getParameter("inCalendar")).thenReturn(null);
//        when(request.getParameter("onMainPage")).thenReturn(null);
//        when(mockUser.getFullName()).thenReturn("userName");
//        when(request.isUserInRole("Administrator")).thenReturn(true);
//        when(mockThemeDisplay.getScopeGroupId()).thenReturn(0l);
//        when(mockService.addNews(any(News.class))).thenReturn(mockNews);
//
//        try{
//            controller.addNews(mainImage, new CommonsMultipartFile[]{},
//                                request, response, sessionStatus);
//            verify(response).setRenderParameter("success","success-add");
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testAddNewsAllImages(){
//        ActionRequest  request = mock(ActionRequest.class);
//        ActionResponse response = mock(ActionResponse.class);
//        SessionStatus sessionStatus = mock(SessionStatus.class);
//        HashMap<String,String> userInfo = new HashMap<String, String>();
//        userInfo.put("liferay.user.id","0");
//
//        when(mainImage.getOriginalFilename()).thenReturn("mainImage");
//        when(request.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
//        when(request.getAttribute(ActionRequest.USER_INFO)).thenReturn(userInfo);
//        when(request.getParameter("topic")).thenReturn("topic");
//        when(request.getParameter("text")).thenReturn("text");
//        when(request.getParameter("inCalendar")).thenReturn(null);
//        when(request.getParameter("onMainPage")).thenReturn(null);
//        when(mockUser.getFullName()).thenReturn("userName");
//        when(request.isUserInRole("Administrator")).thenReturn(true);
//        when(mockThemeDisplay.getScopeGroupId()).thenReturn(0l);
//        when(mockService.addNews(any(News.class))).thenReturn(mockNews);
//        when(image1.getOriginalFilename()).thenReturn("image1");
//        when(image2.getOriginalFilename()).thenReturn("image2");
//
//        try{
//            controller.addNews(mainImage, new CommonsMultipartFile[]{image1,image2},
//                    request, response, sessionStatus);
//            verify(response).setRenderParameter("success","success-add");
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
}
