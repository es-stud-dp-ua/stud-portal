package ua.dp.stud.StudPortalLib.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
    public String toString()
    {
        return new StringBuffer().append("ImageImpl[").append("originalImageName=").append(originalImageName)
                .append(", id=").append(id).append(']').toString();
    }

    @Override
    public int hashCode()
    {
        return  new HashCodeBuilder(17, 97).append(this.id).append(this.base).append(this.originalImageName)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ImageImpl))
            return false;
        final ImageImpl other = (ImageImpl) obj;
        return new EqualsBuilder().append(other.id, id).append(other.base, base)
                .append(other.originalImageName, originalImageName).isEquals();
    }
}
