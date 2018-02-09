package org.example.chapter2;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConsListTest
{
    private static final int NUMBER = 5;
    private static final ConsList<Integer> EMPTY_LIST = ConsList.empty();
    private static final ConsList<Integer> NON_EMPTY_LIST =
                                 ConsList.cons(5, ConsList.cons(7, ConsList.cons(3, ConsList.empty())));

    @Test
    public void givenEmptyListWhenInvokeLengthThenReturnZero()
    {
        assertEquals(0, EMPTY_LIST.length());
    }

    @Test
    public void givenNonEmptyListWhenInvokeLengthThenReturnAccurateResult()
    {
        assertEquals(3, NON_EMPTY_LIST.length());
    }

    @Test
    public void givenEmptyListWhenInvokeAppendThenReturnAccurateResult()
    {
        ConsList<Integer> result = EMPTY_LIST.append(NUMBER);
        assertTrue(result.length() == 1);
        assertEquals(NUMBER, result.head().intValue());
    }

    @Test
    public void givenNonEmptyListWhenInvokeAppendThenReturnAccurateResult()
    {
        ConsList<Integer> expected = ConsList.cons(5, ConsList.cons(7, ConsList.cons(3, ConsList.cons(NUMBER, ConsList.empty()))));
        ConsList<Integer> result = NON_EMPTY_LIST.append(NUMBER);
        assertEquals(NON_EMPTY_LIST.length() + 1, result.length());
        assertEquals(expected, result);
    }

    @Test
    public void givenEmptyListsWhenInvokeEqualsThenReturnTrue() {
        ConsList<Integer> newEmptyList = ConsList.empty();
        assertEquals(EMPTY_LIST, newEmptyList);
    }

    @Test
    public void givenNonEqualListsWhenInvokeEqualsThenReturnFalse() {
        ConsList<Integer> nonEqualList =
                ConsList.cons(5, ConsList.cons(7, ConsList.cons(2, ConsList.empty())));

        assertNotEquals(NON_EMPTY_LIST, nonEqualList);
    }

    @Test
    public void givenEqualListsWhenInvokeEqualsThenReturnTrue() {
        ConsList<Integer> equalList =
                ConsList.cons(5, ConsList.cons(7, ConsList.cons(3, ConsList.empty())));

        assertEquals(NON_EMPTY_LIST, equalList);
    }

    @Test
    public void whenInvokeReverseThenReturnReversedList()
    {
        ConsList<Integer> expected = ConsList.cons(3, ConsList.cons(7, ConsList.cons(5, ConsList.empty())));
        assertEquals(expected, NON_EMPTY_LIST.reverse());

        ConsList<Integer> twiceReversed = NON_EMPTY_LIST.reverse().reverse();
        assertEquals(NON_EMPTY_LIST, twiceReversed);
    }

    @Test
    public void whenInvokeTakeWhileThenReturnAccurateSublist()
    {
        ConsList<Integer> expected = ConsList.cons(5, ConsList.cons(7, ConsList.empty()));
        assertEquals(expected, NON_EMPTY_LIST.takeWhile(num -> num != 3));
    }

    @Test
    public void whenInvokeDropWhileThenReturnAccurateSublist()
    {
        ConsList<Integer> expected = ConsList.cons(3, ConsList.empty());
        assertEquals(expected, NON_EMPTY_LIST.dropWhile(num -> num != 3));
    }

    @Test
    public void whenInvokeFilterThenReturnAccurateSublist()
    {
        ConsList<Integer> expected = ConsList.cons(5, ConsList.cons(7, ConsList.empty()));
        assertEquals(expected, NON_EMPTY_LIST.filter(num -> num > 3));
    }

    @Test
    public void whenInvokeMapThenReturnListWithMappedValues()
    {
        ConsList<Integer> expected = ConsList.cons(9, ConsList.cons(13, ConsList.cons(5, ConsList.empty())));
        assertEquals(expected, NON_EMPTY_LIST.map(num -> num * 2 - 1));
    }
}