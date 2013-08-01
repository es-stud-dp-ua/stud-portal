package ua.dp.stud.StudPortalLib.service.impl;

/**
 * @author Ivan Kamyshan
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.NewsService;

import java.util.Collection;
import ua.dp.stud.StudPortalLib.dao.impl.BaseDaoImpl;

@Service("newsService")
@Transactional
public class NewsServiceImpl implements NewsService {
    /**
     * dao - Dao object
     */
    @Autowired
    private NewsDao dao;
   
    /**
     * setter for NewsArchiveDAO
     *
     * @param dao NewsDAO
     */
    @Transactional(readOnly = false)
    public void setDao(NewsDao dao) {
        this.dao = dao;
    }
 

    /**
     * adds news
     *
     * @param newsToAdd
     */
    @Override
    @Transactional(readOnly = false)
    public void addNews(News newsToAdd) {
        dao.save(newsToAdd);
    }

    /**
     * updates news
     *
     * @param news
     */
    @Override
    @Transactional(readOnly = false)
    public void updateNews(News news) {
        dao.update(news);
    }

    /**
     * delete news
     *
     * @param news
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteNews(News news) {
        dao.delete(news.getId());
    }

    /**
     * add image
     *
     * @param image
     */
    @Override
    @Transactional(readOnly = false)
    public void addImage(ImageImpl image) {
        dao.addImage(image);
    }

    /**
     * returns news by id
     */
    @Override
    @Transactional(readOnly = true)
    public News getNewsById(Integer id) {
        return dao.getById(id);
    }

    /**
     * returns all news from db
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getAllNews() {
        return dao.getAllNews();
    }

    /**
     * @param pageNumb
     * @param newsByPage
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getNewsOnPage(Integer pageNumb, Integer newsByPage) {
        return dao.getNewsOnPage(pageNumb, newsByPage);
    }

    /**
     * returns number of available pages
     *
     * @param newsByPage
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Integer newsByPage) {
        return dao.calcPages(dao.getCount(), newsByPage);
    }

    /**
     * gets news for main page
     *
     * @return news which will be visible on Main Page
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getNewsOnMainPage() {
        return dao.getNewsOnMainPage();
    }

    /**
     * return all Categories
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    /**
     * Delete image by id
     *
     * @param image of image for delete
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteImage(ImageImpl image) {
        dao.deleteImage(image.getId());
    }

    /**
     * Returns image by id
     *
     * @param id image
     * @return image that is equals id
     */
    @Override
    @Transactional(readOnly = true)
    public ImageImpl getImageById(Long id) {
        return dao.getImageById(id);
    }

    /**
     * Returns collection of news by author
     *
     * @param author of news
     * @return collection of news
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getAllNewsByAuthor(String author) {
        return dao.getAllNewsByAuthor(author);
    }

    /**
     * Returns page count of news for author
     *
     * @param author of news
     * @return page count of news by author
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCountByAuthor(String author, Integer newsByPage) {
        return dao.calcPages(dao.getCountByAuthor(author), newsByPage);
    }

    /**
     * Returns collection of news on page by author
     *
     * @param author
     * @param pageNumb
     * @param newsByPage
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getPagesNewsByAuthor(String author, Integer pageNumb, Integer newsByPage) {
        return dao.getPagesObjectByAuthor(author, pageNumb, newsByPage);
    }

    /**
     * Returns collection of approved or not approved news by id organization
     *
     * @param idOrg    organization
     * @param approved of administrator
     * @return collection of news
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getNewsByOrg(Integer idOrg, Boolean approved) {
        return dao.getNewsByOrg(idOrg, approved);
    }

    /**
     * Returns count of news for organization's id
     *
     * @param author   author of organization
     * @param approved of administrator
     * @return count of news for organization's id
     */
    @Override
    public Integer getPagesCountByOrgAuthor(String author, Boolean approved, Integer newsByPage) {
        return dao.calcPages(dao.getCountByOrgAuthor(author, approved), newsByPage);
    }

    /**
     * Returns collection of news on page for organization's author
     *
     * @param author     author of organization
     * @param approved   for administrator
     * @param pageNumb   number of pages
     * @param newsByPage news's count for 1 page
     * @return collection of news on page
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getPagesNewsByOrgAuthor(String author, Boolean approved, Integer pageNumb, Integer newsByPage) {
        return dao.getPagesNewsByOrgAuthor(author, approved, pageNumb, newsByPage);
    }

    /**
     * Return collection of news for administrator
     *
     * @param approved for administrator
     * @return collection of news for administrator
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getAllNews(Boolean approved) {
        return dao.getAllNews(approved);
    }

    /**
     * Return count of news for administrator
     *
     * @param approved   for administrator
     * @param newsByPage news's count per page
     * @return count of news for administrator
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Boolean approved, Integer newsByPage) {
        return dao.calcPages(dao.getCount(approved), newsByPage);
    }

    /**
     * Return collection news on pages for administrator
     *
     * @param approved   for administrator
     * @param pageNumb   number of page
     * @param newsByPage news by page
     * @return collection news on pages for administrator
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<News> getNewsOnPage(Boolean approved, Integer pageNumb, Integer newsByPage) {
        return dao.getObjectOnPage(approved, pageNumb, newsByPage);
    }
    
    /**
     * @param news
     * @return  true if news with this name is present
     */
      @Override
    public Boolean isUnique(News news){
        if (dao.getNewsByName(news.getTopic())!=null){
               return true;
           }
        return false; 
    }
}
