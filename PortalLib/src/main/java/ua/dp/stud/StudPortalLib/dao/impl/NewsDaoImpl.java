package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;

import java.util.Collection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class is used to work with the database
 *
 * @author Roman Lukash
 * @author Vladislav Pikus
 */
@Repository("newsDao")
public class NewsDaoImpl extends BaseDaoImpl<News> implements NewsDao<News> {

    private static final String AUTHOR = "author";
    private static final String APPROVED = "approved";

    /**
     * Method return news form database by id
     *
     * @param id -ID of news that we want to get
     * @return The news which ID = id;
     */
    @Override
    public News getNewsById(Integer id) {
        News news = (News) getSession().get(News.class, id);
        Hibernate.initialize(news.getAdditionalImages());
        return news;
    }

    /**
     * deleteNews
     *
     * @param id of news
     */
    @Override
    public void deleteNews(Integer id) {
        News news = (News) getSession().get(News.class, id);
        ImageImpl image = news.getMainImage();
        getSession().delete(image);
        getSession().delete(news);
    }

    /**
     * getAllNews
     *
     * @return Collection of News
     */
    @Override
    public Collection<News> getAllNews() {
        return getSession().createCriteria(News.class).addOrder(Order.desc("publication")).list();
    }

    /**
     * getCount
     *
     * @return coutn of news
     */
    @Override
    public Integer getCount() {
        return ((Long) getSession().createCriteria(News.class).setProjection(Projections.rowCount())
                .uniqueResult()).intValue();
    }

    /**
     * updateCategory
     *
     * @param id
     * @param cat
     * @return 1 if updating is false and 0 other way
     */
    @Override
    public int updateCategory(Integer id, String cat) {
        return getSession().createQuery("Update Category SET categoryName = :categoryName WHERE id = :id")
                .setParameter("categoryName", cat)
                .setParameter("id", id).executeUpdate();
    }

    /**
     * deleteCategory
     *
     * @param id
     * @return
     */
    @Override
    public int deleteCategory(Integer id) {
        return getSession().createQuery("DELETE FROM Category WHERE id = :id").setParameter("id", id).executeUpdate();
    }

    /**
     * For pagination
     *
     * @param pageNumb
     * @param newsByPage
     * @return
     */
    @Override
    public Collection<News> getNewsOnPage(Integer pageNumb, Integer newsByPage) {
        int firstResult = (pageNumb - 1) * newsByPage;
        return (Collection<News>) getSession().createCriteria(News.class).addOrder(Order.desc("id"))
                .setFirstResult(firstResult).setMaxResults(newsByPage).list();

    }

    /**
     * gets news for main page
     *
     * @return news which will be visible on Main Page
     */
    @Override
    public Collection<News> getNewsOnMainPage() {
        return getSession().createCriteria(News.class).addOrder(Order.desc("publication")).
                add(Restrictions.eq("onMainpage", true)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * Returns all categories
     *
     * @return collection categories
     */
    @Override
    public Collection<Category> getAllCategories() {
        return getSession().createCriteria(Category.class).addOrder(Order.asc("categoryName")).list();
    }

    /**
     * Returns collection of news by author
     *
     * @param author of news
     * @return collection of news
     */
    @Override
    public Collection<News> getAllNewsByAuthor(String author) {
        return getSession().getNamedQuery("News.getByAuthor").setParameter(AUTHOR, author).list();
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
    public Collection<News> getPagesNewsByAuthor(String author, Integer pageNumb, Integer newsByPage) {
        int firstResult = (pageNumb - 1) * newsByPage;
        return getSession().getNamedQuery("News.getByAuthor").setParameter(AUTHOR, author)
                .setFirstResult(firstResult).setMaxResults(newsByPage).list();
    }

    /**
     * Returns collection of approved or not approved news by id organization
     *
     * @param idOrg organization
     * @param approved of administrator
     * @return collection of news
     */
    @Override
    public Collection<News> getNewsByOrg(Integer idOrg, Boolean approved) {
        return getSession().getNamedQuery("News.getByOrganization")
                .setParameter(APPROVED, approved).setParameter("id", idOrg).list();
    }

    /**
     * Returns count of news for organization's id
     *
     * @param author author of organization
     * @param approved of administrator
     * @return count of news for organization's id
     */
    @Override
    public Integer getCountByOrgAuthor(String author, Boolean approved) {
        return ((Long) getSession().createQuery("Select Count(*) From News news Where news.orgApproved = :approved and news.baseOrg.author = :author and news.comment is null")
                .setParameter(AUTHOR, author).setParameter(APPROVED, approved).uniqueResult()).intValue();
    }

    /**
     * Returns collection of news on page for organization's author
     *
     * @param author author of organization
     * @param approved for administrator
     * @param pageNumb number of pages
     * @param newsByPage news's count for 1 page
     * @return collection of news per page
     */
    @Override
    public Collection<News> getPagesNewsByOrgAuthor(String author, Boolean approved, Integer pageNumb, Integer newsByPage) {
        int firstResult = (pageNumb - 1) * newsByPage;
        return getSession().getNamedQuery("News.getByOrganization")
                .setParameter(AUTHOR, author).setParameter(APPROVED, approved).setFirstResult(firstResult).setMaxResults(newsByPage).list();
    }
}
