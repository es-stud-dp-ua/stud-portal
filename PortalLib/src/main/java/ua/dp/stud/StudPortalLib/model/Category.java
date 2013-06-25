package ua.dp.stud.StudPortalLib.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**

 * @author Roman Lukash
 */
@Entity
@Table(name="news_category")
public class Category implements Serializable {
    public Category(){}

    public Category(String categoryName){
        this.categoryName=categoryName;
    }
    private static final int START_HASH=7;
    private static final int MULT_HASH=43;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="category_id")
    private Integer categoryId;
    @Column(name="category_name")
    private String categoryName;
    //todo: newsList must be lazy?
    @OneToMany( cascade =CascadeType.ALL, mappedBy="category")
    @OnDelete(action=OnDeleteAction.CASCADE)
    private List<News> newsList ;
    public Integer getId() {
        return categoryId;
    }
    public void setId(Integer id) {
        this.categoryId = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<News> getNews() {
        return newsList;
    }

    public void setNews(List<News> newsSet) {
        this.newsList= newsSet;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + categoryId+ ", categoryName=" + categoryName +'}';
    }

    @Override
    public int hashCode() {
        int hash = START_HASH;
        hash = MULT_HASH * hash + (this.categoryName != null ? this.categoryName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.categoryId != other.categoryId && (this.categoryId == null || !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        if ((this.categoryName == null) ? (other.categoryName != null) : !this.categoryName.equals(other.categoryName)) {
            return false;
        }
        return true;
    }
}
