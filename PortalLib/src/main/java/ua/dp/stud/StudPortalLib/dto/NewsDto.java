package ua.dp.stud.StudPortalLib.dto;

import java.util.Date;

public class NewsDto extends CommonDto{

	private String creationDate;
    private Integer numberOfViews;
    private String author;
    private String text;
    
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

    
    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	NewsDto(){}

    public NewsDto(String imgPath, String title, Integer id, String creationDate, String text,String author, Integer numberOfViews ) {
        super(imgPath, title, id);
        this.creationDate = creationDate;
        this.text = text;
        this.author=author;
        this.numberOfViews=numberOfViews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsDto)) return false;
        if (!super.equals(o)) return false;

        NewsDto newsDto = (NewsDto) o;

        if (text != null ? !text.equals(newsDto.text) : newsDto.text != null) return false;
        if (creationDate != null ? !creationDate.equals(newsDto.creationDate) : newsDto.creationDate != null) return false;
        if (author != null ? !author.equals(newsDto.author) : newsDto.author != null) return false;
        if (numberOfViews != null ? !numberOfViews.equals(newsDto.numberOfViews) : newsDto.numberOfViews != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (numberOfViews != null ? numberOfViews.hashCode() : 0);
        return result;
    }

}
