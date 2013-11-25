package ua.dp.stud.StudPortalLib.util;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import ua.dp.stud.StudPortalLib.model.FileSaver;

import java.io.File;
import java.io.IOException;

/**@author
 * Nazarenko Kostya, Nazarenko Alexandra
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

    public void uploadFile(FileSaver file, String path) throws IOException {
        String pathToFileFolder = checkPathToFileFolder(file);
        /*if (file.getSize() != 0) {
            saveToDiskScaled(file, pathToImagesFolder, "", IMAGE_MAX_WIDTH, -1);
            saveToDiskScaled(file, pathToImagesFolder, CALENDAR_IMAGE_PREFIX, TO_CALENDAR_WIDTH, TO_CALENDAR_HEIGHT);
            saveTo DiskScaled(file, pathToImagesFolder, MICROBLOG_IMAGE_PREFIX, MICROBLOG_IMAGE_WIDTH, MICROBLOG_IMAGE_HEIGHT);

            ImageImpl image = new ImageImpl();
            //image.setBase(base);
            image.setOriginalImageName(file.getOriginalFilename());
            base.setMainImage(image);
        }  */
        File fileSrc = new File(path);

        File fileDest = new File(pathToFileFolder+fileSrc.getName());
        fileDest.createNewFile();
        FileUtils.copyFile(fileSrc,fileDest);
        file.setOriginalFileName(fileDest.getAbsolutePath());

            //file.setOriginalFileName();


    }

    public boolean downloadFile(FileSaver file, String path) throws IOException {
        if (file.getOriginalFileName()==null)
        return false;
        File fileSrc = new File(path);
        File fileDest = new File(file.getOriginalFileName());
        fileSrc.createNewFile();
        FileUtils.copyFile(fileDest,fileSrc);
        return true;
    }

    private String checkPathToFileFolder(FileSaver base) {
        String path;
        if (base.getOriginalFileName()!= null)
        {
            File file1=new File(base.getOriginalFileName());
            deleteDirectory(new File(file1.getPath().substring(0,file1.getPath().lastIndexOf(File.separator))));
        }
        DateTime dt = new DateTime();
        StringBuilder sb = new StringBuilder();
        String uniqueFolderId = String.valueOf(System.currentTimeMillis());

        String yearMonthUniqueFolder = sb.append(dt.yearOfEra().getAsString())
                    .append(FOLDER_SEPARATOR)
                    .append(dt.monthOfYear().getAsString())
                    .append(FOLDER_SEPARATOR)
                    .append(uniqueFolderId).toString();
        base.setOriginalFileName(yearMonthUniqueFolder);

        sb = new StringBuilder();
        sb.append(getFileFolderAbsolutePath())
                 .append(yearMonthUniqueFolder);
        path = sb.toString();
        File folderForFile = new File(path);
        folderForFile.mkdirs();

        return path;
    }

    private void deleteFile(FileSaver file) {
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
