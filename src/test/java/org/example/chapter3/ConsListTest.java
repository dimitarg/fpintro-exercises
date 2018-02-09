package org.example.chapter3;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;

public class ConsListTest
{
    @Test
    public void filter()
    {
        assertThat(
                ConsList.fromArray().filter(a -> a == a),
                is(ConsList.empty())
        );

        assertThat(
                ConsList.fromArray(1, 2, 3, 4, 5).filter(a -> a > 3),
                is(ConsList.fromArray(4, 5))
        );

        assertThat(
                ConsList.fromArray(1, 2, 3, 4, 5).filter(a -> a > -100),
                is(ConsList.fromArray(1, 2, 3, 4, 5))
        );
    }

    @Test
    public void map()
    {
        assertThat(
                ConsList.fromArray().map(num -> num),
                is(ConsList.empty())
        );

        assertThat(
                ConsList.fromArray(1, 2, 3, 4).map(num -> num * 2),
                is(ConsList.fromArray(2, 4, 6, 8))
        );
    }

    @Test
    public void forAll()
    {
        assertThat(
                ConsList.fromArray().forAll(obj -> obj == obj),
                is(true)
        );

        assertThat(
                ConsList.fromArray(1, 2, 3).forAll(num -> num > 2),
                is(false)
        );

        assertThat(
                ConsList.fromArray(1, 2, 3).forAll(num -> num > -100),
                is(true)
        );
    }

    @Test
    public void exists()
    {
        assertThat(
                ConsList.fromArray().exists(obj -> obj == obj),
                is(false)
        );

        assertThat(
                ConsList.fromArray(1, 2, 3).exists(num -> num > 2),
                is(true)
        );

        assertThat(
                ConsList.fromArray(1, 2, 3).exists(num -> num > 100),
                is(false)
        );
    }

    @Test
    public void foldRight2()
    {
        assertThat(
                ConsList.fromArray(1, 2, 3).foldRight2((current, result) -> ConsList.cons(current, result), ConsList.empty()),
                is(ConsList.fromArray(1, 2, 3))
        );

        assertThat(
                ConsList.fromArray(1, 2, 3, 4, 5).foldRight2((current, result) -> current - result, 0),
                is(1 - (2 - (3 - (4 - 5))))
        );
    }
}
