package com.example.bookingapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class EvaluatorTest {

    @Test
    public void isValidCrsCodeTrue() {
        assertTrue(Evaluator.isValidCrsCode("MCG1100"));
    }
    @Test
    public void isValidCrsCodeFalse() {
        assertFalse(Evaluator.isValidCrsCode("Mcg110"));
    }



    @Test
    public void isValidSessionTrue() {
        assertTrue(Evaluator.isValidSession(15,45,17,19));
    }
    @Test
    public void isValidSessionFalse() {
        assertFalse(Evaluator.isValidSession(75,85,17,19));
    }

    @Test
    public void isValidFirstNameTrue() {
        assertTrue(Evaluator.isValidFirstName("Jack"));
    }
    @Test
    public void isValidFirstNameFalse() {
        assertFalse(Evaluator.isValidFirstName("Jack-8"));
    }

    @Test
    public void isValidLastNameTrue() {
        assertTrue(Evaluator.isValidLastName("Jack"));
    }

    @Test
    public void isValidUserNameTrue() {
        assertTrue(Evaluator.isValidUserName("eyaco078"));
    }
    @Test
    public void isValidUserNameFalse() {
        assertFalse(Evaluator.isValidUserName("eyaco078-"));
    }
}