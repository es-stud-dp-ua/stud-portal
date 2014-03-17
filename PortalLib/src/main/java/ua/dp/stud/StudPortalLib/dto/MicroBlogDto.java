package ua.dp.stud.StudPortalLib.dto;

import java.util.Date;

public class MicroBlogDto extends CommonDto{

    private String creationDate;
    private Integer numberOfViews;
    private String author;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(Integer numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    MicroBlogDto(){

    }

    public MicroBlogDto(String imgPath, String title, Integer id, String creationDate, String author, Integer numberOfViews ) {
        super(imgPath, title, id);
        this.creationDate = creationDate;
        this.author=author;
        this.numberOfViews=numberOfViews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof MicroBlogDto)){
            return false;
        }
        if (!super.equals(o)){
            return false;
        }

        MicroBlogDto MicroBlogDto = (MicroBlogDto) o;

        if (creationDate != null ? !creationDate.equals(MicroBlogDto.creationDate) : MicroBlogDto.creationDate != null){
            return false;
        }
        if (author != null ? !author.equals(MicroBlogDto.author) : MicroBlogDto.author != null){
            return false;
        }
        if (numberOfViews != null ? !numberOfViews.equals(MicroBlogDto.numberOfViews) : MicroBlogDto.numberOfViews != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (numberOfViews != null ? numberOfViews.hashCode() : 0);
        return result;
    }

}