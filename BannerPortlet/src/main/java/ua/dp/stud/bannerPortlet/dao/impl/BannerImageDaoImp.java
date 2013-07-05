package ua.dp.stud.bannerPortlet.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;

import java.util.Collection;

/**
 * Class is responsible for working with the database
 *
 * @author Vladislav Pikus
 */
@Repository("bannerDao")
public class BannerImageDaoImp implements BannerImageDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public BannerImage getBannerImageById(Integer id) {
        return (BannerImage) getSession().get(BannerImage.class, id);
    }

    @Override
    public void addBannerImage(BannerImage banner) {
        getSession().save(banner);
    }

    @Override
    public void deleteBannerImage(Integer id) {
        BannerImage banner = (BannerImage) getSession().get(BannerImage.class, id);
        ImageImpl image = banner.getMainImage();
        if (image != null)
        {
            getSession().delete(image);
        }
        getSession().delete(banner);
    }

    @Override
    public void updateBannerImage(BannerImage banner) {
        getSession().update(banner);
    }

    @Override
    public Collection<BannerImage> getAll() {
        return getSession().createCriteria(BannerImage.class).list();
    }

    @Override
    public BannerImage getByURL(String url)
    {
        return (BannerImage) getSession().createCriteria(BannerImage.class).add(Restrictions.eq("url", url)).uniqueResult();
    }
}
