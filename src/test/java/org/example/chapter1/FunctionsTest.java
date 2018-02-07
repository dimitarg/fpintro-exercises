package org.example.chapter1;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class FunctionsTest
{
    private static final int NUMBER = 5;
    private static final int OTHER_NUMBER = 7;
    private static final Function<Integer, Integer> FUNCTION = x -> 6 * x;
    private static final Function<Integer, Integer> SECOND_FUNCTION = y -> y / 2;

    private static final BiFunction<Integer, Integer, Double> BI_FUNCTION = (a, b) -> Math.pow(a, b);
    private static final Function<Integer, Function<Integer, Double>> CURRIED_FUNCTION = a -> (b -> Math.pow(a, b));

    @Test
    public void identityTest()
    {
        assertEquals(NUMBER, Functions.identity().apply(NUMBER));
    }

    @Test
    public void composeTest()
    {
        assertEquals(3 * NUMBER, Functions.compose(FUNCTION, SECOND_FUNCTION).apply(NUMBER).intValue());
    }

    @Test
    public void curryTest()
    {
        assertEquals(BI_FUNCTION.apply(NUMBER, OTHER_NUMBER),
                     Functions.curry(BI_FUNCTION).apply(NUMBER).apply(OTHER_NUMBER));
    }

    @Test
    public void consttTest()
    {
        assertEquals(NUMBER, Functions.constt(NUMBER).apply(OTHER_NUMBER).intValue());
    }

    @Test
    public void uncurryTest()
    {
        assertEquals(CURRIED_FUNCTION.apply(NUMBER).apply(OTHER_NUMBER),
                     Functions.uncurry(CURRIED_FUNCTION).apply(NUMBER, OTHER_NUMBER));
    }
}