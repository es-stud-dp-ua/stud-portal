package ua.dp.stud.askQuestionPortlet.model;

import java.io.Serializable;

/**
 * Mail changer model
 *
 * @author Alex
 */
public class MailChanger implements Serializable {
    private String newMail;

    /**
     * Constructor
     */
    public MailChanger() {
    }

    /**
     * @return newMail string
     */
    public String getNewMail() {
        return newMail;
    }

    /**
     * set new mail
     *
     * @param newMail
     */
    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }


    /**
     * Equals override
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MailChanger that = (MailChanger) o;

        if (newMail != null ? !newMail.equals(that.newMail) : that.newMail != null) {
            return false;
        }

        return true;
    }

    /**
     * hashcode override
     *
     * @return
     */
    @Override
    public int hashCode() {
        return newMail != null ? newMail.hashCode() : 0;
    }
}
