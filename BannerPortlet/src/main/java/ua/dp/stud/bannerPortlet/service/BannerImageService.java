package ua.dp.stud.bannerPortlet.service;

import ua.dp.stud.bannerPortlet.model.BannerImage;
import ua.dp.stud.StudPortalLib.dto.CommonDto;

import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
public interface BannerImageService {
    BannerImage getBannerImageById(Integer id);

    void addBannerImage(BannerImage banner);

    void deleteBannerImage(BannerImage banner);

    void updateBannerImage(BannerImage banner);

    Collection<BannerImage> getAll();

    BannerImage getByURL(String url);

    Collection<CommonDto> getAllDTO(Collection<BannerImage> images);

	Long countBannerImage();

}