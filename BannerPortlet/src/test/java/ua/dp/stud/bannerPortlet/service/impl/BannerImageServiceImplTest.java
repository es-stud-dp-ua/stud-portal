package ua.dp.stud.bannerPortlet.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;
import ua.dp.stud.bannerPortlet.service.BannerImageService;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author Vladislav Pikus
 */
@RunWith(MockitoJUnitRunner.class)
public class BannerImageServiceImplTest {
    private BannerImageService service;
    private BannerImageDao mockDao;
    private BannerImage banner1;
    private BannerImage banner2;

    @Before
    public void init() {
        service = new BannerImageServiceImpl();
        mockDao = mock(BannerImageDao.class);
        ((BannerImageServiceImpl) service).setDao(mockDao);

        banner1 = new BannerImage("http://stud.dp.ua/");
        banner1.setId(1);
        banner2 = new BannerImage("http://vk.com/");
        banner2.setId(2);
    }

    @Test
    public void getBannerImageByIdTest() {
        when(mockDao.getById(1)).thenReturn(banner1);
        assertEquals(banner1, service.getBannerImageById(1));
        assertNull(service.getBannerImageById(10));
    }

    @Test
    public void getAllTest() {
        Collection<BannerImage> bannerImageCollection = Arrays.asList(banner1, banner2);
        when(mockDao.getAll()).thenReturn(bannerImageCollection);
        assertEquals(bannerImageCollection, service.getAll());
    }

    @Test
    public void getByURLTest() {
        when(mockDao.getByURL("http://vk.com/")).thenReturn(banner2);
        assertEquals(banner2, service.getByURL("http://vk.com/"));
        assertNull(service.getByURL("http://stud.dp.ua/"));
    }

    @Test
    public void addBannerImageTest() {
        service.addBannerImage(banner1);
        verify(mockDao).save(banner1);
    }

    @Test
    public void deleteBannerImageTest() {
        service.deleteBannerImage(banner1);
        verify(mockDao).delete(banner1.getId());
    }

    @Test
    public void updateBannerImageTest() {
        service.updateBannerImage(banner1);
        verify(mockDao).update(banner1);
    }
}
