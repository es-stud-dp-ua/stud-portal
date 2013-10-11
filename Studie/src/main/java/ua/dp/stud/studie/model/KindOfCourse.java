package ua.dp.stud.studie.model;

import org.hibernate.validator.constraints.NotEmpty;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import java.util.List;

/**
 *@author: Nazarenko Alexandra
 * Date: 04.10.13
 */

@Entity
@Table(name="kindofcourses_table")
public class KindOfCourse {

@OneToMany(fetch = FetchType.LAZY, mappedBy = "kindOfCourse")
private List<Course> course;

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

@Id
@GeneratedValue(strategy = IDENTITY)
@Column(name = "typeId", unique = true, nullable = false)
private Integer typeId;

@Column
@NotEmpty
private String kindOfCourse;


    public KindOfCourse(String kindOfCourse)
    {
        this.kindOfCourse = kindOfCourse;
    }

    public String getKindOfCourse() {
        return this.kindOfCourse;
    }

    public KindOfCourse()
    {}




    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getKindOfCourse() {
        return kindOfCourse;
    }

    public void setKindOfCourse(String kindOfCourse) {
        this.kindOfCourse = kindOfCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KindOfCourse that = (KindOfCourse) o;

        if (kindOfCourse != null ? !kindOfCourse.equals(that.kindOfCourse) : that.kindOfCourse != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId != null ? typeId.hashCode() : 0;
        result = 31 * result + (kindOfCourse != null ? kindOfCourse.hashCode() : 0);
        return result;
    }
}


