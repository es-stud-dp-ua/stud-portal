package ua.dp.stud.StudPortalLib.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
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

    @OneToMany( cascade =CascadeType.ALL, mappedBy="category", fetch = FetchType.LAZY)
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
    public String toString()
    {
        return new StringBuffer().append("Category[").append("categoryId=").append(categoryId).append(", categoryName=")
                .append(categoryName).append(']').toString();
    }

    @Override
    public int hashCode()
    {
        return  new HashCodeBuilder(START_HASH, MULT_HASH).append(this.categoryId).append(this.categoryName)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Category))
            return false;
        final Category other = (Category) obj;
        return new EqualsBuilder().append(other.categoryId, categoryId).append(other.categoryName, categoryName)
                .append(other.newsList, newsList).isEquals();
    }
}
