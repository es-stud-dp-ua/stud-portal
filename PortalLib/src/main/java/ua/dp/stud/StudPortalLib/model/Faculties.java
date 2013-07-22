package ua.dp.stud.StudPortalLib.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ольга
 */
@Entity
@Table(name = "faculties")
public class Faculties implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Faculties() {
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nameOfFaculties", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Specialties> special;

    public List<Specialties> getSpecialties() {
        return special;
    }

    public void setSpecialties(List<Specialties> specialties) {
        this.special = specialties;
    }

    @Column(name = "nameOfFaculties")
    private String nameOfFaculties;

    public void setNameOfFaculties(String nameOfFaculties) {
        this.nameOfFaculties = nameOfFaculties;
    }

    public String getNameOfFaculties() {
        return nameOfFaculties;
    }

    @ManyToOne
    @JoinColumn(name = "base")
    private Studie base;


    public void setBase(Studie base) {
        this.base = base;
    }

    public Studie getBase() {
        return base;
    }

}
