package ua.dp.stud.StudPortalLib.util;


import ua.dp.stud.StudPortalLib.model.FileSaver;

import java.util.List;

public interface EntityWithFile {


        List<String> getEntityNameForPath();
        boolean setFile (FileSaver file) ;

}
