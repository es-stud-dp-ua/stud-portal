package ua.dp.stud.studie.model;

//import ua.dp.stud.studie.model.Faculties;
import javax.persistence.*;
import java.io.Serializable;

/**
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

    public void setNameOfFaculties(Faculties nameOfFaculties) {
        this.nameOfFaculties = nameOfFaculties;
    }

    public Faculties getNameOfFaculties() {
        return nameOfFaculties;
    }

    @Column(name = "nameOfSpecialties")
    private String nameOfSpecialties;


    public void setNameOfSpecialties(String nameOfSpecialties) {
        this.nameOfSpecialties = nameOfSpecialties;
    }

    public String getNameOfSpecialties() {
        return nameOfSpecialties;
    }
}
