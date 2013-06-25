//package ua.dp.stud.bannerPortlet.dao.impl;

//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import ua.dp.stud.bannerPortlet.dao.BannerImageDao;
//import ua.dp.stud.bannerPortlet.model.BannerImage;

//import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * @author Vladislav Pikus
 * Date: 15.03.13
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
//@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
//@TransactionConfiguration(defaultRollback = true)
//@RunWith(SpringJUnit4ClassRunner.class)
//public class BannerImageDaoImpTest
//{
//    @Autowired
//    @Qualifier(value = "bannerDao")
//    private BannerImageDao dao;
//    private static BannerImage banner1;
//    private static BannerImage banner2;
//
//    @Before
//    @Rollback(false)
//    public  void setUp()
//    {
//        banner1 = new BannerImage("http://vk.com");
//        banner2 = new BannerImage("http://stud.dp.ua");
//        dao.addBannerImage(banner1);
//        dao.addBannerImage(banner2);
//    }
//
//    @Test
//    public void addBannerImageTest()
//    {
//        //assertTrue(dao.getBannerImageById(banner1.getId()).getId() == banner1.getId());
//        assertEquals(dao.getBannerImageById(banner1.getId()), banner1);
//    }
//
//    @Test
//    public void updateBannerImage()
//    {
//        BannerImage newBanner = new BannerImage("http://google.com");
//        //dao.updateBannerImage(newBanner);
//        //assertEquals(dao.getBannerImageById(newBanner.getId()), newBanner);
//    }

//    @After
//    @Rollback(false)
//    public void clearTestDB() {
//        dao.deleteBannerImage(banner1.getId());
//        dao.deleteBannerImage(banner2.getId());
//    }
//}
