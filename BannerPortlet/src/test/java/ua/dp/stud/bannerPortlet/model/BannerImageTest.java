package ua.dp.stud.bannerPortlet.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * @author Vladislav Pikus
 * Date: 15.03.13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class BannerImageTest
{
    private BannerImage banner;

    @Before
    public void setUp()
    {
        this.banner = new BannerImage();
    }

    @Test
    public void getUrlTest()
    {
        String expResult = null;
        String result = this.banner.getUrl();
        assertEquals(expResult, result);
    }

    @Test
    public void setUrlTest()
    {
        String url = "http://vk.com";
        banner.setUrl(url);
    }

    @Test
    public void toStringTest()
    {
        banner = new BannerImage("http://vk.com");
        String expResult = "BannerImage{url=http://vk.com}";
        String result = banner.toString();
        assertEquals(expResult, result);
    }
}
