package org.example.chapter4.homework;

import fj.data.Validation;

public class Validator
{
    public static Validation<Fail, String> isValidString(String str)
    {
        return str == null || str.equals("") ? Validation.fail(Fail.EMPTY_OR_NULL_STRING) : Validation.success(str);
    }

    public static Validation<Fail, Integer> parseInt(String str)
    {
        try
        {
            return Validation.success(Integer.parseInt(str));
        } catch (NumberFormatException e)
        {
            return Validation.fail(Fail.NOT_A_NUMBER);
        }
    }

    public static Validation<Fail, Integer> isPositive(int number)
    {
        return number > 0 ? Validation.success(number) : Validation.fail(Fail.NOT_POSITIVE_NUMBER);
    }

    public static Validation<Fail, Gender> parseGender(String str)
    {
        try
        {
            return Validation.success(Gender.valueOf(str.toUpperCase()));
        } catch (IllegalArgumentException e)
        {
            return Validation.fail(Fail.ILLEGAL_GENDER);
        }
    }
}
