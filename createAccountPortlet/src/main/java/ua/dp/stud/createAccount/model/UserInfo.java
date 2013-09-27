package ua.dp.stud.createAccount.model;

import java.io.Serializable;

/**
 * User: Tradunsky V.V.
 * Date: 21.02.13
 * Time: 23:08
 */
public class UserInfo implements Serializable {
    //fields
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password1;
    private String password2;
    private String placeOfStudy;
    private String faculty;
    private String group;
    private String vkontakteId;
    private String facebookId;
    private String aboutMe;
    private boolean rules;

    //constructors

    /**
     * Constructor by default
     */
    public UserInfo() {
    }
    //todo: do we really need two constructors? - yes

    /**
     * Constructor for new user necessary fields
     *
     * @param lastName     Last name
     * @param firstName    First name
     * @param emailAddress email address
     * @param password1    Password
     * @param password2    Confirm password
     * @param rules        Acept rules
     */
    public UserInfo(String lastName, String firstName,
                    String emailAddress, String password1,
                    String password2, Boolean rules) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password1 = password1;
        this.password2 = password2;
        this.rules = rules;
    }

    /**
     * Constructor for new user all fields
     *
     * @param lastName     Last name
     * @param firstName    First name
     * @param emailAddress email address
     * @param password1    Password
     * @param password2    Confirm password
     * @param rules        Acept rules
     * @param placeOfStudy Place of study
     * @param group        Group
     * @param vkontakteId  russian social networking id
     * @param facebookId   american social networking id
     * @param aboutMe      something about new user
     */
    public UserInfo(String lastName, String firstName,
                    String emailAddress, String password1,
                    String password2, Boolean rules,
                    String placeOfStudy, String group,
                    String vkontakteId, String facebookId,
                    String aboutMe
    ) {
        this(lastName, firstName, emailAddress, password1, password2, rules);
        this.placeOfStudy = placeOfStudy;
        this.group = group;
        this.vkontakteId = vkontakteId;
        this.facebookId = facebookId;
        this.aboutMe = aboutMe;
    }

    //getters & setter
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPlaceOfStudy() {
        return placeOfStudy;
    }

    public void setPlaceOfStudy(String placeOfStudy) {
        this.placeOfStudy = placeOfStudy;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVkontakteId() {
        return vkontakteId;
    }

    public void setVkontakteId(String vkontakteId) {
        this.vkontakteId = vkontakteId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public boolean isRules() {
        return rules;
    }

    public void setRules(boolean rules) {
        this.rules = rules;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
