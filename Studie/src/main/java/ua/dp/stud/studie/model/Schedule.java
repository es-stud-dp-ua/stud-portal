package ua.dp.stud.studie.model;

import ua.dp.stud.StudPortalLib.model.FileSaver;
import ua.dp.stud.StudPortalLib.util.EntityWithFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "schedule_table")
public class Schedule implements Serializable, EntityWithFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculties faculty;
    @Column
    private Course year;
    @Column
    private FileSaver scheduleFile;
    @Column
    private Date lastUpdateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Faculties getFaculty()
    {
        return faculty;
    }
    public void setFaculty(Faculties faculty)
    {
        this.faculty=faculty;
    }

    public Course getYear() {
        return year;
    }

    public void setYear(Course year) {
        this.year = year;
    }

    public FileSaver getScheduleFile() {
        return scheduleFile;
    }

    public void setScheduleFile(FileSaver scheduleFile) {
        this.scheduleFile = scheduleFile;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Schedule()
    {
        this.faculty=null;
        this.year= Course.FIRST;
        this.scheduleFile =null;
        this.lastUpdateDate=new Date();
    }
   public Schedule(Faculties faculty, Course year, FileSaver scheduleFileFolder, Date lastUpdateDate)
    {
        this.faculty=faculty;
        this.year=year;
        this.scheduleFile =scheduleFileFolder;
        this.lastUpdateDate=lastUpdateDate;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (faculty != null ? !faculty.equals(schedule.faculty) : schedule.faculty != null) return false;
        if (id != null ? !id.equals(schedule.id) : schedule.id != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(schedule.lastUpdateDate) : schedule.lastUpdateDate != null)
            return false;
        if (scheduleFile != null ? !scheduleFile.equals(schedule.scheduleFile) : schedule.scheduleFile != null)
            return false;
        if (year != schedule.year) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (faculty != null ? faculty.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (scheduleFile != null ? scheduleFile.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }

    @Override
    public List<String> getEntityNameForPath() {
        List <String> path=new ArrayList<String>();
        path.add("SCHEDULE");
        path.add(this.faculty.getBase().getId().toString());
        path.add(this.faculty.getId().toString());
        path.add(this.year.toString());

        return  path;
    }

    @Override
    public boolean setFile(FileSaver file) {

        this.scheduleFile=file;
        return true;
    }
}
