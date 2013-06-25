package ua.dp.stud.StudPortalLib.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseImagesSupport implements Serializable {

    private Integer id;
    private ImageImpl mainImage;
    private List<ImageImpl> additionalImages;
    private String yearMonthUniqueFolder;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getYearMonthUniqueFolder() {
        return yearMonthUniqueFolder;
    }

    public void setYearMonthUniqueFolder(String yearMonthUniqueFolder) {
        this.yearMonthUniqueFolder = yearMonthUniqueFolder;
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "FK_Main_Img")
    public ImageImpl getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageImpl mainImage) {
        this.mainImage = mainImage;
    }
    //todo: use laze fetch type
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "base", orphanRemoval=true)
    public List<ImageImpl> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<ImageImpl> additionalImages) {
        this.additionalImages = additionalImages;
    }

    //todo: alwaus override hashcode AND equals
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
