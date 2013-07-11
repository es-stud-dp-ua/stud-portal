package ua.dp.stud.bannerPortlet.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Vladislav Pikus
 */
public class BannerImageTest {
    private BannerImage banner;

    @Before
    public void setUp() {
        this.banner = new BannerImage();
    }

    @Test
    public void getUrlTest() {
        String expResult = null;
        String result = this.banner.getUrl();
        assertEquals(expResult, result);
    }

    @Test
    public void setUrlTest() {
        String url = "http://vk.com";
        banner.setUrl(url);
    }

    @Test
    public void toStringTest() {
        banner = new BannerImage("http://vk.com");
        String expResult = "BannerImage[url=http://vk.com]";
        String result = banner.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(banner.hashCode());
    }

    @Test
    public void equalsTest() {
        assertFalse(banner.equals(null));
        BannerImage banner1 = banner;
        assertTrue(banner.equals(banner1));
        Integer numb = 4;
        assertFalse(banner.equals(numb));
        banner1 = new BannerImage();
        assertTrue(banner.equals(banner1));
        banner1 = new BannerImage("http://stud.dp.ua/");
        assertFalse(banner.equals(banner1));
    }
}
