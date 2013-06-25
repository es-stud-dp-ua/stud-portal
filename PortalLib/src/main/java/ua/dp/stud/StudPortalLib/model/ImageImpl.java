package ua.dp.stud.StudPortalLib.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * helper entity for holding images
 */
@Entity
public class ImageImpl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String originalImageName;

    @ManyToOne
    @JoinColumn(name = "FK_Base")
    private BaseImagesSupport base;

    public ImageImpl() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalImageName() {
        return originalImageName;
    }

    public void setOriginalImageName(String originalImageName) {
        this.originalImageName = originalImageName;
    }

    public BaseImagesSupport getBase() {
        return base;
    }

    public void setBase(BaseImagesSupport base) {
        this.base = base;
    }



    @Override
    public String toString() {
        return "ImageImpl{" +
                "originalImageName='" + originalImageName + '\'' +
                ", id=" + id +
                '}';
    }
}
