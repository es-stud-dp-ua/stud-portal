package ua.dp.stud.StudPortalLib.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Author: Nazarenko K.V
 * Date: 03.10.13
 *
 */

@Entity
@Table(name = "course_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Course extends BaseImagesSupport{

    @Column
    private String courseName;

    @Column
    private String author;

    @Column
    private String coursesContact;

    @Column
    private String coursesDescription;

    @Column
    private String comment;

    @Column
    @Temporal(TemporalType.DATE)
    private Date addDate;

    @Column
    private KindOfCourse kindOfCourse;

    @Column
    private Boolean approved;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private  CoursesType coursesType;

    public CoursesType getCoursesType() {
        return coursesType;
    }

    public Course()
    {
        this.addDate=new Date();
        this.approved = false;
    }
    public Course(String coursesName){
    	this.courseName = coursesName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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


    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }


    public void setCoursesType(CoursesType coursesType) {
        this.coursesType = coursesType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="kind_of_course_id", nullable = true)
    public KindOfCourse getKindOfCourse() {
        return kindOfCourse;
    }

    public void setKindOfCourse(KindOfCourse kindOfCourse) {
        this.kindOfCourse = kindOfCourse;
    }

    public Course(String courseName, String author, String coursesContact, String coursesDescription, KindOfCourse kindOfCourse, CoursesType coursesType, String comment) {
        this.courseName = courseName;
        this.author = author;
        this.coursesContact = coursesContact;
        this.approved=false;
        this.coursesDescription = coursesDescription;
        this.addDate = new Date();
        this.kindOfCourse = kindOfCourse;
        this.coursesType = coursesType;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        	}
        if (o == null || getClass() != o.getClass()) {
        	return false;
        	}
        if (!super.equals(o)){
        	return false;
        	}

        Course course = (Course) o;

        if (addDate != null ? !addDate.equals(course.addDate) : course.addDate != null){
        	return false;
        	}
        if (approved != null ? !approved.equals(course.approved) : course.approved != null){
        	return false;
        	}
        if (author != null ? !author.equals(course.author) : course.author != null){
            return false;
            }
        if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) {
        	return false;
        	}
        if (coursesContact != null ? !coursesContact.equals(course.coursesContact) : course.coursesContact != null){
            return false;
            }
        if (coursesDescription != null ? !coursesDescription.equals(course.coursesDescription) : course.coursesDescription != null){
            return false;
            }
        if (coursesType != course.coursesType){ 
        	return false;
        	}
        if (kindOfCourse != null ? !kindOfCourse.equals(course.kindOfCourse) : course.kindOfCourse != null){
            return false;
            }
        if (comment != null ? !comment.equals(course.comment) : course.comment != null){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (coursesContact != null ? coursesContact.hashCode() : 0);
        result = 31 * result + (coursesDescription != null ? coursesDescription.hashCode() : 0);
        result = 31 * result + (addDate != null ? addDate.hashCode() : 0);
        result = 31 * result + (kindOfCourse != null ? kindOfCourse.hashCode() : 0);
        result = 31 * result + (approved != null ? approved.hashCode() : 0);
        result = 31 * result + (coursesType != null ? coursesType.hashCode() : 0);
        result = 31 * result + (comment != null ? coursesType.hashCode() : 0);
        return result;
    }
}