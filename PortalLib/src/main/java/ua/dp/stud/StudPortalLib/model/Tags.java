
package ua.dp.stud.StudPortalLib.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ольга
 */
@Entity
@Table(name = "tags_table")
public class Tags implements Serializable{
    
    public Tags(){       
    }
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="events_tags",
               joinColumns={@JoinColumn(name="tagId")},
               inverseJoinColumns={@JoinColumn(name="id")})
    private List<Events> events;
    
    private String name;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tagId")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
     public List<Events> getEvents(){
       return this.events; 
    }
    
    public void setEvents(List<Events> events){
        this.events=events;
    }
    
    @Column(name="name")
      public String getName(){
       return this.name; 
    }
    
    public void setName(String name){
        this.name=name;
    }
    
}
