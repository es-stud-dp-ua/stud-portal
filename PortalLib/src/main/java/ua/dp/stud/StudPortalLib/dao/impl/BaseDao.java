package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Pikus Vladislav
 */
public abstract class BaseDao
{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession()
    {
        return this.sessionFactory.getCurrentSession();
    }

    public Integer calcPages(Integer count, Integer perPage)
    {
        return (count > 0) ? ((count - 1) / perPage + 1) : 0;
    }
}
