package ua.dp.stud.askQuestionPortlet.model;

import java.io.Serializable;

/**
 * Question models
 *
 * @author Oleg
 */
public class Question implements Serializable {
    private String sentFrom;
    private String sentFromName;
    private String sentFromMobile;
    private String subject;
    private String text;

    /**
     * constructor
     */
    public Question() {
    }

    /**
     * returns sentFrom email
     *
     * @return
     */
    public String getSentFrom() {
        return sentFrom;
    }

    /**
     * sets sentFrom email
     *
     * @param sentFrom
     */
    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    /**
     * gets subject of mail
     *
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject of mail
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * gets SentFromName of mail
     *
     * @return
     */
    public String getSentFromName() {
        return sentFromName;
    }

    /**
     * Sets sentFromName of mail
     *
     * @param sentFromName
     */
    public void setSentFromName(String sentFromName) {
        this.sentFromName = sentFromName;
    }

    /**
     * gets SentFromMobile of mail
     *
     * @return
     */
    public String getSentFromMobile() {
        return sentFromMobile;
    }

    /**
     * Sets sentFromMobile of mail
     *
     * @param sentFromMobile
     */
    public void setSentFromMobile(String sentFromMobile) {
        this.sentFromMobile = sentFromMobile;
    }

    /**
     * returns mail text
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * sets mail text
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }
}
