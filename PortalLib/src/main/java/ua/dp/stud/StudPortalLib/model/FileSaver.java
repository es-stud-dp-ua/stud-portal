package ua.dp.stud.StudPortalLib.model;


import javax.persistence.*;
import java.io.Serializable;

/**@author Nazarenko Kostya
 * helper entity for holding files
 */
@Entity
public class FileSaver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String originalFileName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileSaver)) return false;

        FileSaver fileSaver = (FileSaver) o;

        if (!id.equals(fileSaver.id)) return false;
        if (originalFileName != null ? !originalFileName.equals(fileSaver.originalFileName) : fileSaver.originalFileName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (originalFileName != null ? originalFileName.hashCode() : 0);
        return result;
    }
}
