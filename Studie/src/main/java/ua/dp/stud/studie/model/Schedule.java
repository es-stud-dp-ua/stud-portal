package ua.dp.stud.studie.model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "schedule_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Schedule implements Serializable  {
    private Integer id;
    private String university;
    private String faculty;
    private String year;
    private String scheduleFileFolder;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return  id;
    }
    public void setId(Integer id)
    {
        this.id= id;
    }

    @Column(name="university")
    public String getUniversity()
    {
        return university;
    }
    public void setUniversity(String university)
    {
        this.university = university;
    }

    @Column(name="faculty")
    public String getFaculty()
    {
        return faculty;
    }
    public void setFaculty(String faculty)
    {
        this.faculty=faculty;
    }

    @Column(name ="year")
    public String getYear()
    {
        return year;
    }
    public  void setYear(String year)
    {
        this.year=year;
    }

    public String getScheduleFileFolder() {
        return scheduleFileFolder;
    }

    public void setScheduleFileFolder(String scheduleFileFolder) {
        this.scheduleFileFolder = scheduleFileFolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (faculty != null ? !faculty.equals(schedule.faculty) : schedule.faculty != null) return false;
        if (id != null ? !id.equals(schedule.id) : schedule.id != null) return false;
        if (university != null ? !university.equals(schedule.university) : schedule.university != null) return false;
        if (year != null ? !year.equals(schedule.year) : schedule.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (university != null ? university.hashCode() : 0);
        result = 31 * result + (faculty != null ? faculty.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }
}
