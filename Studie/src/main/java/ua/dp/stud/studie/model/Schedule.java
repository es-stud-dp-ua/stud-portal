package ua.dp.stud.studie.model;

import ua.dp.stud.StudPortalLib.model.FileSaver;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "schedule_table")
public class Schedule implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @Column
    private String university;

    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculties faculty;
    @Column
    private String year;
    @Column
    private FileSaver scheduleFile;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversity()
    {
        return university;
    }
    public void setUniversity(String university)
    {
        this.university = university;
    }
    public Faculties getFaculty()
    {
        return faculty;
    }
    public void setFaculty(Faculties faculty)
    {
        this.faculty=faculty;
    }
    public String getYear()
    {
        return year;
    }
    public  void setYear(String year)
    {
        this.year=year;
    }

    public FileSaver getScheduleFile() {
        return scheduleFile;
    }

    public void setScheduleFile(FileSaver scheduleFile) {
        this.scheduleFile = scheduleFile;
    }
    public Schedule()
    {
        this.university=null;
        this.faculty=null;
        this.year=null;
        this.scheduleFile =null;
    }
   public Schedule(String university, Faculties faculty, String year, FileSaver scheduleFileFolder)
    {
        this.university=university;
        this.faculty=faculty;
        this.year=year;
        this.scheduleFile =scheduleFileFolder;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (faculty != null ? !faculty.equals(schedule.faculty) : schedule.faculty != null) return false;
        if (id != null ? !id.equals(schedule.id) : schedule.id != null) return false;
        if (scheduleFile != null ? !scheduleFile.equals(schedule.scheduleFile) : schedule.scheduleFile != null)
            return false;
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
        result = 31 * result + (scheduleFile != null ? scheduleFile.hashCode() : 0);
        return result;
    }
}
