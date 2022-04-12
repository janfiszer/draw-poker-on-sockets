package pl.edu.agh.kis.pz1.util;

import pl.edu.agh.kis.pz1.exeptions.DeckOutOfCardsException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing single player
 * cards_in_hand_quantity - final int equals 5
 * nick - nick of a player
 * hand - card that the player has
 * best_hand_ranking - best hand ranking of the player
 */
public class Player {
    public static final int CARDS_IN_HAND_QUANTITY = 5;
    final static int STARTING_MONEY = 1000;
    String nick;
    protected Card [] hand = new Card[CARDS_IN_HAND_QUANTITY];
    public handRanking best_hand_ranking;
    public int index_of_best;
    private int money;

    /**
     * Player constructor requiring a nickname, set money to starting value
     * @param nick - nickname for the Player
     */
    public Player(String nick) {
        this.nick = nick;
        money = STARTING_MONEY;
    }

    public Card[] getHand() {
        return hand;
    }

    public void addMoney(int money_to_add) {
        this.money += money_to_add;
    }

    public int getMoney() {
        return money;
    }

    /**
     * represents a drawing cards by the player
     * probably will be only used in first draw of a game (drawing 5 cards so the cars_to_draw won't be used)
     * @throws DeckOutOfCardsException - deck run out of cards
     */
    public void startingDraw(Deck deck) throws DeckOutOfCardsException {
        for (int i = 0; i < CARDS_IN_HAND_QUANTITY; ++i){
            hand[i] = deck.draw();
        }
    }

    /**
     * Sorts player's hand ascending
     */
    void sortHand(){
        Arrays.sort(hand);
    }

    /**
     * Lets player change his cards
     * @param indexes_to_change - indexes of cards to change
     * @param deck - deck from which player draws
     * @throws DeckOutOfCardsException - deck can run out of cards
     */
    public void changeHand(ArrayList<Integer> indexes_to_change, Deck deck) throws DeckOutOfCardsException{
        for(int index: indexes_to_change){
            hand[index] = deck.draw();
        }
    }

    /**
     * Function that check if there is a pair ranking in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int Pair(){
        sortHand();
        // cards are sorted so if player has a pair two same cards have to be next to each other
        for (int i = 0; i < CARDS_IN_HAND_QUANTITY - 1; ++i){
            if (hand[i].rank == hand[i+1].rank)
                return i;
        }
        return -1;
    }
    /**
     * Function that check if there is a three of a kind ranking in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int ThreeOfAKind(){
        sortHand();
        // same as in Pair
        for (int i = 0; i < CARDS_IN_HAND_QUANTITY - 2; ++i){
            if (hand[i].rank == hand[i+2].rank)
                return i;
        }
        return -1;
    }
    /**
     * Function that check if there is a four of a kind in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int FourOfAKind(){
        sortHand();
        // same as in Pair
        for (int i = 0; i < CARDS_IN_HAND_QUANTITY - 3; ++i){
            if (hand[i].rank == hand[i+3].rank)
                return i;
        }
        return -1;
    }
    /**
     * Function that check if there are two pairs in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int TwoPair(){
        int index = Pair();
        // Two pair are only possible if the first is in the first 3 cards
        // Fourth and fifth card in hand are same (they create a pair)
        if ((index == 1 || index == 0) && hand[3].rank.equals(hand[4].rank)){
            return index;
        }
        // Fourth and third card in hand are same (they create a pair)
        if (index == 0 && hand[2].rank == hand[3].rank)
            return index;
        return -1;
    }
    /**
     * Function that check if there is a straight in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int Straight(){
        sortHand();

        for (int i = 0; i < CARDS_IN_HAND_QUANTITY - 2; ++i){
            if (hand[i].rank.next() != hand[i+1].rank)
                return -1;
        }
        // "Normal" straight e.g. THREE SPADES, FOUR HEARTHS, FIVE SPADES, SIX DIAMONDS, SEVEN CLUB
        if (hand[3].rank.next() == hand[4].rank) {
            return 0;
        }
        // Straight with an Ace as One
        // e.g. TWO CLUB, THREE SPADES, FOUR HEARTHS, FIVE SPADES, ACE CLUB
        if ((hand[3].rank == rank.FIVE && hand[4].rank == rank.ACE))
            return 0;
        return -1;
    }
    /**
     * Function that check if there is a flush in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int Flush(){
        sortHand();
        for (int i = 0; i < CARDS_IN_HAND_QUANTITY - 1; ++i){
            if (hand[i].suit != hand[i+1].suit)
                return -1;
        }
        return 0;
    }
    /**
     * Function that check if there is a straight flush in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int StraightFlush(){
        sortHand();
        if (Flush() == 0 && Straight() == 0)
            return 0;
        return -1;
    }
    /**
     * Function that check if there is a full house in the player's hand
     * @return Index of the HIGHEST (if rank the same, suit counts) card of the ranking, if there is no such a ranking returns -1
     */
    protected int FullHouse(){
        sortHand();
        int index = -1;
        int three = ThreeOfAKind();
        if ((three == 0 && hand[3].rank == hand[4].rank) || (three == 2 && Pair() == 0)){
                index = three;
        }
        return index;
    }

    /**
     * Finds the best hand ranking in player's hand
     * Changes best_hand_ranking and index_of_best
     */
    public void checkHand(){
        if (StraightFlush() == 0) {
            index_of_best = 0;
            best_hand_ranking = handRanking.STRAIGHT_FLUSH;
            return;
        }
        int index = FourOfAKind();
        if (index != -1){
            index_of_best = index;
            best_hand_ranking = handRanking.FOUR_OF_A_KIND;
            return;
        }
        if (Flush() == 0) {
            index_of_best = 0;
            best_hand_ranking = handRanking.FLUSH;
            return;
        }
        if (Straight() == 0) {
            index_of_best = 0;
            best_hand_ranking = handRanking.STRAIGHT;
            return;
        }
        index = FullHouse();
        if (index != -1){
            index_of_best = index;
            best_hand_ranking = handRanking.FULL_HOUSE;
            return;
        }
        index = ThreeOfAKind();
        if (index != -1) {
            index_of_best = index;
            best_hand_ranking = handRanking.THREE_OF_A_KIND;
            return;
        }
        index = TwoPair();
        if (index != -1){
            index_of_best = index;
            best_hand_ranking = handRanking.TWO_PAIRS;
            return;
        }
        index = Pair();
        if (index != -1){
            index_of_best = index;
            best_hand_ranking = handRanking.PAIR;
            return;
        }
        best_hand_ranking = handRanking.HIGH_CARD;
        index_of_best = 4;
    }
}
