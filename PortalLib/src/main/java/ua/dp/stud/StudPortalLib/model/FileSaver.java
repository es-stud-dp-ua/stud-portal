package ua.dp.stud.StudPortalLib.model;


import javax.persistence.*;
import java.io.Serializable;

/**@author Nazarenko Kostya
 * helper entity for holding files
 */
@Entity
public class FileSaver implements Serializable {


    private Long id;
    @Column
    private String originalFileName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Override
    public String toString() {
        return "FileSaver{" +
                "id=" + id +
                ", originalFileName='" + originalFileName + '\'' +
                '}';
    }

   public FileSaver(String originalFileName )
    {
        this.originalFileName = originalFileName;
    }

    public FileSaver(){}
}


