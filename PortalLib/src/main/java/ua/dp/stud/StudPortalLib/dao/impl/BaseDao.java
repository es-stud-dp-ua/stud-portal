package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import ua.dp.stud.StudPortalLib.model.News;

/**
 * @author: Pikus Vladislav
 */
public abstract class BaseDao
{
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    protected Session getSession()
    {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    public int calcPages(Integer count, Integer perPage)
    {
        return (count > 0) ? ((count - 1) / perPage + 1) : 0;
    }
}
