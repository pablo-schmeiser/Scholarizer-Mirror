package edu.kit.scholarizer.security;

/**
 * This enum is used to hold the address of the ScholariZer web application.
 * @author Tim Wolk
 * @version 1.0
 */
public enum WebAdresses {
    /**
     * The address of the ScholariZer web applications backend.
     */
    BACKEND_ADDRESS,
    
    /**
     * The address of the ScholariZer web applications frontend.
     */
    FRONTEND_ADDRESS;

    private String adr;

    static {
        BACKEND_ADDRESS.adr = "http://localhost:8080";
        FRONTEND_ADDRESS.adr = "http://localhost:3000";
    }

    /**
     * Getter for the web address.
     * @return the web address as a String.
     */
    public String getAddress() {
        return this.adr;
    }
}
