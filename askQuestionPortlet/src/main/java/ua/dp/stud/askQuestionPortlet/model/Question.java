package ua.dp.stud.askQuestionPortlet.model;

import java.io.Serializable;

/**
 * Question models
 * @author Oleg
 */
public class Question implements Serializable {
    private String sentFrom;
    private String subject;
    private String text;

    /**
     * constructor
     */
    public Question() {
    }

    /**
     * returns sentFrom email
     * @return
     */
    public String getSentFrom() {
        return sentFrom;
    }

    /**
     * sets sentFrom email
     * @param sentFrom
     */
    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    /**
     * gets subject of mail
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject of mail
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * returns mail text
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * sets mail text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }
}
