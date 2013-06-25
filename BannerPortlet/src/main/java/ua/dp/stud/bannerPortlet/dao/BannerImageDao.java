package ua.dp.stud.bannerPortlet.dao;
import ua.dp.stud.bannerPortlet.model.BannerImage;

import java.util.Collection;
/**
 * @author Vladislav Pikus
 */
public interface BannerImageDao
{
    BannerImage getBannerImageById(Integer id);

    void addBannerImage(BannerImage banner);

    void deleteBannerImage(Integer id);

    void updateBannerImage(BannerImage banner);

    Collection<BannerImage> getAll();
}
