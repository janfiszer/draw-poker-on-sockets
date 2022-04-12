package pl.edu.agh.kis.pz1.util;

import java.util.Objects;

/**
 * Class representing a play card, gives information about its rank and suit
 * Attributes:
 * - suit: card suit, described by enum
 * - rank: card rank, described by enum
 */

public class Card implements Comparable<Card>{
    protected rank rank;
    protected suit suit;

    /**
     * To check whether shuffling works
     * @param o object to compare with
     * @return true if cards are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
    /**
     * Card constructor:
     * @param r - card's rank
     * @param s - card's suit
     */
    public Card(rank r, suit s){
        rank = r;
        suit = s;
    }

    @Override
    public String toString() {
        return rank + " " + suit + " ";
    }

    @Override
    public int compareTo(Card c) {
        if (rank.compareTo(c.rank) != 0)
            return rank.compareTo(c.rank);
        else
            return suit.compareTo(c.suit);
    }
}
