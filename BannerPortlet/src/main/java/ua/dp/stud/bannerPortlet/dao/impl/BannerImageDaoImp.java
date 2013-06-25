package ua.dp.stud.bannerPortlet.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

import java.util.Collection;

/**  Class is responsible for working with the database
 * @author Vladislav Pikus
 */

public class BannerImageDaoImp extends HibernateDaoSupport implements BannerImageDao
{
    @Override
    public BannerImage getBannerImageById(Integer id)
    {
        return (BannerImage) getSession().get(BannerImage.class, id);
    }

    @Override
    public void addBannerImage(BannerImage banner)
    {
        getSession().save(banner);
    }

    @Override
    public void deleteBannerImage(Integer id)
    {
        //todo: replace getBannerImageById call by getSession().load(id)
        BannerImage banner = this.getBannerImageById(id);
        ImageImpl image = banner.getMainImage();
        getSession().delete(image);
        getSession().delete(banner);
    }

    @Override
    public void updateBannerImage(BannerImage banner)
    {
        getSession().update(banner);
    }

    @Override
    public Collection<BannerImage> getAll()
    {
        return getSession().createCriteria(BannerImage.class).list();
    }
}
