package com.company;

import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> defaultDeck = new ArrayList<>();

    /**
     * Deck object
     */
    public Deck(){
        initializeDeck();
    }

    /**
     * Creates default deck
     */
    private void initializeDeck() {
        String[] suits = {"spades", "clubs", "diamonds", "hearts"};

        for(int value = 1; value <= 13; value++) {
            for (String suit : suits) {
                defaultDeck.add(new Card(suit, value));
            }
        }
    }

    /**
     * Randomly draws a card from deck
     * @return Card object
     */
    public Card drawCard(boolean visible) {
        int cardIndex = (int) (Math.random() * defaultDeck.size());
        Card cardDrawn = defaultDeck.get(cardIndex);
        defaultDeck.remove(cardIndex);

        if(visible) {
            System.out.print(cardDrawn);
        }

        return cardDrawn;
    }
}