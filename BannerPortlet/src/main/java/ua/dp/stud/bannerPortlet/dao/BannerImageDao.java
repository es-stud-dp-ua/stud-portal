package ua.dp.stud.bannerPortlet.dao;

import ua.dp.stud.StudPortalLib.dao.BaseDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;

import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
public interface BannerImageDao extends BaseDao<BannerImage> {
    BannerImage getByURL(String url);
}
