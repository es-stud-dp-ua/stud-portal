package ua.dp.stud.bannerPortlet.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.impl.BaseDaoImpl;
import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;

/**
 * Class is responsible for working with the database
 *
 * @author Vladislav Pikus
 */
@Repository("bannerDao")
public class BannerImageDaoImp extends BaseDaoImpl<BannerImage> implements BannerImageDao {

    public BannerImage getByURL(String url) {
        return (BannerImage) getSession().createCriteria(BannerImage.class).add(Restrictions.eq("url", url)).uniqueResult();
    }
}
