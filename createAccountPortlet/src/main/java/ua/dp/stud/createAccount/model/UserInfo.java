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
    private String phoneNumber;
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
     * @param phoneNumber  Phone number
     * @param rules        Accept rules
     */
    public UserInfo(String lastName, String firstName,
                    String emailAddress, String password1,
                    String password2,String phoneNumber, Boolean rules) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password1 = password1;
        this.password2 = password2;
        this.phoneNumber = phoneNumber;
        this.rules = rules;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
      public boolean isRules() {
        return rules;
    }

    public void setRules(boolean rules) {
        this.rules = rules;
    }

}
