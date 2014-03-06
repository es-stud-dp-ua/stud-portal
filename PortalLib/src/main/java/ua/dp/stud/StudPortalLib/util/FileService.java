package ua.dp.stud.StudPortalLib.util;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ua.dp.stud.StudPortalLib.model.FileSaver;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util.Calendar;
import java.util.List;

import static org.apache.commons.lang.StringUtils.join;

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
    static final String FILE_URL_PREFIX = "/mediastuff/";

    static{
        TOMCAT_FOLDER = System.getProperty("catalina.base");
        DATA_FOLDER = "PROJECT_DATA";
        FILE_FOLDER = "Files";
        FOLDER_SEPARATOR = File.separator;
        FILE_FOLDER_ABS_PATH = TOMCAT_FOLDER
                + FOLDER_SEPARATOR
                + DATA_FOLDER
                + FOLDER_SEPARATOR
                + FILE_FOLDER ;

    }

    /**
     * generates absolute path to files folder like: /home/tomcat/PROJECT_DATA/Files
     *
     * @return abs path to files
     */
    public String getFileFolderAbsolutePath() {
        return FILE_FOLDER_ABS_PATH;
    }


   /** add to absolute path a path to file*/
    public String generatePathToFile(EntityWithFile entity){
        List<String> entityPath=entity.getEntityNameForPath();
        String path=FILE_FOLDER_ABS_PATH;
        for (String s:entityPath){
          path+=FOLDER_SEPARATOR+s;
        }
        return path;
    }


    /**
     * Deletes directory with subdirs and subfolders
     *
     * @param dir Directory to delete
     */
    private void deleteDirectory(File dir) {
        if (dir.isDirectory()){
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++){
                File f = new File(dir, children[i]);
                this.deleteDirectory(f);
            }
            dir.delete();
        }
    }

    public FileSaver uploadFile(FileSaver file, CommonsMultipartFile fileSrc,EntityWithFile entity) throws IOException {
       checkPathToFileFolder( file, entity);
        StringBuilder pathToFileFolder =new StringBuilder( this.generatePathToFile(entity));
        pathToFileFolder.append(FOLDER_SEPARATOR);
        boolean filecreate = false;
        String createdFilePath=null;
        File file1 = new File(pathToFileFolder+fileSrc.getOriginalFilename().replaceAll(" ","_"));
        try {
            FileOutputStream out = new FileOutputStream(file1);
            createdFilePath = writeFile(out,file1,fileSrc);
            if(createdFilePath != null){
                filecreate = true;
            }
         } catch (FileNotFoundException e) {

        }

         file=new FileSaver(file1.getName())  ;
       return  file;
   }

    private String writeFile(FileOutputStream out, File file, CommonsMultipartFile fileSrc){
        String filePath = null;
        try{
            out.write(fileSrc.getBytes());
            out.close();
            filePath=file.getAbsolutePath();

        }catch (IOException e) { }
        return filePath;
    }

    public String downloadFile(FileSaver file, EntityWithFile entity) throws IOException {
        StringBuilder path = new StringBuilder(FILE_URL_PREFIX);
        path.append(FOLDER_SEPARATOR);
        path.append( FILE_FOLDER );
        for (String s: entity.getEntityNameForPath())   {
                path.append(FOLDER_SEPARATOR).append(s);
            }
        path.append(FOLDER_SEPARATOR).append(file.getOriginalFileName());
        return path.toString();
    }
   /**creates directories if it is absent
    * @param entity - it gives the second part of the path to file*/
   private void checkPathToFileFolder(FileSaver base,EntityWithFile entity) {
        StringBuilder path=new StringBuilder();
        path.append(generatePathToFile(entity));
        path.append(FOLDER_SEPARATOR);
       if (base != null){
         path.append(base.getOriginalFileName());
       }
        File file=new File(path.toString());
     //  file.delete();

       if (!(file.exists())){
            file.mkdirs();
       }

    }

    private void deleteFile(FileSaver file, EntityWithFile entityWithFile) {
        StringBuilder filePath = new StringBuilder();
        filePath.append(generatePathToFile(entityWithFile));
        File fileToDelete = new File(filePath.append(file.getOriginalFileName()).toString());
        fileToDelete.delete();
        File dir = new File(filePath.toString());
        if (dir.list().length == 0) {
            dir.delete();
        }
    }

}
