package pl.edu.agh.kis.pz1.util;

/**
 * Enum of cards' ranks which considers ACE as ONE when needed
 */
public enum rank {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
    JACK, QUEEN, KING, ACE {
        @Override
        public rank next() {
            return TWO; // see below for options for this line
        }
    };
    public rank next() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() + 1];
    }
}
