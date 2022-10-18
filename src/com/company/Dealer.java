package com.company;

public class Dealer extends Person{
    public void playHand(Deck deck) {
        while(getHandValue() < 17) {
            System.out.print("Dealer draws a ");
            addCard(deck.drawCard(true));
            System.out.println();
            System.out.println("Dealers hand: " + getHandValue());
            System.out.println();
        }
    }
}