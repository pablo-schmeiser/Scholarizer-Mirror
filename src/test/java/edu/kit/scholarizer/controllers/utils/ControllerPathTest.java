package edu.kit.scholarizer.controllers.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the ControllerPath class.
 *
 * @author Pablo Schmeiser
 * @version 1.0
 */
class ControllerPathTest {

        @Test
         void getSEARCH() {
            assertEquals("/search", ControllerPath.SEARCH);
        }

        @Test
        void getCOMPARE() {
            assertEquals("/compare", ControllerPath.COMPARE);
        }

        @Test
        void getFOLLOW() {
            assertEquals("/follow", ControllerPath.FOLLOW);
        }

        @Test
        void getBOOKMARK() {
            assertEquals("/bookmark", ControllerPath.BOOKMARK);
        }

        @Test
        void getRECOMMENDATION() {
            assertEquals("/recommended", ControllerPath.RECOMMENDATION);
        }

        @Test
        void getLOGIN() {
            assertEquals("/login", ControllerPath.LOGIN);
        }

        @Test
        void getLOGOUT() {
            assertEquals("/logout", ControllerPath.LOGOUT);
        }

        @Test
        void getAUTHOR_LOOKUP() {
            assertEquals("/author_lookup", ControllerPath.AUTHOR_LOOKUP);
        }

        @Test
        void getACTIVATION() {
            assertEquals("/activate", ControllerPath.ACTIVATION);
        }

        @Test
        void getACCOUNT() {
            assertEquals("/account", ControllerPath.ACCOUNT);
        }

        @Test
        void getQUERY_VARIABLE() {
            assertEquals("query", ControllerPath.QUERY_VARIABLE);
        }

        @Test
        void getQUERY_PATH_VARIABLE() {
            assertEquals("/{query}", ControllerPath.QUERY_PATH_VARIABLE);
        }

        @Test
        void getUID_VARIABLE() {
            assertEquals("uid", ControllerPath.UID_VARIABLE);
        }

        @Test
        void getOID_VARIABLE() {
            assertEquals("oid", ControllerPath.OID_VARIABLE);
        }

        @Test
        void getUID_PATH_VARIABLE() {
            assertEquals("/{uid}", ControllerPath.UID_PATH_VARIABLE);
        }

        @Test
        void getOID_PATH_VARIABLE() {
            assertEquals("/{oid}", ControllerPath.OID_PATH_VARIABLE);
        }

}
