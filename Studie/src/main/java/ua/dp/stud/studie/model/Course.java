package ua.dp.stud.studie.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Nazarenko K.V
 * Date: 03.10.13
 */

@Entity
@Table(name = "course_table")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String user;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String url;

    @Column
    private String photoUrl;

    @Column
    private String info;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private String kind;

    @Column
    private Boolean state;

    @Column
    private String type;

    public Course() {
    }

    public Course(String name, String user, String address, String phone, String url, String photoUrl, String info, String kind, String type) {
        this.name = name;
        this.user = user;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.photoUrl = photoUrl;
        this.info = info;
        this.date = new Date();
        this.kind = kind;
        this.state = false;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (address != null ? !address.equals(course.address) : course.address != null) return false;
        if (date != null ? !date.equals(course.date) : course.date != null) return false;
        if (!id.equals(course.id)) return false;
        if (info != null ? !info.equals(course.info) : course.info != null) return false;
        if (kind != null ? !kind.equals(course.kind) : course.kind != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (phone != null ? !phone.equals(course.phone) : course.phone != null) return false;
        if (photoUrl != null ? !photoUrl.equals(course.photoUrl) : course.photoUrl != null) return false;
        if (state != null ? !state.equals(course.state) : course.state != null) return false;
        if (type != null ? !type.equals(course.type) : course.type != null) return false;
        if (url != null ? !url.equals(course.url) : course.url != null) return false;
        if (user != null ? !user.equals(course.user) : course.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (kind != null ? kind.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", url='" + url + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", info='" + info + '\'' +
                ", date=" + date +
                ", kind='" + kind + '\'' +
                ", state=" + state +
                ", type='" + type + '\'' +
                '}';
    }
}
