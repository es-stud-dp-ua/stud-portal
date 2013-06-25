/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.model;

import javax.persistence.*;
import java.io.Serializable;
/**
 *
 * @author Ольга
 */
 @Entity
@Table(name = "studies_table")
 @PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class Studie extends BaseImagesSupport implements Serializable  {

    private String title;
    private String text;
    private String adress;
    private String facultetDnevn;
    private String facultetZaoch;
    
    
    private static final int TEXT_LENGTH = 10000;
    public Studie(){}   
    public Studie(String topic, String text, String facultetDnevn,String facultetZaoch, String adress ) {
        this.title = topic;
        this.text = text;
        this.facultetDnevn=facultetDnevn;
        this.facultetZaoch=facultetZaoch;
        this.adress=adress;
    }
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "text",length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
   @Column(name = "adress") 
    public String getAdress(){
       return adress;
   }
   
     public void setAdress(String adress){
       this.adress=adress;
   }
     //todo: facepalm
    @Column(name = "facultetDnevn")  
     public String getFacultetsDnev(){     
      return facultetDnevn;
     }
    
   @Column(name = "facultetZaoch")
        public String getFacultetsZaoch(){          
      return facultetZaoch;
     }
   
    public void setFacultetsDnev(String facultetDnevn){
       this.facultetDnevn=facultetDnevn;
   }
    
   public void setFacultetsZaoch(String facultetZaoch){
       this.facultetZaoch=facultetZaoch;
   }
   
}
