package blackjack_core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {

    private final Hand hand = new Hand();

    @Test
    public void GivenNoValues_WhenSum_ThenReturn0() {
        // given
        // when
        final int sumResult = hand.sum();

        // then
        assertEquals(0, sumResult);
    }

    @Test
    public void GivenValue_WhenSum_ThenReturnValue() {
        // given
        hand.addValue(10);

        // when
        final int sumResult = hand.sum();

        // then
        assertEquals(10, sumResult);
    }

    @Test
    public void GivenValues_WhenSum_ThenReturnTheSum() {
        // given
        hand.addValue(10);
        hand.addValue(1);

        // when
        final int sumResult = hand.sum();

        // then
        assertEquals(11, sumResult);
    }

    @Test
    public void GivenValues_WhenToString_ThenShowValuesInBrackets() {
        // given
        hand.addValue(1);
        hand.addValue(3);
        // when
        // then
        assertEquals("[1, 3]", hand.toString());
    }

    @Test
    public void GivenNoValues_WhenToString_ThenShowNoValues() {
        // given
        // when
        // then
        assertEquals("[]", hand.toString());
    }
}