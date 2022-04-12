package pl.edu.agh.kis.pz1.util;

import org.junit.*;
import pl.edu.agh.kis.pz1.exeptions.DeckOutOfCardsException;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class PlayerTest{
    Player player;
    Deck deck;
    @Before
    public void setUp(){
        player = new Player("jonson19");
        deck = new Deck();
    }
    @Test
    public void addMonetTest(){
        player.addMoney(1000);
        assertEquals(2000, player.getMoney());
    }
    @Test
    public void drawingTest() throws DeckOutOfCardsException {
        player.startingDraw(deck);
        Deck fabric_deck = new Deck();
        for (int i = 0; i < player.CARDS_IN_HAND_QUANTITY; ++i) {
            assertEquals(player.hand[i], fabric_deck.cards[i]);
        }
    }
    @Test
    public void sortingTest() throws DeckOutOfCardsException {
        deck.shuffle();
        player.startingDraw(deck);
        player.sortHand();
        for (int i = 0; i < player.CARDS_IN_HAND_QUANTITY -1 ; ++i){
            assertTrue(player.hand[i].compareTo(player.hand[i+1]) < 0);
        }
    }
//    public void sortingNotFullHandTest() throws ToManyCardsDrawnException, DeckOutOfCardsException {
//        deck.shuffle();
//        int to_draw = 3;
//        player.playerDraw(to_draw, deck);
//        player.sortHand();
//        for (int i = 0; i < to_draw; ++i){
//            assertTrue(player.hand[i].compareTo(player.hand[i+1]) < 0);
//        }
//    }
    @Test
    public void pairTest(){
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FOUR, suit.HEARTS);
        Card c3 = new Card(rank.SEVEN, suit.HEARTS);
        Card c4 = new Card(rank.SIX, suit.HEARTS);
        Card c5 = new Card(rank.FIVE, suit.HEARTS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        int result = player.Pair();
        assertEquals(0, result);
    }
    @Test
    public void twoPairsTest(){
        Card c1 = new Card(rank.SIX, suit.SPADES);
        Card c2 = new Card(rank.KING, suit.DIAMONDS);
        Card c3 = new Card(rank.SEVEN, suit.SPADES);
        Card c4 = new Card(rank.NINE, suit.HEARTS);
        Card c5 = new Card(rank.SEVEN, suit.CLUB);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        int result = player.TwoPair();
        assertEquals(-1, result);
    }
    @Test
    public void straightTest() throws DeckOutOfCardsException {
        player.startingDraw(deck);
        int result = player.Straight();
        assertEquals(0, result);
        Card c = new Card(rank.ACE, suit.SPADES);
        player.hand[4] = c;
        result = player.Straight();
        assertEquals(0, result);
    }
    @Test
    public void flushTest() throws DeckOutOfCardsException {
        player.startingDraw(deck);
        int result = player.Straight();
        assertEquals(0, result);
    }
    @Test
    public void straightFlushTest() throws DeckOutOfCardsException {
        player.startingDraw(deck);
        int result = player.StraightFlush();
        assertEquals(0, result);
    }
    @Test
    public void fullHouseTest(){
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FOUR, suit.HEARTS);
        Card c3 = new Card(rank.SEVEN, suit.HEARTS);
        Card c4 = new Card(rank.SEVEN, suit.SPADES);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        int result = player.FullHouse();
        assertEquals(2, result);
        Card c6 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c7 = new Card(rank.FOUR, suit.HEARTS);
        Card c8 = new Card(rank.FOUR, suit.SPADES);
        Card c9 = new Card(rank.SEVEN, suit.SPADES);
        Card c10 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c6;
        player.hand[1] = c7;
        player.hand[2] = c8;
        player.hand[3] = c9;
        player.hand[4] = c10;
        result = player.FullHouse();
        assertEquals(0, result);
    }
    @Test
    public void checkHandTestDeterminedHand(){
        // Player has o full house
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FOUR, suit.HEARTS);
        Card c3 = new Card(rank.SEVEN, suit.HEARTS);
        Card c4 = new Card(rank.SEVEN, suit.SPADES);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        player.checkHand();
        assertEquals(handRanking.FULL_HOUSE, player.best_hand_ranking);
    }
    //TEST JUST TO DEBUG
    @Test
    public void checkHandTest() throws DeckOutOfCardsException {
        deck.shuffle();
        player.startingDraw(deck);
        player.checkHand();
        assertTrue(true);
    }
    @Test
    public void checkHandStraightAndFlushTest() {
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.SIX, suit.DIAMONDS);
        Card c3 = new Card(rank.JACK, suit.DIAMONDS);
        Card c4 = new Card(rank.QUEEN, suit.DIAMONDS);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        player.checkHand();
        assertEquals(0, player.index_of_best);
        assertEquals(handRanking.FLUSH, player.best_hand_ranking);
        Card c6 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c7 = new Card(rank.THREE, suit.HEARTS);
        Card c8 = new Card(rank.TWO, suit.SPADES);
        Card c9 = new Card(rank.FIVE, suit.SPADES);
        Card c10 = new Card(rank.SIX, suit.CLUB);
        player.hand[0] = c6;
        player.hand[1] = c7;
        player.hand[2] = c8;
        player.hand[3] = c9;
        player.hand[4] = c10;
        player.checkHand();
        assertEquals(0, player.index_of_best);
        assertEquals(handRanking.STRAIGHT, player.best_hand_ranking);
    }
    @Test
    public void checkHandStraightFlushTest() throws DeckOutOfCardsException {
        player.startingDraw(deck);
        player.checkHand();
        assertEquals(handRanking.STRAIGHT_FLUSH, player.best_hand_ranking);
        assertEquals(0, player.index_of_best);
    }
    @Test
    public void checkHandPairTest(){
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FOUR, suit.HEARTS);
        Card c3 = new Card(rank.JACK, suit.DIAMONDS);
        Card c4 = new Card(rank.QUEEN, suit.DIAMONDS);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        player.checkHand();
        assertEquals(handRanking.PAIR, player.best_hand_ranking);
        assertEquals(0, player.index_of_best);
    }
    @Test
    public void checkHandTwoPairsTest(){
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FOUR, suit.HEARTS);
        Card c3 = new Card(rank.JACK, suit.DIAMONDS);
        Card c4 = new Card(rank.JACK, suit.HEARTS);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        player.checkHand();
        assertEquals(handRanking.TWO_PAIRS, player.best_hand_ranking);
        assertEquals(0, player.index_of_best);
    }
    @Test
    public void checkHandThreeOfAKind(){
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FOUR, suit.HEARTS);
        Card c3 = new Card(rank.FOUR, suit.SPADES);
        Card c4 = new Card(rank.QUEEN, suit.DIAMONDS);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        player.checkHand();
        assertEquals(handRanking.THREE_OF_A_KIND, player.best_hand_ranking);
        assertEquals(0, player.index_of_best);
    }
    @Test
    public void checkHandFourOfAKind(){
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FOUR, suit.HEARTS);
        Card c3 = new Card(rank.FOUR, suit.SPADES);
        Card c4 = new Card(rank.FOUR, suit.CLUB);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        player.checkHand();
        assertEquals(handRanking.FOUR_OF_A_KIND, player.best_hand_ranking);
        assertEquals(0, player.index_of_best);
    }
    @Test
    public void OnlyHighCardTest(){
        Card c1 = new Card(rank.FOUR, suit.DIAMONDS);
        Card c2 = new Card(rank.FIVE, suit.HEARTS);
        Card c3 = new Card(rank.JACK, suit.CLUB);
        Card c4 = new Card(rank.QUEEN, suit.DIAMONDS);
        Card c5 = new Card(rank.SEVEN, suit.DIAMONDS);
        player.hand[0] = c3;
        player.hand[1] = c4;
        player.hand[2] = c5;
        player.hand[3] = c1;
        player.hand[4] = c2;
        assertEquals(-1, player.Pair());
        assertEquals(-1, player.TwoPair());
        assertEquals(-1, player.ThreeOfAKind());
        assertEquals(-1, player.Flush());
        assertEquals(-1, player.FullHouse());
        assertEquals(-1, player.FourOfAKind());
        assertEquals(-1, player.Straight());
        assertEquals(-1, player.StraightFlush());
        assertEquals(-1, player.Pair());
    }
    @Test
    public void changeCardTest() throws DeckOutOfCardsException {
        player.startingDraw(deck);
        ArrayList<Integer> to_change = new ArrayList<>();
        to_change.add(1);
        to_change.add(2);
        to_change.add(3);
        player.changeHand(to_change, deck);
        rank spades_rank = rank.SEVEN;
        for (int i = 1; i < 4; ++i){
            assertEquals(player.hand[i].rank, spades_rank);
            spades_rank = spades_rank.next();
        }
    }

}