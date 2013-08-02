package ua.dp.stud.studie.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Ольга
 * @author Vladislav Pikus
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

    public void setNameOfFaculties(Faculties nameOfFaculties) {
        this.nameOfFaculties = nameOfFaculties;
    }

    public Faculties getNameOfFaculties() {
        return nameOfFaculties;
    }

    @Size(min = 2, max = 100)
    @NotEmpty
    private String nameOfSpecialties;

    public void setNameOfSpecialties(String nameOfSpecialties) {
        this.nameOfSpecialties = nameOfSpecialties;
    }

    @Column(name = "nameOfSpecialties")
    public String getNameOfSpecialties() {
        return nameOfSpecialties;
    }
}
