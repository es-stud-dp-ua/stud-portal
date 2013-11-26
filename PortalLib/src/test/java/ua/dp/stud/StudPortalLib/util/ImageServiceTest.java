package ua.dp.stud.StudPortalLib.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceTest {

    private ImageService service;
    private BaseImagesSupport base;
    private ImageImpl img;
    private CommonsMultipartFile file;

    @Before
    public void setUp() {
        service = new ImageService();
        base = mock(BaseImagesSupport.class);
        img = mock(ImageImpl.class);
        file = mock(CommonsMultipartFile.class);
        when(img.getOriginalImageName()).thenReturn("image.jpg");
    }

    @Test
    public void testGetImagesFolderAbsolutePath() {
        String expResult = System.getProperty("catalina.base") + File.separator + "PROJECT_DATA" + File.separator;
        String result = service.getImagesFolderAbsolutePath();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPathToLargeImageEmpty() {
        String value = null;
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        assertNull(service.getPathToLargeImage(img, base));
        verify(base).getYearMonthUniqueFolder();
    }

    @Test
    public void testGetPathToLargeImage() {
        String value = "2012/11/12";
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        String expResult = "/mediastuff/2012/11/12/image.jpg";
        String result = service.getPathToLargeImage(img, base);
        assertEquals(expResult, result);
        verify(base).getYearMonthUniqueFolder();
    }

    @Test
    public void testGetPathToSmallImageEmpty() {
        String value = null;
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        assertNull(service.getPathToSmallImage(img, base));
        verify(base).getYearMonthUniqueFolder();
    }

    @Test
    public void testGetPathToSmallImage() {
        String value = "2012/11/12";
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        String expResult = "/mediastuff/2012/11/12/small_image.jpg";
        String result = service.getPathToSmallImage(img, base);
        assertEquals(expResult, result);
        verify(base).getYearMonthUniqueFolder();
    }

    @Test
    public void testGetPathToMicroblogImageEmpty() {
        String value = null;
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        assertNull(service.getPathToMicroblogImage(img, base));
        verify(base).getYearMonthUniqueFolder();
    }

    @Test
    public void testGetPathToMicroblogImage() {
        String value = "2012/11/12";
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        String expResult = "/mediastuff/2012/11/12/microblog_image.jpg";
        String result = service.getPathToMicroblogImage(img, base);
        assertEquals(expResult, result);
        verify(base).getYearMonthUniqueFolder();
    }

    @Test
    public void testGetPathToCalendarImageEmpty() {
        String value = null;
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        assertNull(service.getPathToCalendarImage(img, base));
        verify(base).getYearMonthUniqueFolder();
    }

    @Test
    public void testGetPathToCalendarImage() {
        String value = "2012/11/12";
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        String expResult = "/mediastuff/2012/11/12/toCalendar_image.jpg";
        String result = service.getPathToCalendarImage(img, base);
        assertEquals(expResult, result);
        verify(base).getYearMonthUniqueFolder();
    }
/*
    @Test
    public void testSaveMainImageEmpty() {
        String value = null;
        when(file.getSize()).thenReturn(0l);
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        try {
            service.saveMainImage(file, base);
            assertTrue(true);
            verify(file).getSize();
            verify(base).getYearMonthUniqueFolder();
        } catch (IOException ex) {
            assertTrue(false);
        }
    }

    @Test
    public void testSaveAdditionalImagesEmpty() {
        String value = "2012/11/12";
        when(file.getSize()).thenReturn(0l);
        when(base.getYearMonthUniqueFolder()).thenReturn(value);
        try {
            service.saveAdditionalImages(file, base);
            assertTrue(true);
            verify(file).getSize();
        } catch (IOException ex) {
            assertTrue(false);
        }
    }    *//*@Test
	public void testSaveMainImage() throws IOException
	{
		MockMultipartFile mockedFile = new MockMultipartFile("image", "myImage.jpg", "image/jpeg", "123".getBytes());
		when(file.getSize()).thenReturn(mockedFile.getSize());
		when(file.getInputStream()).thenReturn(mockedFile.getInputStream());
		when(file.getOriginalFilename()).thenReturn(mockedFile.getOriginalFilename());
		when(base.getYearMonthUniqueFolder()).thenReturn("2012/11/12");
		try
		{
			service.saveMainImage(file, base);
			assertTrue(false);
		}
		catch(IOException ex)	
		{
			assertTrue(true);
			verify(file).getSize();
		}
	}*/
}