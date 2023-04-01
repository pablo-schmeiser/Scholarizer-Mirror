package edu.kit.scholarizer.controllers.utils;

/**
 * This class is used to return an error message in case of an exception.
 * @author Pablo Schmeiser
 * @version 1.0
 */
public class ErrorMessage {
    private String error;

    /**
     * This is the default constructor.
     */
    ErrorMessage() {
        this.error = "";
    }

    /**
     * This constructor is used to create an error message using a given String as its content.
     * @param error the error messages content
     */
    public ErrorMessage(String error) {
        this.error = error;
    }

    /**
     * This method is used to get the error message.
     * @return the error message
     */
    public String getError() {
        return this.error;
    }

    /**
     * This method is used to set the error message.
     * @param error the error message
     */
    void setError(String error) {
        this.error = error;
    }
}
