package ua.dp.stud.StudPortalLib.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Roman Lukash
 */
public class NewsTest {
    public NewsTest() {
    }

    private News instance;

    @Before
    public void setUp() {
        instance = new News();
    }

    @Test
    public void getNumberOfViewsTest() {
        Integer result = 0;
        assertEquals(result, instance.getNumberOfViews());
    }

    @Test
    public void getOrgApprovedTest() {
        assertNull(instance.getOrgApproved());
    }

    @Test
    public void setOrgApprovedTest() {
        instance.setOrgApproved(true);
        assertTrue(instance.getOrgApproved());
    }

    /**
     * Test of getId method, of class News.
     */
    @Test
    public void testGetId() {
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class News.
     */
    @Test
    public void testSetId() {
        Integer id = null;
        instance.setId(id);
    }

    /**
     * Test of getTopic method, of class News.
     */
    @Test
    public void testGetTopic() {
        String expResult = null;
        String result = instance.getTopic();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTopic method, of class News.
     */
    @Test
    public void testSetTopic() {
        String topic = "";
        instance.setTopic(topic);
    }

    /**
     * Test of getText method, of class News.
     */
    @Test
    public void testGetText() {
        String expResult = null;
        String result = instance.getText();
        assertEquals(expResult, result);
    }

    /**
     * Test of setText method, of class News.
     */
    @Test
    public void testSetText() {
        String text = "";
        instance.setText(text);
    }

    /**
     * Test of getAuthor method, of class News.
     */
    @Test
    public void testGetAuthor() {
        String expResult = null;
        String result = instance.getAuthor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAuthor method, of class News.
     */
    @Test
    public void testSetAuthor() {
        String author = "";
        instance.setAuthor(author);
    }

    /**
     * Test of getPublication method, of class News.
     */
    @Test
    public void testGetPublication() {
        Date expResult = null;
        Date result = instance.getPublication();
        assertEquals(expResult, result);
    }


    /**
     * Test of setPublication method, of class News.
     */
    @Test
    public void testSetPublication() {
        Date date = null;
        instance.setPublication(date);
    }

    /**
     * Test of getInCalendar method, of class News.
     */
    @Test
    public void testGetInCalendar() {
        boolean expResult = false;
        boolean result = instance.getInCalendar();
        assertEquals(expResult, result);
    }

    /**
     * Test of setInCalendar method, of class News.
     */
    @Test
    public void testSetInCalendar() {
        boolean inCalendar = false;
        instance.setInCalendar(inCalendar);
    }

    /**
     * Test of getApproved method, of class News.
     */
    @Test
    public void testGetApproved() {
        Boolean expResult = true;
        Boolean result = instance.getApproved();
        assertEquals(expResult, result);
    }

    /**
     * Test of setApproved method, of class News.
     */
    @Test
    public void testSetApproved() {
        Boolean approved = true;
        instance.setApproved(approved);
    }

    /**
     * Test of getOnMainpage method, of class News.
     */
    @Test
    public void testGetOnMainpage() {
        boolean expResult = false;
        boolean result = instance.getOnMainpage();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOnMainpage method, of class News.
     */
    @Test
    public void testSetOnMainpage() {
        boolean onMainpage = false;
        instance.setOnMainpage(onMainpage);
    }

    @Test
    public void testHashCode() {
        Integer result = instance.hashCode();
        assertNotNull(result);
    }

    @Test
    public void testToString() {
        assertNotNull(instance.toString());
    }
}
