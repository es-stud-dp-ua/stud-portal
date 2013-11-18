package ua.dp.stud.StudPortalLib.util;

import org.springframework.stereotype.Component;
import ua.dp.stud.StudPortalLib.model.FileSaver;

import java.io.File;
import java.io.IOException;

/**@author
 * Nazarenko Kostya
 */

@Component("fileService")
public class FileService {

    private static final String FILE_FOLDER_ABS_PATH;
    private static final String TOMCAT_FOLDER;
    private static final String DATA_FOLDER;
    private static final String FOLDER_SEPARATOR;
    private static final String FILE_FOLDER;

    static
    {
        TOMCAT_FOLDER = System.getProperty("catalina.base");
        DATA_FOLDER = "PROJECT_DATA";
        FILE_FOLDER = "Files";
        FOLDER_SEPARATOR = File.separator;
        FILE_FOLDER_ABS_PATH = TOMCAT_FOLDER
                + FOLDER_SEPARATOR
                + DATA_FOLDER
                + FOLDER_SEPARATOR
                + FILE_FOLDER
                + FOLDER_SEPARATOR;
    }

    /**
     * generates absolute path to files folder like: /home/tomcat/PROJECT_DATA/Files
     *
     * @return abs path to files
     */
    public String getFileFolderAbsolutePath() {
        return FILE_FOLDER_ABS_PATH;
    }

    /**
     * Deletes directory with subdirs and subfolders
     *
     * @param dir Directory to delete
     */
    private void deleteDirectory(File dir) {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                File f = new File(dir, children[i]);
                this.deleteDirectory(f);
            }
            dir.delete();
        }
    }

    public void uploadFile(FileSaver file) throws IOException {

    }

    public void downloadFile(FileSaver file) throws IOException {

    }

    public void deleteFile(FileSaver file) {
        StringBuilder filePath = new StringBuilder();
        filePath.append(this.getFileFolderAbsolutePath());
        File fileToDelete = new File(filePath.append(file.getOriginalFileName()).toString());
        fileToDelete.delete();
        File dir = new File(filePath.toString());
        if (dir.list().length == 0) {
            dir.delete();
        }
    }

}
