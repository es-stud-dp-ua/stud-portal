package ua.dp.stud.bannerPortlet.service.impl;

/**
 * @author Vladislav Pikus
 */

import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;
import ua.dp.stud.bannerPortlet.service.BannerImageService;

import java.util.Collection;

public class BannerImageServiceImpl implements BannerImageService {

    private BannerImageDao dao;

    public void setDao(BannerImageDao dao)
    {
        this.dao = dao;
    }

    @Override
    public BannerImage getBannerImageById(Integer id)
    {
        BannerImage banner = dao.getBannerImageById(id);
        //todo: wtf?
        if (banner != null)
        {
            return banner;
        }
        return null;
    }

    @Override
    public void addBannerImage(BannerImage banner)
    {
        dao.addBannerImage(banner);
    }

    @Override
    public void deleteBannerImage(BannerImage banner)
    {
        dao.deleteBannerImage(banner.getId());
    }

    @Override
    public void updateBannerImage(BannerImage banner)
    {
        dao.updateBannerImage(banner);
    }

    @Override
    public Collection<BannerImage> getAll()
    {
        return dao.getAll();
    }

}
