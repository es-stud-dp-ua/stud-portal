package ua.dp.stud.bannerPortlet.service.impl;

/**
 * @author Vladislav Pikus
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;
import ua.dp.stud.bannerPortlet.service.BannerImageService;

import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
@Service("bannerImageService")
public class BannerImageServiceImpl implements BannerImageService {

    private BannerImageDao dao;
    //todo: remove all @Override
    @Transactional(readOnly = false)
    @Autowired
    @Qualifier(value = "bannerDao")
    public void setDao(BannerImageDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public BannerImage getBannerImageById(Integer id) {
        return dao.getById(id);
    }

    @Transactional(readOnly = false)
    public void addBannerImage(BannerImage banner) {
        dao.save(banner);
    }

    @Transactional(readOnly = false)
    public void deleteBannerImage(BannerImage banner) {
        dao.delete(banner.getId());
    }

    @Transactional(readOnly = false)
    public void updateBannerImage(BannerImage banner) {
        dao.update(banner);
    }

    @Transactional(readOnly = true)
    public Collection<BannerImage> getAll() {
        return dao.getAll();
    }

    @Transactional(readOnly = true)
    public BannerImage getByURL(String url) {
        return dao.getByURL(url);
    }
}
