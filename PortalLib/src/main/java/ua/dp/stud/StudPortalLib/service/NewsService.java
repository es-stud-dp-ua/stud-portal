package ua.dp.stud.StudPortalLib.service;


import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;

import java.util.Collection;

/**
 * @author Ivan Kamyshan
 */
public interface NewsService {
    /**
     * Method to return news by ID
     */
    News getNewsById(Integer id);

    /**
     * Method to return all news
     */
    Collection<News> getAllNews();

    /**
     * For pagination
     *
     * @param pageNumb
     * @param newsByPage
     * @return news on current page
     */
    Collection<News> getNewsOnPage(Integer pageNumb, Integer newsByPage);

    /**
     * Returns overall pages count
     *
     * @param newsByPage
     * @return pages count
     */
    Integer getPagesCount(Integer newsByPage);

    /**
     * Adds news to DB
     *
     * @param newsToAdd
     */
    void addNews(News newsToAdd);

    /**
     * add image
     *
     * @param image
     */
    void addImage(ImageImpl image);

    /**
     * gets news from dao for main page
     *
     * @return news which will be visible on Main Page
     */
    Collection<News> getNewsOnMainPage();

    /**
     * return all Categories
     *
     * @return
     */
    Collection<Category> getAllCategories();

    /**
     * updates news
     *
     * @param news
     */
    void updateNews(News news);

    /**
     * delete news
     *
     * @param news
     */
    void deleteNews(News news);

    /**
     * Delete image by id
     *
     * @param image of image for delete
     */
    void deleteImage(ImageImpl image);

    /**
     * Returns image by id
     *
     * @param id image
     * @return image that is equals id
     */
    ImageImpl getImageById(Long id);

    /**
     * Returns collection of news by author
     *
     * @param author of news
     * @return collection of news
     */
    Collection<News> getAllNewsByAuthor(String author);

    /**
     * Returns page count of news for author
     *
     * @param author of news
     * @return page count of news by author
     */
    Integer getPagesCountByAuthor(String author, Integer newsByPage);

    /**
     * Returns collection of news on page by author
     *
     * @param author
     * @param pageNumb
     * @param newsByPage
     * @return
     */
    Collection<News> getPagesNewsByAuthor(String author, Integer pageNumb, Integer newsByPage);

    /**
     * Returns collection of approved or not approved news by id organization
     *
     * @param idOrg    organization
     * @param approved of administrator
     * @return collection of news
     */
    Collection<News> getNewsByOrg(Integer idOrg, Boolean approved);

    /**
     * Returns count of news for organization's id
     *
     * @param author   author of organization
     * @param approved of administrator
     * @return count of news for organization's id
     */
    Integer getPagesCountByOrgAuthor(String author, Boolean approved, Integer newsByPage);

    /**
     * Returns collection of news on page for organization's author
     *
     * @param author     author of organization
     * @param approved   for administrator
     * @param pageNumb   number of pages
     * @param newsByPage news's count for 1 page
     * @return collection of news on page
     */
    Collection<News> getPagesNewsByOrgAuthor(String author, Boolean approved, Integer pageNumb, Integer newsByPage);

    /**
     * Return collection of news for administrator
     *
     * @param approved for administrator
     * @return collection of news for administrator
     */
    Collection<News> getAllNews(Boolean approved);

    /**
     * Return count of news for administrator
     *
     * @param approved   for administrator
     * @param newsByPage news's count per page
     * @return count of news for administrator
     */
    Integer getPagesCount(Boolean approved, Integer newsByPage);

    /**
     * Return collection news on pages for administrator
     *
     * @param approved   for administrator
     * @param pageNumb   number of page
     * @param newsByPage news by page
     * @return collection news on pages for administrator
     */
    Collection<News> getNewsOnPage(Boolean approved, Integer pageNumb, Integer newsByPage);
}