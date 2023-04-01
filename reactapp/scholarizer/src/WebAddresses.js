/**
 * This file contains the web addresses to the backend and frontend
 *
 * If you want to run the frontend and backend on different URIs and Ports, change the values below
 *
 * @author Pablo Schmeiser
 * @version 1.0
 */

//Specify the URI and Port of the backend here
const BACKEND_URI = 'http://localhost';
const BACKEND_PORT = "8080";
//Set this to false if you don't want to specify a port for the backend
const BACKEND_PORT_REQUIRED = true;

//Specify the URI and Port of the frontend here
const FRONTEND_URI = 'http://localhost';
const FRONTEND_PORT = "3000";
//Set this to false if you don't want to specify a port for the frontend
const FRONTEND_PORT_REQUIRED = true;


/**
 * The path to the backend
 * @type {string|string}
 * @const
 * @example
 * returns "http://localhost:8080"
 */
export const BACKEND = BACKEND_PORT_REQUIRED
    ? BACKEND_URI + ":" + BACKEND_PORT + "/"
    : BACKEND_URI + "/";

/**
 * The path to the frontend
 * @type {string|string}
 * @const
 * @example
 * returns "http://localhost:3000"
 */
export const FRONTEND = FRONTEND_PORT_REQUIRED
    ? FRONTEND_URI + ":" + FRONTEND_PORT + "/"
    : FRONTEND_URI + "/";