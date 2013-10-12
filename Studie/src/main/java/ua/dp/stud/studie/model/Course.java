package ua.dp.stud.studie.model;

import org.hibernate.validator.constraints.NotEmpty;
import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.awt.Image;
/**
 * Author: Nazarenko K.V
 * Date: 03.10.13
 *
 */
enum CoursesType {Company,Tutor,Online}

@Entity
@Table(name = "course_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Course extends BaseImagesSupport{


    @Column
    @NotEmpty
    private String courseName;

    @Column
    @NotEmpty
    private String authorslogin;

    @Column
    private String coursesContact;

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

    public String getCoursesContact() {
        return coursesContact;
    }

    public void setCoursesContact(String coursesContact) {
        this.coursesContact = coursesContact;
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

    public Course(String courseName, String authorslogin, String coursesContact, String coursesDescription, KindOfCourse kindOfCourse, CoursesType coursesType) {
        this.courseName = courseName;
        this.authorslogin = authorslogin;
        this.coursesContact = coursesContact;
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
        if (!super.equals(o)) return false;

        Course course = (Course) o;

        if (addDate != null ? !addDate.equals(course.addDate) : course.addDate != null) return false;
        if (addState != null ? !addState.equals(course.addState) : course.addState != null) return false;
        if (authorslogin != null ? !authorslogin.equals(course.authorslogin) : course.authorslogin != null)
            return false;
        if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) return false;
        if (coursesContact != null ? !coursesContact.equals(course.coursesContact) : course.coursesContact != null)
            return false;
        if (coursesDescription != null ? !coursesDescription.equals(course.coursesDescription) : course.coursesDescription != null)
            return false;
        if (coursesType != course.coursesType) return false;
        if (kindOfCourse != null ? !kindOfCourse.equals(course.kindOfCourse) : course.kindOfCourse != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (authorslogin != null ? authorslogin.hashCode() : 0);
        result = 31 * result + (coursesContact != null ? coursesContact.hashCode() : 0);
        result = 31 * result + (coursesDescription != null ? coursesDescription.hashCode() : 0);
        result = 31 * result + (addDate != null ? addDate.hashCode() : 0);
        result = 31 * result + (kindOfCourse != null ? kindOfCourse.hashCode() : 0);
        result = 31 * result + (addState != null ? addState.hashCode() : 0);
        result = 31 * result + (coursesType != null ? coursesType.hashCode() : 0);
        return result;
    }
}




