package pl.edu.agh.kis.pz1.util;


import org.junit.*;

import static org.junit.Assert.*;

/**
 * Class testing Deck class
 */
public class DeckTest {
    private Deck d;
    private Card c;

    @Before
    public void setUp(){
        d = new Deck();
        //The first card in new deck
        c = new Card(rank.TWO, suit.SPADES);

    }
    @Test
    public void doesConstructorWorks(){
        System.out.println("TESTING: Deck()");
        assertEquals(d.cards[0], c);
    }
    @Test
    public void fabricTest(){
        d.shuffle();
        d.fabric();
        assertEquals(d.cards[0], c);
    }
    @Test
    public void doesShuffleWorks(){
        Deck fabric_d = new Deck();
        d.shuffle();
        int same_position_counter = 0;
        for (int i = 0; i < d.CARDS_QUANTITY; ++i)
            if (d.cards[i].equals(fabric_d.cards[i]))
                same_position_counter++;
        System.out.println("TESTING: shuffle()");
        System.out.println("Cards on the same position: " + same_position_counter);
        assertTrue(same_position_counter < 10); //10 cars in the same position are allowed
    }

    @AfterClass
    public static void setDown(){
        System.out.println("\n\nTESTING ENDED");
    }
}