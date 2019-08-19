package com.rphc.rphc_app_android;


import com.rphc.rphc_app_android.auxiliary.Validators;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {

    @Test
    public void testEmptyEmail() {
        boolean actual = Validators.isValidEmail("test@test.de");
        assertTrue(actual);
    }
}
