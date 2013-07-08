/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Ольга
 */
@Entity
@Table(name = "studies_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Studie extends BaseImagesSupport implements Serializable {

    private String title;
    private String text;
    private String adress;
    private String facultetDnevn;
    private String facultetZaoch;


    private static final int TEXT_LENGTH = 10000;

    public Studie() {
    }

    public Studie(String topic, String text, String facultetDnevn, String facultetZaoch, String adress) {
        this.title = topic;
        this.text = text;
        this.facultetDnevn = facultetDnevn;
        this.facultetZaoch = facultetZaoch;
        this.adress = adress;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "text", length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "adress")
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    //todo: facepalm
    @Column(name = "facultetDnevn")
    public String getFacultetsDnev() {
        return facultetDnevn;
    }

    @Column(name = "facultetZaoch")
    public String getFacultetsZaoch() {
        return facultetZaoch;
    }

    public void setFacultetsDnev(String facultetDnevn) {
        this.facultetDnevn = facultetDnevn;
    }

    public void setFacultetsZaoch(String facultetZaoch) {
        this.facultetZaoch = facultetZaoch;
    }

    private static final int START_HASH = 55;
    private static final int MULT_HASH = 77;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(START_HASH, MULT_HASH).append(this.title).append(this.text).append(this.adress)
                .append(this.facultetDnevn).append(this.facultetZaoch).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Studie)) {
            return false;
        }
        final Studie other = (Studie) obj;
        return new EqualsBuilder().append(other.adress, adress).append(other.text, text).append(other.title, title)
                .append(other.facultetZaoch, facultetZaoch).append(other.facultetDnevn, facultetDnevn).isEquals();
    }

    @Override
    public String toString() {
        return new StringBuffer().append("Studie[").append("adress=").append(adress)
                .append(", text=").append(text).append(", title=").append(title).append(']').toString();
    }
}
