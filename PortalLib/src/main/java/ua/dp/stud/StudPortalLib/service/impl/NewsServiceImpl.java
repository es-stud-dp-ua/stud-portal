package ua.dp.stud.StudPortalLib.service.impl;

/**
 * @author Ivan Kamyshan
 */

import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.NewsService;

import java.util.Collection;
//todo: all services must use @Transactional
public class NewsServiceImpl implements NewsService {
     /**
     * dao - Dao object
     */
    private NewsDao dao;

    /**
     * setter for NewsArchiveDAO
     * @param dao   NewsDAO
     */
    public void setDao(NewsDao dao) {
        this.dao = dao;
    }

    /**
     *   returns news by id
     */
    @Override
    public News getNewsById(Integer id) {
        News news = dao.getNewsById(id);
        //todo: wtf?!
        if (news != null) {
            return news;
        }
        return null;
    }

    /**
     *   returns all news by topic
     */
    @Override
    public News getNewsByTopic(String topic) {
        News news = dao.getNewsByTopic(topic);
        //todo: facepalm
        if (news != null) {
            return news;
        }
        return null;
    }

    /**
     *   returns all news from db
     */
    @Override
    public Collection<News> getAllNews() {
        Collection<News> collection = dao.getAllNews();
        //todo:
        if (collection != null) {
            return dao.getAllNews();
        }
        return null;
    }

    /**
     *
     * @param pageNumb
     * @param newsByPage
     * @return
     */
    @Override
    public Collection<News> getNewsOnPage(Integer pageNumb, Integer newsByPage) {
        return dao.getNewsOnPage(pageNumb,newsByPage);
    }

    /**
     *  returns number of available pages
     * @param newsByPage
     * @return
     */
    @Override
    public int getPagesCount(Integer newsByPage) {
        int newsCount = dao.getCount();
        //todo: move newsCount logic to dao
        return (newsCount>0)? ((newsCount-1)/newsByPage + 1) : 0;
    }

    /**  adds news
     *
     * @param newsToAdd
     */
    @Override
    public void addNews(News newsToAdd) {
        dao.addNews(newsToAdd);
    }

    /**
     *  updates news
     * @param news
     */
    @Override
    public void updateNews(News news) {
        dao.updateNews(news);
    }

    /**
     * delete news
     * @param news
     */
    @Override
    public void deleteNews(News news) {
        dao.deleteNews(news.getId());
    }

    /**
     * add image
     * @param image
     */
    @Override
    public void addImage(ImageImpl image) {
       dao.addImage(image);
    }

    /**
     * gets news for main page
     *
     * @return news which will be visible on Main Page
     */
    @Override
    public Collection<News> getNewsOnMainPage() {
        return dao.getNewsOnMainPage();
    }

    /**
     * return Category by id
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Integer id) {
        Category cat = dao.getCategoryById(id);
        //todo:
        if (cat != null) {
            return cat;
        }
        return null;
    }

    /**
     * return all Categories
     * @return
     */
    @Override
    public Collection<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    /**
     * Delete image by id
     * @param image of image for delete
     */
	@Override
    public void deleteImage(ImageImpl image)
	{
        dao.deleteImage(image.getId());
    }

    /**
     * Returns image by id
     * @param id image
     * @return image that is equals id
     */
	@Override
    public ImageImpl getImageById(Long id)
	{
        //todo:
        ImageImpl image = dao.getImageById(id);
        if (image != null) {
            return image;
        }
        return null;
    }

    /**
     * Returns collection of news by author
     * @param author of news
     * @return collection of news
     */
    @Override
    public Collection<News> getAllNewsByAuthor(String author)
    {
        return dao.getAllNewsByAuthor(author);
    }

    /**
     * Returns page count of news for author
     * @param author of news
     * @return page count of news by author
     *
     */
    @Override
    public int getPagesCountByAuthor(String author, Integer newsByPage)
    {
        int newsCount = dao.getCountByAuthor(author);
        //todo:
        return (newsCount > 0) ? ((newsCount - 1) / newsByPage + 1) : 0;
    }

    /**
     * Returns collection of news on page by author
     * @param author
     * @param pageNumb
     * @param newsByPage
     * @return
     */
    @Override
    public Collection<News> getPagesNewsByAuthor(String author, Integer pageNumb, Integer newsByPage)
    {
        return dao.getPagesNewsByAuthor(author, pageNumb, newsByPage);
    }

    /**
     * Returns collection of approved or not approved news by id organization
     * @param IdOrg organization
     * @param approved of administrator
     * @return collection of news
     */
    @Override
    public Collection<News> getNewsByOrg(Integer IdOrg, Boolean approved)
    {
        return dao.getNewsByOrg(IdOrg, approved);
    }

    /**
     * Returns count of news for organization's id
     * @param author author of organization
     * @param approved of administrator
     * @return count of news for organization's id
     */
    @Override
    public int getPagesCountByOrgAuthor(String author, Boolean approved, Integer newsByPage)
    {
        int newsCount = dao.getCountByOrgAuthor(author, approved);
        //todo:
        return (newsCount > 0) ? ((newsCount - 1) / newsByPage + 1) : 0;
    }

    /**
     * Returns collection of news on page for organization's author
     * @param author author of organization
     * @param approved for administrator
     * @param pageNumb number of pages
     * @param newsByPage news's count for 1 page
     * @return collection of news on page
     */
    @Override
    public Collection<News> getPagesNewsByOrgAuthor(String author, Boolean approved, Integer pageNumb, Integer newsByPage)
    {
        return dao.getPagesNewsByOrgAuthor(author, approved, pageNumb, newsByPage);
    }

    /**
     * Return collection of news for administrator
     * @param approved for administrator
     * @return collection of news for administrator
     */
    @Override
    public Collection<News> getAllNews(Boolean approved)
    {
        return dao.getAllNews(approved);
    }

    /**
     * Return count of news for administrator
     * @param approved for administrator
     * @param newsByPage news's count per page
     * @return count of news for administrator
     */
    @Override
    public int getPagesCount(Boolean approved, Integer newsByPage)
    {
        int newsCount = dao.getCount(approved);
        //todo:
        return (newsCount > 0) ? ((newsCount - 1) / newsByPage + 1) : 0;
    }

    /**
     * Return collection news on pages for administrator
     * @param approved for administrator
     * @param pageNumb number of page
     * @param newsByPage news by page
     * @return collection news on pages for administrator
     */
    @Override
    public Collection<News> getNewsOnPage(Boolean approved, Integer pageNumb, Integer newsByPage)
    {
        return dao.getNewsOnPage(approved, pageNumb, newsByPage);
    }
}
