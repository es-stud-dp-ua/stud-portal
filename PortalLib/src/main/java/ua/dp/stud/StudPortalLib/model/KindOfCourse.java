package ua.dp.stud.StudPortalLib.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *@author: Nazarenko Alexandra
 * Date: 04.10.13
 */

@Entity
@DiscriminatorValue(value="kindOfCourse")
public class KindOfCourse extends CommonType implements Serializable {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kindOfCourse")
    private List<Course> course;

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

   /* @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "typeId", unique = true, nullable = false)
    private Integer typeId;

    @Column
    @NotEmpty
    private String kindOfCourse;  */



    public KindOfCourse(String kindOfCourse)
    {
        super(kindOfCourse);
    }

    public KindOfCourse()
    {}

   /* public Integer getTypeId() {
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
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KindOfCourse)) return false;
        if (!super.equals(o)) return false;

        KindOfCourse that = (KindOfCourse) o;

        if (course != null ? !course.equals(that.course) : that.course != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }


/*    @Override
    public String toString() {
        return kindOfCourse ;
    }*/
}