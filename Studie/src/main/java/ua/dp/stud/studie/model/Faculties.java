package ua.dp.stud.studie.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(min = 2, max = 100)
    @NotNull
    @NotBlank
    private String nameOfFaculties;

    public void setNameOfFaculties(String nameOfFaculties) {
        this.nameOfFaculties = nameOfFaculties;
    }

    @Column(name = "nameOfFaculties")
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
