
package ua.dp.stud.StudPortalLib.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ольга
 */

public enum EventsType {
  SPORTS, MUSIC, VACANCY, CONFERENCE, WEBINAR, THEATRE;

    public static List<String> allTypes() {
        List<String> result = new ArrayList<String>();
        for (EventsType t : EventsType.values()) {
            result.add(t.toString());
        }
        return result;
    }
   
}
