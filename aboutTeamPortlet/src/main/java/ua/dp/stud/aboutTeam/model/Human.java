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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.firstName);
        sb.append("\n");
        sb.append(this.lastName);
        sb.append("\n");
        sb.append(this.status);
        sb.append("\n");
        sb.append(this.photoUrl);
        sb.append("\n");
        sb.append(this.url);
        sb.append("\n");
        return sb.toString();
    }
}
