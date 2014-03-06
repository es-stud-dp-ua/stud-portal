package ua.dp.stud.StudPortalLib.model;

import javax.persistence.*;

@Entity
@Table(name = "common_type")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="commonType")
public class CommonType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "typeId", unique = true, nullable = false)
    private Long id;

    @Column
    private String kindOfCourse;

    @Transient
    private Long countOfCourses;

    public Long getId() {
        return id;
    }

    public void setId(Long typeId) {
        this.id = typeId;
    }

    public String getKindOfCourse() {
        return kindOfCourse;
    }

    public void setKindOfCourse(String kindOfCourse) {
        this.kindOfCourse = kindOfCourse;
    }

    public Long getCountOfCourses() {
        return countOfCourses;
    }

    public void setCountOfCourses(Long countOfCourses) {
        this.countOfCourses = countOfCourses;
    }

    public CommonType(String kindOfCourse) {
        this.kindOfCourse = kindOfCourse;
    }

    public CommonType() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof CommonType)){
            return false;
        }

        CommonType that = (CommonType) o;

        if (countOfCourses != null ? !countOfCourses.equals(that.countOfCourses) : that.countOfCourses != null){
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null){
            return false;
        }
        if (kindOfCourse != null ? !kindOfCourse.equals(that.kindOfCourse) : that.kindOfCourse != null){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kindOfCourse != null ? kindOfCourse.hashCode() : 0);
        result = 31 * result + (countOfCourses != null ? countOfCourses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return kindOfCourse;
    }
}
