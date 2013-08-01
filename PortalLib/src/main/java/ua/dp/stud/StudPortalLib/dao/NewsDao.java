package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;

import java.util.Collection;
import ua.dp.stud.StudPortalLib.dao.impl.BaseDaoImpl;

/**
 * @author Lukash Roman
 * @author Vladislav Pikus
 */
public interface NewsDao extends DaoForApprove<News> {
    
     public Collection<News> getAllNews(Boolean approved);

     public News getNewsByName(String topic);
    /**
     * getAllNews
     *
     * @return
     */
    Collection<News> getAllNews();

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
     * Returns collection of news by author
     *
     * @param author of news
     * @return collection of news
     */
    Collection<News> getAllNewsByAuthor(String author);

    /**
     * Returns collection of approved or not approved news by id organization
     *
     * @param idOrg organization
     * @param approved of administrator
     * @return collection of news
     */
    Collection<News> getNewsByOrg(Integer idOrg, Boolean approved);

    /**
     * Returns count of news for organization's author
     *
     * @param author author of organization
     * @param approved of administrator
     * @return count of news for organization's id
     */
    Integer getCountByOrgAuthor(String author, Boolean approved);

    /**
     * Returns collection of news on page for organization's author
     *
     * @param author author of organization
     * @param approved for administrator
     * @param pageNumb number of pages
     * @param newsByPage news's count for 1 page
     * @return collection of news on page
     */
    Collection<News> getPagesNewsByOrgAuthor(String author, Boolean approved, Integer pageNumb, Integer newsByPage);
}
