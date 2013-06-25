
package ua.dp.stud.StudPortalLib.model;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Roman Lukash
 */
public class CategoryTest {
    
    public CategoryTest() {
    }
    private Category instance;
    @Before
    public void setUp() {
        instance = new Category();
    }

    /**
     * Test of getId method, of class Category.
     */
    @Test
    public void testGetId() {
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }
    /**
     * Test of setId method, of class Category.
     */
    @Test
    public void testSetId() {
        Integer id = 1;
        instance.setId(id);
    }

    /**
     * Test of getCategoryName method, of class Category.
     */
    @Test
    public void testGetCategoryName() {
        String expResult = null;
        String result = instance.getCategoryName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCategoryName method, of class Category.
     */
    @Test
    public void testSetCategoryName() {  
        String categoryName = "Sport";
        instance.setCategoryName(categoryName);
    }

    /**
     * Test of getNews method, of class Category.
     */

    @Test
    public void testGetNews() {
         List expResult = null;
        List result = instance.getNews();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setNews method, of class Category.
     */

    @Test
    public void testSetNews() {
        List<News> newsList = null;
        instance.setNews(newsList);
    }
	
	@Test
	public void testToString()
	{
		String expResult = "Category{id=null, categoryName=null}";
		String result = instance.toString();
		assertEquals(expResult, result);
	}
	
	@Test
	public void testHashCode(){
		Integer result = instance.hashCode();
		assertNotNull(result);
	}
	
	@Test
	public void testEquals()
	{
		Object obj = null;
		assertFalse(instance.equals(obj));
		
		obj = new Integer(1);
		assertFalse(instance.equals(obj));
		
		obj = new Category();
		((Category)obj).setId(12);
		assertFalse(instance.equals(obj));
		
		obj = new Category();
		((Category)obj).setCategoryName("Name");
		assertFalse(instance.equals(obj));
		
		obj = new Category();
		assertTrue(instance.equals(obj));
	}
}
