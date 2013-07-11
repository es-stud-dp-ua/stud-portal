package ua.dp.stud.StudPortalLib.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vladislav Pikus
 */
public class ImageImplTest {
    public ImageImplTest() {
    }

    private ImageImpl instance;

    @Before
    public void setUp() {
        instance = new ImageImpl();
    }

    @Test
    public void testGetId() {
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetId() {
        Long id = 1l;
        instance.setId(id);
        assertEquals(id, instance.getId());
    }

    @Test
    public void testGetOriginalImageName() {
        String expResult = null;
        String result = instance.getOriginalImageName();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetOriginalImageName() {
        String name = "New name";
        instance.setOriginalImageName(name);
        assertEquals(name, instance.getOriginalImageName());
    }

    @Test
    public void testGetBase() {
        BaseImagesSupport expResult = null;
        BaseImagesSupport result = instance.getBase();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetBase() {
        BaseImagesSupport newBase = new BaseImagesSupport();
        instance.setBase(newBase);
        assertEquals(newBase, instance.getBase());
    }

    @Test
    public void testToString() {
        String expResult = "ImageImpl[originalImageName=null, id=null]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}