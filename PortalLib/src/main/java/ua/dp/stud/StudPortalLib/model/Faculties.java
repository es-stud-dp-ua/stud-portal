package ua.dp.stud.StudPortalLib.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
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
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "nameOfFaculties", fetch = FetchType.LAZY)
    private List<Specialties> special;
    @Column(name = "nameOfFaculties")
    private String nameOfFaculties;
    @ManyToOne
    @JoinColumn(name = "BASE")
    private Studie base;
     
    public void setNameOfFaculties(String nameOfFaculties) {
        this.nameOfFaculties = nameOfFaculties;
    }

    public String getNameOfFaculties() {
        return nameOfFaculties;
    }

    public void setBase(Studie base) {
       this.base = base;
    }

    public Studie getBase() {
        return base;
    }
    
     public List<Specialties> getSpecialties() {
        return special;
    }

    public void setSpecialties(List<Specialties> specialties) {
        this.special = specialties;
    }
    
}
