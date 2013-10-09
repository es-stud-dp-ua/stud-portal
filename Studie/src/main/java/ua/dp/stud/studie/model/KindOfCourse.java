package ua.dp.stud.studie.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 *Author: Nazarenko Alexandra
 * Date: 04.10.13
 */

@Entity
@Table (name="kindofcourses_table")
public class KindOfCourse {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer typeId;

@Column
@NotEmpty
private String kindOfCourse;

    public KindOfCourse(String kindOfCourse)
    {
        this.kindOfCourse = kindOfCourse;
    }

    public KindOfCourse()
    {}

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


