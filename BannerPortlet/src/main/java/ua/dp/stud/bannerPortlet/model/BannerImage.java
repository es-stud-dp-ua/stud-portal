package ua.dp.stud.bannerPortlet.model;
/**
 * @author Roman Lukash
 * @author Vladislav Pikus
 */

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "banner_images")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class BannerImage extends BaseImagesSupport implements Serializable {
    private String url;

    public BannerImage() {
    }

    public BannerImage(String url) {
        this.url = url;
    }

    @Column(name = "url")
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(55, 257).append(this.url).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BannerImage))
            return false;
        final BannerImage other = (BannerImage) obj;
        return new EqualsBuilder().append(other.url, url).isEquals();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("BannerImage[").append("url=").append(url).append(']').toString();
    }
}
