package org.example.chapter4.homework;

import fj.P;
import fj.data.List;
import fj.data.Validation;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PersonValidatorTest
{

    @Test
    public void match()
    {
        Validation<List<Fail>, Person> first = Validation.success(new Person(5, "K", 23, Gender.F));
        Validation<List<Fail>, Person> second = Validation.success(new Person(5, "M", 20, Gender.M));

        assertThat(
                PersonValidator.match(first, second),
                is(Validation.fail(List.arrayList(Fail.UNDERAGE)))
        );

        first = Validation.success(new Person(5, "K", 23, Gender.M));
        second = Validation.success(new Person(5, "M", 21, Gender.M));
        assertThat(
                PersonValidator.match(first, second),
                is(Validation.fail(List.arrayList(Fail.NON_HETERO_PAIR)))
        );
    }

}