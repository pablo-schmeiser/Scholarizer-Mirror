package edu.kit.scholarizer.controllers.utils;

/**
 * This class is used to store the paths of the controllers.
 * @author Pablo Schmeiser
 * @version 1.0
 */
public final class ControllerPath {
    /**
     * This is the path for the search controller.
     */
    public static final String SEARCH = "/search";
    /**
     * This is the path for the compare controller.
     */
    public static final String COMPARE = "/compare";
    /**
     * This is the path for the follow controller.
     */
    public static final String FOLLOW = "/follow";
    /**
     * The path for the bookmark controller.
     */
    public static final String BOOKMARK = "/bookmark";
    /**
     * The path for the recommendation controller.
     */
    public static final String RECOMMENDATION = "/recommended";
    /**
     * The path for the login controller.
     */
    public static final String LOGIN = "/login";
    /**
     * The path for the logout controller.
     */
    public static final String LOGOUT = "/logout";
    /**
     * The path for requesting a single author.
     */
    public static final String AUTHOR_LOOKUP = "/author_lookup";
    /**
     * The path for activating an account.
     */
    public static final String ACTIVATION = "/activate";

    /**
     * The path for the managing an account.
     */
    public static final String ACCOUNT = "/account";

    /**
     * The String to identify the query variable by.
     */
    public static final String QUERY_VARIABLE = "query";
    /**
     * The String to identify the query path variable by.
     */
    public static final String QUERY_PATH_VARIABLE = "/{" + QUERY_VARIABLE + "}";

    /**
     * The String to identify the uid variable by.
     */
    public static final String UID_VARIABLE = "uid";
    /**
     * The String to identify the oid variable by.
     */
    public static final String OID_VARIABLE = "oid";
    /**
     * The String to identify the uid path variable by.
     */
    public static final String UID_PATH_VARIABLE = "/{" + UID_VARIABLE + "}";
    /**
     * The String to identify the oid path variable by.
     */
    public static final String OID_PATH_VARIABLE = "/{" + OID_VARIABLE + "}";

    /**
     * The String to identify the root path by.
     */
    public static final String ROOT = "/";

    private ControllerPath() {
        //Utility class
    }
}
