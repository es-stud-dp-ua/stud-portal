package ua.dp.stud.aboutTeam.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dp.stud.aboutTeam.dao.HumanDao;
import ua.dp.stud.aboutTeam.model.Human;

import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
@Repository("humanDao")
public class HumanDaoImpl implements HumanDao {
    //todo: remove all @Override
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Human getHumanById(Integer id) {
        return (Human) getSession().get(Human.class, id);
    }

    @Override
    public void addHuman(Human human) {
        getSession().save(human);
    }

    @Override
    public void deleteHuman(Integer id) {
        Human human = (Human) getSession().get(Human.class, id);
        getSession().delete(human);
    }

    @Override
    public void updateHuman(Human human) {
        getSession().update(human);
    }

    @Override
    //todo: use List<Human>
    public Collection<Human> getAll() {
        return getSession().createCriteria(Human.class).list();
    }

    @Override
    public Human getByUrl(String url) {
        return (Human) getSession().createCriteria(Human.class).add(Restrictions.eq("url", url)).uniqueResult();
    }
}
