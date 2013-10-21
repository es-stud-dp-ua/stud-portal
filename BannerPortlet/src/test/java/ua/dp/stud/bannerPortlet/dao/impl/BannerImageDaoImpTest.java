package ua.dp.stud.bannerPortlet.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
import ua.dp.stud.bannerPortlet.model.BannerImage;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Vladislav Pikus
 */
@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class BannerImageDaoImpTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    @Qualifier(value = "bannerDao")
    private BannerImageDao bannerDao;

    public void setDao(BannerImageDao bannerDao) {
        this.bannerDao = bannerDao;
    }

    private BannerImage banner1;
    private BannerImage banner2;

    @Before
    @Rollback(false)
    public void setUp() {
        banner1 = new BannerImage("http://vk.com");
        banner2 = new BannerImage("http://stud.dp.ua");
        bannerDao.save(banner1);
        bannerDao.save(banner2);
    }

    @Test
    public void getAllTest() {
        Collection<BannerImage> bannerImageCollection = bannerDao.getAll();
        assertEquals(2, bannerImageCollection.size());
    }

    @Test
    public void updateBannerImageTest() {
        banner1.setUrl("http://google.com/");
        bannerDao.update(banner1);
    }

    @Test
    public void getByURLTest() {
        BannerImage banner = bannerDao.getByURL("http://vk.com");
        assertEquals(banner, banner1);
    }

    @Test
    public void getBannerImageByIdTest() {
        BannerImage banner = bannerDao.getById(banner2.getId());
        assertEquals(banner, banner2);
    }

    @After
    @Rollback(false)
    public void clearTestDB() {
        bannerDao.delete(banner1.getId());
        bannerDao.delete(banner2.getId());
    }
}
