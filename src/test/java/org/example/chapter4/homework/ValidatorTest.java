package org.example.chapter4.homework;

import fj.data.Validation;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ValidatorTest
{

    @Test
    public void isValidString()
    {
        String str = null;
        assertThat(
                Validator.isValidString(str),
                is(Validation.fail(Fail.EMPTY_OR_NULL_STRING))
        );

        str = "";
        assertThat(
                Validator.isValidString(str),
                is(Validation.fail(Fail.EMPTY_OR_NULL_STRING))
        );

        str = "alabala";
        assertThat(
                Validator.isValidString(str),
                is(Validation.success(str))
        );
    }

    @Test
    public void parseInt()
    {
        String str = "alabala";
        assertThat(
                Validator.parseInt(str),
                is(Validation.fail(Fail.NOT_A_NUMBER))
        );

        str = "23";
        assertThat(
                Validator.parseInt(str),
                is(Validation.success(23))
        );
    }

    @Test
    public void isPositive()
    {
        int num = -5;
        assertThat(
                Validator.isPositive(num),
                is(Validation.fail(Fail.NOT_POSITIVE_NUMBER))
        );

        num = 0;
        assertThat(
                Validator.isPositive(num),
                is(Validation.fail(Fail.NOT_POSITIVE_NUMBER))
        );

        num = 5;
        assertThat(
                Validator.isPositive(num),
                is(Validation.success(num))
        );
    }

    @Test
    public void parseGender()
    {
        String gender = "pf";
        assertThat(
                Validator.parseGender(gender),
                is(Validation.fail(Fail.ILLEGAL_GENDER))
        );

        gender = "m";
        assertThat(
                Validator.parseGender(gender),
                is(Validation.success(Gender.M))
        );

    }
}