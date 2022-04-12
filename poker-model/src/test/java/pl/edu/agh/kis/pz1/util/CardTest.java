package pl.edu.agh.kis.pz1.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class testing Card class
 */
public class CardTest {
    Card c1;
    Card c2;
    Card c3;
    @Before
    public void setUp(){
        c1 = new Card(rank.ACE, suit.DIAMONDS);
        c2 = new Card(rank.ACE, suit.HEARTS);
        c3 = new Card(rank.FOUR, suit.HEARTS);
    }
    @Test
    public void compereToTest(){
        //Ace Diamonds > Four Hearths
        assertTrue(c1.compareTo(c3) > 0);
        //Ace Hearths > Ace Diamonds
        assertTrue(c2.compareTo(c1) < 0);
        //Ace Hearths > Four Hearths
        assertTrue(c2.compareTo(c3) > 0);
        //Ace Diamonds < Ace Hearths
        assertTrue(c1.compareTo(c2) > 0);
    }

}