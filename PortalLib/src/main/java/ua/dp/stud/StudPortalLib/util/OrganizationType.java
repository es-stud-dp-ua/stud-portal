package ua.dp.stud.StudPortalLib.util;

import java.util.ArrayList;
import java.util.List;

public enum OrganizationType
{
    SPORTS, YOUNGSTERS, VOLUNTEERING, CHARITY, INT_ORGS, ART, OTHERS;
    public static List<String> allTypes(){
        List<String> result=new ArrayList<String>() ;
        for (OrganizationType t:OrganizationType.values())
        {
            result.add(t.toString());
        }
        return result;
    }
}




