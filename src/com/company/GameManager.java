package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {
    private ArrayList<Player> playerList = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);
    private final Scanner inputTwo = new Scanner(System.in);
    private final Dealer dealer = new Dealer();
    private boolean startGame;

    public GameManager() {
        startSetup();
        addPlayers();
        playGame();
        endGame();
    }

    /**
     * Initializes game start
     */
    private void startSetup() {
        System.out.println("""
               ___.   .__                 __         __               __   \s
               \\_ |__ |  | _____    ____ |  | __    |__|____    ____ |  | __
                | __ \\|  | \\__  \\ _/ ___\\|  |/ /    |  \\__  \\ _/ ___\\|  |/ /
                | \\_\\ \\  |__/ __ \\\\  \\___|    <     |  |/ __ \\\\  \\___|    <\s
                |___  /____(____  /\\___  >__|_ \\/\\__|  (____  /\\___  >__|_ \\
                    \\/          \\/     \\/     \\/\\______|    \\/     \\/     \\/
           """);
        String start = "0";

        System.out.print("Welcome to Blackjack! ");

        while(start.toUpperCase().charAt(0) != 'Y'){
            System.out.print("Would you like to start the game? ");
            start = input.nextLine();

            if (start.toUpperCase().charAt(0) == 'N'){
                System.out.println("Come again! Goodbye. ");
                System.exit(1);
            }
        }

        System.out.println();
        startGame = true;
    }

    /**
     * Adds dealer and amt. of players to playerList
     */
    private void addPlayers(){
        if(startGame) {
            System.out.print("How many players will be playing tonight? ");
            int numberOfPlayers = input.nextInt();

            while(numberOfPlayers < 1 || numberOfPlayers > 6) {
                System.out.println("Invalid number of players (1 - 6)");
                System.out.print("\nHow many players will be playing tonight? ");
                numberOfPlayers = input.nextInt();
            }

            System.out.println("All " + numberOfPlayers + " players have been accounted for.\n");
            for (int playerNumber = 0; playerNumber < numberOfPlayers; playerNumber++) {
                System.out.print("Player" + (playerNumber + 1) + " ");
                playerList.add(new Player());
            }

            System.out.println();
        }
    }

    /**
     * Creates new Deck, draws 2 cards (ea. player), completes hand (ea. player), and determines winner(s)
     */
    private void playGame() {
        Deck deck = new Deck();

        for(Player player : playerList) {
            System.out.print(player.getName() + " can bet a max of $" + player.getMoney() + ". ");
            player.setBetAmount();
        }

        System.out.println();

        for (int i = 0; i < 2; i++) {
            for (Player player : playerList) {
                System.out.print(player.getName() + " is dealt a ");
                player.addCard(deck.drawCard(true));
                System.out.println();
            }

            System.out.print("Dealer draws a ");

            if(i == 0) {
                dealer.addCard(deck.drawCard(true));
            } else {
                System.out.println("flipped over card");
                dealer.addCard(deck.drawCard(false));
            }

            System.out.println();
        }

        for (Player value : playerList) {
            System.out.println(value.getName() + "'s hand: " + value.getHandValue());
        }

        System.out.println("Dealers hand: " + dealer.getHand().get(0).getBlackjackValue());
        System.out.println();

        for(Player player : playerList) {
            player.playHand(deck);
            System.out.println();
        }

        if(!checkIfAllPlayersBusted()) {
            System.out.println("Dealer flips over " + dealer.getHand().get(1));
            System.out.println("Dealer hand: " + dealer.getHandValue());
            System.out.println();

            dealer.playHand(deck);
            checkWinCondition();
        }
    }

    /**
     * Checks if all players have busted
     * @return boolean All player busted
     */
    private boolean checkIfAllPlayersBusted() {
        boolean allPlayersBusted = true;

        for(Player player : playerList) {
            if(!player.getBusted()) {
                allPlayersBusted = false;
                break;
            }
        }

        return allPlayersBusted;
    }

    /**
     * Prints player results
     */
    private void checkWinCondition() {
        for(Player player : playerList) {
            if(player.getBusted() || player.getHandValue() > 21 || player.getHandValue() < dealer.getHandValue() && dealer.getHandValue() <= 21) {
                System.out.print(player.getName() + " lost! ");
                player.loseBet();
            } else if (player.getHandValue() == dealer.getHandValue()) {
                System.out.print(player.getName() + " pushes! ");
                player.pushBet();
            } else {
                System.out.print(player.getName() + " won! ");
                player.winBet();
            }
        }

        System.out.println();
    }

    /**
     * Asks users if they will continue playing
     */
    public void endGame() {
        ArrayList<Player> tempList = new ArrayList<>(playerList);

        for (Player player : playerList) {
            if (player.getMoney() <= 0) {
                System.out.print(player.getName() + " has insufficient funds to continue. ");
                tempList.remove(player);
            } else {
                String answer = "";

                System.out.print(player.getName() + ", would you like to play again? ");
                answer = inputTwo.nextLine();

                if (answer.toUpperCase().charAt(0) != 'Y') {
                    System.out.println(player.getName() + " has left the table.");
                    tempList.remove(player);
                }
            }

            String[] superWinMessage = new String[]{
                    player.getName() + " has won $" + (player.getMoney() - 100) + ", your practically a god!",
                    player.getName() + " has won $" + (player.getMoney() - 100) + ", what a legend!"
            };

            String[] winMessage = new String[]{
                    player.getName() + " has won $" + (player.getMoney() - 100) + ", good job!",
                    player.getName() + " has won $" + (player.getMoney() - 100) + ", luck was on your side!",
            };

            String[] lostMessage = new String[]{
                    player.getName() + " has lost $" + (100 - player.getMoney()) + ", try counting the cards!",
                    player.getName() + " has lost $" + (100 - player.getMoney()) + ", brighter days are ahead!",
                    player.getName() + " has lost $" + (100 - player.getMoney()) + ", im not crying you are!",
                    player.getName() + " has lost $" + (100 - player.getMoney()) + ", better luck next time!",
                    player.getName() + " has lost $" + (100 - player.getMoney()) + ", feels bad!"
            };

            if(player.getMoney() >= 1000) {
                System.out.println(superWinMessage[(int) (Math.random() * superWinMessage.length)]);
            } else if(player.getMoney() > 100) {
                System.out.println(winMessage[(int) (Math.random() * winMessage.length)]);
            } else if (player.getMoney() == 100) {
                System.out.println(player.getName() + " has tied with $" + player.getMoney() + "!");
            } else {
                System.out.println(lostMessage[(int) (Math.random() * lostMessage.length)]);
            }
        }

        System.out.println();
        playerList = tempList;

        if(playerList.size() == 0) {
            System.out.println("Thanks for playing!");
            System.exit(1);
        }

        clearAllParticipants();
        playGame();
        endGame();
    }

    /**
     * Resets game conditions
     */
    public void clearAllParticipants() {
        dealer.getHand().clear();

        for(Player player : playerList) {
            player.getHand().clear();
        }

        for(Player player : playerList) {
            player.setBusted(false);
        }
    }
}