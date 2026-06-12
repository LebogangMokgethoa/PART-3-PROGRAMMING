/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.userlogin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Lebogang.F Mokgethoa
 * @version 1.1 Testing
 */
public class UsermainregistrationTest {
    
    public UsermainregistrationTest() {
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

    /**
     * Test of main method, of class Usermainregistration.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Usermainregistration.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class Usermainregistration.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        Usermainregistration instance = null;
        String expResult = "";
        String result = instance.registerUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class Usermainregistration.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String enteredUser = "";
        String enteredPass = "";
        Usermainregistration instance = null;
        boolean expResult = false;
        boolean result = instance.loginUser(enteredUser, enteredPass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnLoginStatus method, of class Usermainregistration.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean success = false;
        Usermainregistration instance = null;
        String expResult = "";
        String result = instance.returnLoginStatus(success);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}





