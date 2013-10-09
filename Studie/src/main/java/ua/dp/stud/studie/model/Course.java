package ua.dp.stud.studie.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.awt.Image;
//import ua.dp.stud.StudPortalLib.model.ImageImpl;
//import ua.dp.stud.StudPortalLib.util.ImageService;
/**
 * Author: Nazarenko K.V
 * Date: 03.10.13
 *
 */
enum CoursesType {Company,Tutor,Online}

@Entity
@Table(name = "course_table")
public class Course{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotEmpty
    private String courseName;

    @Column
    @NotEmpty
    private String authorslogin;

    @Column
    private String courseAdress;

    @Column
    private String coursesPhoneNum;

    @Column
    private String courcesUrl;

    @Column
    private Image photoUrl;
    //private ImageImpl photoUrl;

    @Column
    private String coursesDescription;

    @Column
    @Temporal(TemporalType.DATE)
    private Date addDate;

    @Column
    @NotEmpty
    @ManyToOne
    private KindOfCourse kindOfCourse;

    @Column
    private Boolean addState;

    @Column
    private  CoursesType coursesType;

    public Course()
    {
        this.addDate=new Date();
        this.addState = false;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAuthorslogin() {
        return authorslogin;
    }

    public void setAuthorslogin(String authorslogin) {
        this.authorslogin = authorslogin;
    }

    public String getCourseAdress() {
        return courseAdress;
    }

    public void setCourseAdress(String courseAdress) {
        this.courseAdress = courseAdress;
    }

    public String getCoursesPhoneNum() {
        return coursesPhoneNum;
    }

    public void setCoursesPhoneNum(String coursesPhoneNum) {
        this.coursesPhoneNum = coursesPhoneNum;
    }

    public String getCourcesUrl() {
        return courcesUrl;
    }

    public void setCourcesUrl(String courcesUrl) {
        this.courcesUrl = courcesUrl;
    }

    public Image getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Image photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCoursesDescription() {
        return coursesDescription;
    }

    public void setCoursesDescription(String coursesDescription) {
        this.coursesDescription = coursesDescription;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }


    public Boolean getAddState() {
        return addState;
    }

    public void setAddState(Boolean addState) {
        this.addState = addState;
    }



    public CoursesType getCoursesType() {
        return coursesType;
    }

    public void setCoursesType(CoursesType coursesType) {
        this.coursesType = coursesType;
    }

    public KindOfCourse getKindOfCourse() {
        return kindOfCourse;
    }

    public void setKindOfCourse(KindOfCourse kindOfCourse) {
        this.kindOfCourse = kindOfCourse;
    }

    public Course(String courseName, String authorslogin, String courseAdress, String coursesPhoneNum, String courcesUrl, Image photoUrl, String coursesDescription, KindOfCourse kindOfCourse, CoursesType coursesType) {
        this.courseName = courseName;
        this.authorslogin = authorslogin;
        this.courseAdress = courseAdress;
        this.coursesPhoneNum = coursesPhoneNum;
        this.courcesUrl = courcesUrl;
        this.photoUrl = photoUrl;
        this.addState=false;
        this.coursesDescription = coursesDescription;
        this.addDate = new Date();
        this.kindOfCourse = kindOfCourse;
        this.coursesType = coursesType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (addDate != null ? !addDate.equals(course.addDate) : course.addDate != null) return false;
        if (addState != null ? !addState.equals(course.addState) : course.addState != null) return false;
        if (authorslogin != null ? !authorslogin.equals(course.authorslogin) : course.authorslogin != null)
            return false;
        if (courcesUrl != null ? !courcesUrl.equals(course.courcesUrl) : course.courcesUrl != null) return false;
        if (courseAdress != null ? !courseAdress.equals(course.courseAdress) : course.courseAdress != null)
            return false;
        if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) return false;
        if (coursesDescription != null ? !coursesDescription.equals(course.coursesDescription) : course.coursesDescription != null)
            return false;
        if (coursesPhoneNum != null ? !coursesPhoneNum.equals(course.coursesPhoneNum) : course.coursesPhoneNum != null)
            return false;
        if (coursesType != course.coursesType) return false;
        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (kindOfCourse != null ? !kindOfCourse.equals(course.kindOfCourse) : course.kindOfCourse != null)
            return false;
        if (photoUrl != null ? !photoUrl.equals(course.photoUrl) : course.photoUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (authorslogin != null ? authorslogin.hashCode() : 0);
        result = 31 * result + (courseAdress != null ? courseAdress.hashCode() : 0);
        result = 31 * result + (coursesPhoneNum != null ? coursesPhoneNum.hashCode() : 0);
        result = 31 * result + (courcesUrl != null ? courcesUrl.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (coursesDescription != null ? coursesDescription.hashCode() : 0);
        result = 31 * result + (addDate != null ? addDate.hashCode() : 0);
        result = 31 * result + (kindOfCourse != null ? kindOfCourse.hashCode() : 0);
        result = 31 * result + (addState != null ? addState.hashCode() : 0);
        result = 31 * result + (coursesType != null ? coursesType.hashCode() : 0);
        return result;
    }
}



