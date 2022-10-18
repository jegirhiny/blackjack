package com.company;

public class Card {
    private final String suit;
    private final int value;

    /**
     * Card object w/suit and value
     */
    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    /**
     * Gets blackjack value of card
     * @return int Integer representation of card value
     */
    public int getBlackjackValue() {
        if(value > 10) {
            return 10;
        } else if(value == 1) {
            return 11;
        }

        return value;
    }

    /**
     * Translates card value to regular expression
     * @return String Representation of the object
     */
    public String toString() {
        switch (value) {
            case (1): return "ace of " + suit;
            case (11): return "jack of " + suit;
            case (12): return "queen of " + suit;
            case (13): return "king of " + suit;
            default: return value + " of " + suit;
        }
    }
}