package ua.dp.stud.aboutTeam.model;

/**
 * Author: Tradunsky V.V.
 * Date: 24.04.13
 */
public class Human {
    private String firstName;
    private String lastName;
    private String status;
    private String url;
    private String photoUrl;

    public Human(){
        firstName = "";
        lastName = "";
        status = "";
        url = "";
        photoUrl = "";
    }

    public Human(String firstName, String lastName, String status, String photoUrl, String url)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.url = url;
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Human[").append("firstName=").append(firstName)
                .append("lastName=").append(lastName)
                .append("status=").append(status)
                .append("url=").append(url)
                .append("photoUrl=").append(photoUrl).append("]");
        return sb.toString();
    }
}
