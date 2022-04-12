package pl.edu.agh.kis.pz1.exeptions;

import org.junit.Test;
import pl.edu.agh.kis.pz1.util.Deck;

import static org.junit.Assert.*;

public class DeckOutOfCardsExceptionTest {
    Deck deck;

    private void drawing53Cards() throws DeckOutOfCardsException {
        deck = new Deck();
        // Drawing more that deck card quantity
        for (int i = 0; i < 54; ++i)
            deck.draw();
    }
    @Test(expected = DeckOutOfCardsException.class)
    public void test() throws DeckOutOfCardsException {
        drawing53Cards();
    }
}