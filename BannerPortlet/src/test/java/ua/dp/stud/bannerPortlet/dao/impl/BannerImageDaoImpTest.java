/*package ua.dp.stud.bannerPortlet.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.junit.Assert.assertFalse;*/

/**
 * Created with IntelliJ IDEA.
 * @author Vladislav Pikus
 * Date: 15.03.13
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
/*@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class BannerImageDaoImpTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private BannerImageDao bannerDao;

    private BannerImage banner1;
    private BannerImage banner2;

    @Before
    @Rollback(false)
    public  void setUp()
    {
        banner1 = new BannerImage("http://vk.com");
        banner2 = new BannerImage("http://stud.dp.ua");
        bannerDao.addBannerImage(banner1);
        bannerDao.addBannerImage(banner2);
    }

    @Test
    public void getAllTest()
    {
        Collection<BannerImage> bannerImageCollection = bannerDao.getAll();
        assertEquals(2, bannerImageCollection.size());
    }

    @After
    @Rollback(false)
    public void clearTestDB() {
        bannerDao.deleteBannerImage(banner1.getId());
        bannerDao.deleteBannerImage(banner2.getId());
    }
}*/
