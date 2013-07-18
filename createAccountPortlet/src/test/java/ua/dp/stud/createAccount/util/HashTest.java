package ua.dp.stud.createAccount.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Author: Pikus V.V.
 * Date: 25.03.13
 */
public class HashTest {
    private Hash instance;
    private String URL = "http://stud.dp.ua/registration?id=10&mail=user@gmail.com";

    @Before
    public void setUp() {
        this.instance = new Hash();
    }

    @Test
    public void getCryptTest() {
        String result = instance.getCrypt(this.URL);
        assertNotNull(result);
    }

    @Test
    public void getDecryptTest() {
        String result = instance.getCrypt(this.URL);
        String expResult = instance.getDecrypt(result);
        assertEquals(expResult, this.URL);
    }
}
