package ua.dp.stud.studie.model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Lysenko Nikolai
 */
public class CouncilTest {

    private Council instance;

    @Before
    public void SetUp()
    {
        instance=new Council();
    }

    
    @Test
    public void TestGetCouncilName()
    {
        String expResult=null;
        String result=instance.getCouncilName();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetCouncilName()
    {
        String councilName="";
        instance.setCouncilName(councilName);
    }
    
    @Test
    public void TestGetCouncilDescription()
    {
        String expResult=null;
        String result=instance.getCouncilDescription();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetCouncilDescription()
    {
        String councilDescription="";
        instance.setCouncilDescription(councilDescription);
    }

    @Test
    public void TestGetCouncilContact()
    {
        String expResult=null;
        String result=instance.getCouncilContact();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetCouncilContact()
    {
        String councilContact="";
        instance.setCouncilContact(councilContact);
    }

    
    
}
