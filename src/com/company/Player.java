package com.company;

import java.util.Scanner;

public class Player extends Person {
    private final Scanner input = new Scanner(System.in);
    private final String name;
    private boolean busted = false;
    private int money = 100;
    private int bet;

    /**
     * Player object w/name and hand
     */
    public Player() {
        System.out.print("what is your name? ");
        this.name = input.nextLine();
    }

    /**
     * Draws until player stands or bust
     * @param deck Default 52 card Deck
     */
    public void playHand(Deck deck) {
        System.out.print(name + ", hit or stand? ");
        input.nextLine();
        String choice = input.nextLine();

        //User hits
        while (!checkFirstCharacter(choice, 'S') && getHandValue() < 21) {
            if (checkFirstCharacter(choice, 'H')) {
                System.out.print(name + " is dealt a ");
                addCard(deck.drawCard(true));

                if (getHandValue() > 21) {
                    System.out.print(" and busts!\n");
                    System.out.println(name + "'s hand value: " + getHandValue());
                    loseBet();
                    busted = true;
                    return;
                }

                System.out.println();
                System.out.println(getName() + "'s hand value: " + getHandValue());
                System.out.println();
            } else {
                System.out.println("Incorrect option, try again \n");
            }

            System.out.print(getName() + ", hit or stand? ");
            choice = input.nextLine();
        }

        System.out.println(getName() + "'s hand value: " + getHandValue());
    }

    /**
     * Returns boolean expression if first character equals defined letter
     * @param choice User input
     * @param letter Defined letter
     * @return boolean expression
     */
    private boolean checkFirstCharacter(String choice, char letter) {
        return choice.toUpperCase().charAt(0) == letter;
    }

    /**
     * Gets player name
     * @return String Name of player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets busted boolean
     * @return boolean Busted player
     */
    public boolean getBusted() {
        return busted;
    }

    /**
     * Sets busted boolean
     * @param busted Busted player
     */
    public void setBusted(boolean busted) {
        this.busted = busted;
    }

    /**
     * Sets individual bet amount
     */
    public void setBetAmount() {
        System.out.print("How much would you like to bet? ");
        int bet = input.nextInt();

        while(bet > money || bet <= 0) {
            System.out.print("Insufficient funds. How much would you like to bet? ");
            bet = input.nextInt();
        }

        this.bet = bet;
        money -= bet;
    }

    /**
     * Adds player bet to rest of money
     */
    public void winBet() {
        money += bet * 2;
        System.out.print("New balance: $" + money + "\n");
    }

    /**
     * Adds bet amount back to money
     */
    public void pushBet() {
        money += bet;
        System.out.print("Balance: $" + money + "\n");
    }

    /**
     * Removes player bet from rest of money
     */
    public void loseBet() {
        System.out.print("New balance: $" + money + "\n");
    }

    /**
     * Returns integer representation of a players money
     * @return money Player money
     */
    public int getMoney() {
        return money;
    }
}