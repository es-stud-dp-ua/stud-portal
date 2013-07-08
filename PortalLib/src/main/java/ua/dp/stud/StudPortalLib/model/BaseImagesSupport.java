package ua.dp.stud.StudPortalLib.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FK_Main_Img")
    public ImageImpl getMainImage() {
        return mainImage;
    }

    public void setMainImage(ImageImpl mainImage) {
        this.mainImage = mainImage;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "base", orphanRemoval = true)
    public List<ImageImpl> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<ImageImpl> additionalImages) {
        this.additionalImages = additionalImages;
    }

    private static final int START_HASH = 17;
    private static final int MULT_HASH = 37;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(START_HASH, MULT_HASH).append(this.id).append(this.yearMonthUniqueFolder).append(this.mainImage)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BaseImagesSupport)) {
            return false;
        }
        final BaseImagesSupport other = (BaseImagesSupport) obj;
        return new EqualsBuilder().append(other.id, id).append(other.yearMonthUniqueFolder, yearMonthUniqueFolder)
                .append(other.mainImage, mainImage).append(other.additionalImages, additionalImages).isEquals();
    }

    @Override
    public String toString() {
        return new StringBuffer().append("BaseImagesSupport[").append("id=").append(id).append(", yearMonthUniqueFolder=")
                .append(yearMonthUniqueFolder).append(']').toString();
    }
}
