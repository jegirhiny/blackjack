package com.company;

import java.util.ArrayList;

public class Person {
    private final ArrayList<Card> hand = new ArrayList<>();

    /**
     * Gets player hand
     * @return ArrayList of a players cards
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Adds card drawn to players hand
     * @param drawnCard Card object
     */
    public void addCard(Card drawnCard) {
        hand.add(drawnCard);
    }

    /**
     * Gets players hand value
     * @return int Integer representation of hand value
     */
    public int getHandValue() {
        int handValue = 0;

        for(Card card : hand) {
            int cardValue = card.getBlackjackValue();
            handValue += cardValue;

            if(cardValue == 11 && handValue > 21) {
                handValue -= 10;
            }
        }

        return handValue;
    }
}