package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;

import java.util.Collection;

/**
 * @author Lukash Roman
 * @author Vladislav Pikus
 */
public interface NewsDao {
    /**
     * addNews
     *
     * @param news
     * @return adding news
     */
    News addNews(News news);

    /**
     * getNewsById
     *
     * @param id of news
     * @return
     */
    News getNewsById(Integer id);

    /**
     * updateNews
     *
     * @param news was need to update
     */
    void updateNews(News news);

    /**
     * deleteNews
     *
     * @param id
     * @return
     */
    void deleteNews(Integer id);

    /**
     * getAllNews
     *
     * @return
     */
    Collection<News> getAllNews();

    /**
     * getCount
     *
     * @return
     */
    Integer getCount();

    /**
     * persist ImageImpl
     *
     * @param image
     * @return
     */
    void addImage(ImageImpl image);

    /**
     * addCategory
     *
     * @param cat
     * @return
     */
    Category addCategory(Category cat);

    /**
     * updateCategory
     *
     * @param id
     * @param cat
     * @return
     */
    int updateCategory(Integer id, String cat);

    /**
     * deleteCategory
     *
     * @param id
     * @return
     */
    int deleteCategory(Integer id);

    /**
     * For pagination
     *
     * @param pageNumb
     * @param newsByPage
     * @return
     */
    Collection<News> getNewsOnPage(Integer pageNumb, Integer newsByPage);

    /**
     * gets news for main page
     *
     * @return news which will be visible on Main Page
     */
    Collection<News> getNewsOnMainPage();

    /**
     * Returns all categories
     *
     * @return collection categories
     */
    Collection<Category> getAllCategories();

    /**
     * Delete image by id
     *
     * @param id of image for delete
     */
    void deleteImage(Long id);

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
     * Returns count of news for author
     *
     * @param author of news
     * @return count of news by author
     */
    Integer getCountByAuthor(String author);

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
     * Returns count of news for organization's author
     *
     * @param author   author of organization
     * @param approved of administrator
     * @return count of news for organization's id
     */
    Integer getCountByOrgAuthor(String author, Boolean approved);

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
     * Returns all news by approved
     *
     * @param approved of administrator
     * @return collection of news
     */
    Collection<News> getAllNews(Boolean approved);

    /**
     * Returns count of news by approved
     *
     * @param approved of administrator
     * @return returns count of approved news
     */
    Integer getCount(Boolean approved);

    /**
     * Returns a collection of news per page for a set number
     *
     * @param approved   of administrator
     * @param pageNumb   page number
     * @param newsByPage count news by page
     * @return collection for page number
     */
    Collection<News> getNewsOnPage(Boolean approved, Integer pageNumb, Integer newsByPage);

    Integer calcPages(Integer count, Integer perPage);
}
