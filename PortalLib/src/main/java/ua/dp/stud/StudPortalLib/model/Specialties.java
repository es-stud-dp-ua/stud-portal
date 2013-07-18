package ua.dp.stud.StudPortalLib.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Ольга
 */
@Entity
@Table(name = "specialties")
public class Specialties implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nameOfFaculties")
    private Faculties nameOfFaculties;
    @Column(name = "nameOfSpecialties")
    private String nameOfSpecialties;

   public void setNameOfFaculties(Faculties nameOfFaculties) {
        this.nameOfFaculties = nameOfFaculties;
    }

    public Faculties getNameOfFaculties() {
        return nameOfFaculties;
    }

    public void setNameOfSpecialties(String nameOfSpecialties) {
        this.nameOfSpecialties = nameOfSpecialties;
    }

    public String getNameOfSpecialties() {
        return nameOfSpecialties;
    }
}
