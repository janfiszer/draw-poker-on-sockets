package pl.edu.agh.kis.pz1.util;

import pl.edu.agh.kis.pz1.exeptions.DeckOutOfCardsException;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class Deck containing array of 52 cards
 * Attributes:
 * deck - array of 52 Cards
 * cards_quantity - constants representing cards quantity
 * iterator - index of card on the top
 */
public class Deck {
    public static final int CARDS_QUANTITY = 52;
    public Card [] cards = new Card[CARDS_QUANTITY];
    int iterator = 0;
    public Deck(){
        int index = 0;
        for (suit s: suit.values())
            for (rank r: rank.values()){
                Card card = new Card(r,s);
                cards[index] = card;
                index++;
        }
    }

    /**
     * Sorts cards like they are primarily in the deck straight from "fabric"
     */
    public void fabric(){
        int index = 0;
        for (suit s: suit.values())
            for (rank r: rank.values()){
                Card card = new Card(r,s);
                cards[index] = card;
                index++;
            }
    }
    /**
     * Randomly shuffles card in the deck
     */
    public void shuffle(){
        Random rnd = ThreadLocalRandom.current();
        for (int i = 0; i < CARDS_QUANTITY; ++i){
            int random_i = rnd.nextInt(CARDS_QUANTITY);
            Card tmp_card = cards[i];
            cards[i] = cards[random_i];
            cards[random_i] = tmp_card;
        }
    }
    /**
     * Draws a card from the top of the deck
     * Note that the first card of the deck is deck[0]
     * @throws DeckOutOfCardsException - player cannot have more than five cards
     * @return Card
     */
    public Card draw() throws DeckOutOfCardsException{
        if (iterator >= 52 || iterator < 0) throw new DeckOutOfCardsException("The deck is probably out of cards " +
                ", iterator = " + Integer.toString(iterator));
        iterator++;
        return cards[iterator - 1];
    }
}
