package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;

import java.util.Collection;

/**
 * Class is used to work with the database
 *
 * @author Roman Lukash
 * @author Vladislav Pikus
 */
@Repository("newsDao")
public class NewsDaoImpl extends BaseDao implements NewsDao
{
    /**
     * Method add news to database
     *
     * @param news The news we want to add to the database
     * @return The news added to the database
     */
    @Override
    public News addNews(News news) {
        getSession().save(news);
        return news;
    }

    /**
     * Method return news form database by id
     *
     * @param id -ID of news that we want to get
     * @return The news which ID = id;
     */
    @Override
    public News getNewsById(Integer id)
    {
        return (News) getSession().get(News.class, id);
    }

    /**
     * Method update news by id
     *
     * @param news -news with new parameters
     */
    @Override
    public void updateNews(News news) {
        getSession().update(news);
    }

    /**
     * deleteNews
     *
     * @param id of news
     */

    @Override
    public void deleteNews(Integer id)
    {
        News news = (News) getSession().get(News.class, id);
        ImageImpl image = news.getMainImage();
        getSession().delete(image);
        getSession().delete(news);
    }

    /**
     * getAllNews
     * @return Collection of News
     */
    @Override
    public Collection<News> getAllNews()
    {
        return getSession().createCriteria(News.class).addOrder(Order.desc("publication")).list();
    }

    /**
     * getCount
     *
     * @return coutn of news
     */
    @Override
    public Integer getCount()
    {
        return ((Long) getSession().createQuery("Select Count(*) From News").uniqueResult()).intValue();
    }

    /**
     * add image
     * @param image for adding
     */
    @Override
    public void addImage(ImageImpl image) {
        getSession().save(image);
    }

    /**
     * addCategory
     *
     * @param cat
     * @return adding Category
     */
    @Override
    public Category addCategory(Category cat) {
        getSession().save(cat);
        return cat;
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
        return (Collection<News>)getSession().createQuery("From News a ORDER BY a.id desc")
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
     * @return collection categories
     */
    @Override
    public Collection<Category> getAllCategories() {
        return getSession().createCriteria(Category.class).addOrder(Order.asc("categoryName")).list();
    }

    /**
     * Delete image by id
     * @param id of image for delete
     */
	@Override
	public void deleteImage(Long id)
	{   //todo: find better approach
		ImageImpl image = getImageById(id);
        image.getBase().getAdditionalImages().remove(image);
		image.setBase(null);
		getSession().delete(image);
	}

    /**
     * Returns image by id
     * @param id image
     * @return image that is equals id
     */
	@Override
    public ImageImpl getImageById(Long id)
	{
        return (ImageImpl) getSession().get(ImageImpl.class, id);
    }

    /**
     * Returns collection of news by author
     * @param author of news
     * @return collection of news
     */
    @Override
    public Collection<News> getAllNewsByAuthor(String author)
    {
        return getSession().createQuery("Select news From News news  Where news.author = :author ORDER BY news.id desc")
                .setParameter("author", author).list();
    }

    /**
     * Returns count of news for author
     * @param author of news
     * @return count of news by author
     */
    @Override
    public Integer getCountByAuthor(String author)
    {
        return ((Long) getSession().createQuery("Select Count(*) From News news  Where news.author = :author")
                .setParameter("author", author).uniqueResult()).intValue();
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
        int firstResult = (pageNumb - 1) * newsByPage;
        return getSession().createQuery("Select news From News news  Where news.author = :author ORDER BY news.id desc")
                .setParameter("author", author).setFirstResult(firstResult).setMaxResults(newsByPage).list();
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
        return getSession().createQuery("Select news From News news  Where news.baseOrg.id = :id and news.orgApproved = :approved ORDER BY news.id desc")
                .setParameter("approved", approved).setParameter("id", IdOrg).list();
    }

    /**
     * Returns count of news for organization's id
     * @param author author of organization
     * @param approved of administrator
     * @return count of news for organization's id
     */
    @Override
    public Integer getCountByOrgAuthor(String author, Boolean approved)
    {
        return ((Long) getSession().createQuery("Select Count(*) From News news Where news.orgApproved = :approved and news.baseOrg.author = :author and news.comment is null")
                .setParameter("author", author).setParameter("approved", approved).uniqueResult()).intValue();
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
        int firstResult = (pageNumb - 1) * newsByPage;
        return getSession().createQuery("Select news From News news  Where news.orgApproved = :approved and news.baseOrg.author = :author and news.comment is null ORDER BY news.id desc")
                .setParameter("author", author).setParameter("approved", approved).setFirstResult(firstResult).setMaxResults(newsByPage).list();
    }

    /**
     * Returns all news by approved
     * @param approved of administrator
     * @return collection of news
     */
    @Override
    public Collection<News> getAllNews(Boolean approved)
    {
        return getSession().createQuery("Select news From News news  Where news.approved = :approved and news.comment is null ORDER BY news.publication desc")
                .setParameter("approved", approved).list();
    }

    /**
     * Returns count of news by approved
     * @param approved of administrator
     * @return returns count of approved news
     */
    @Override
    public Integer getCount(Boolean approved)
    {
        return ((Long) getSession().createQuery("Select Count(*) From News news  Where news.approved = :approved and news.comment is null")
                .setParameter("approved", approved).uniqueResult()).intValue();
    }

    /**
     * Returns a collection of news per page for a set number
     * @param approved of administrator
     * @param pageNumb page number
     * @param newsByPage count news by page
     * @return collection for page number
     */
    @Override
    public Collection<News> getNewsOnPage(Boolean approved, Integer pageNumb, Integer newsByPage)
    {
        int firstResult = (pageNumb - 1) * newsByPage;
        return getSession().createQuery("Select news From News news  Where news.approved = :approved and news.comment is null ORDER BY news.publication desc")
                .setParameter("approved", approved).setFirstResult(firstResult).setMaxResults(newsByPage).list();
    }
}
    