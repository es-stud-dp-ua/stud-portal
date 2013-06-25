package ua.dp.stud.bannerPortlet.model;
/**
 * @author Roman Lukash
 * @author Vladislav Pikus
 */

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="banner_images")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class BannerImage extends BaseImagesSupport implements Serializable
{
    private String url;

    public BannerImage()
    {
    }

    public BannerImage(String url)
    {
        this.url = url;
    }

    @Column(name="url")
    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public String toString() {
        //todo: use stringBuilder
        return "BannerImage{" + "url=" + this.url + '}';
    }
}
