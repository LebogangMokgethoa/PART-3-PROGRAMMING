/*
 * ... license comment ...
 */
package com.mycompany.userlogin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lebogang.F Mokgethoa
 * @version 1.0 Testing
 */
public class UserloginTest {

    public UserloginTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Userlogin.main(args);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        Userlogin instance = null;
        String expResult = "";
        String result = instance.registerUser();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String enteredUser = "";
        String enteredPass = "";
        Userlogin instance = null;
        boolean expResult = false;
        boolean result = instance.loginUser(enteredUser, enteredPass);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean success = false;
        Userlogin instance = null;
        String expResult = "";
        String result = instance.returnLoginStatus(success);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}