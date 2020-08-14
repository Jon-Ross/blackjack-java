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
}