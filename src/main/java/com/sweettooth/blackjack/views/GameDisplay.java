package com.sweettooth.blackjack.views;

import com.sweettooth.blackjack.models.Player;
import com.sweettooth.blackjack.models.Card;
import com.sweettooth.blackjack.controllers.Game;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class GameDisplay{
    private final Game game;
    private final Scanner sc;
    
    public GameDisplay(Game game){
        this.sc = new Scanner(System.in);
        this.game = game;
    }
    
    public void displayCard(Card card){
        String cardDisplayString = "";
        
        for(String s: card.getCardStringList()){
            cardDisplayString += s + "\n";
        }
        
        System.out.printf(
            "Card: %s of %s: \n %s\n",
             card.getValue().toString(),
             card.getSuit().toString(),
             cardDisplayString
        );
        
    }
    
    public void displayCards(Player player, boolean isShowAll){
        ArrayList<ArrayList<String>> toShow = 
            isShowAll ? 
            player.getVisibleCardStringLists() :
            player.getCardStringLists()
        ;
                
        ArrayList<String> toPrint = new ArrayList();
        
        for(int i = 0; i < toShow.get(0).size(); i++){
            toPrint.add(" ");
        }
        
        for(ArrayList<String> cardStringList: toShow){
            for(int i = 0; i < cardStringList.size(); i++){
                toPrint.set(i, toPrint.get(i) + cardStringList.get(i) + " ");
            }
        }
        
        for(String toPrintLine: toPrint){
            System.out.println(toPrintLine);
        }
    }
    
    
    public void displayPlayer(Player player, boolean isShowAll){
        String playerValue = 
            !player.isDealer() || isShowAll? 
            String.valueOf(player.getTotalCardValue()) : 
            "X";
        
        System.out.printf(
            "%s: Total Card Value %s.\n Cards: \n",
            player.getName(),
            playerValue
        );
        
        this.displayCards(player, isShowAll);
    }
    
    
    private void displayLine(){
        System.out.printf(
            "\n==============================================================\n"
        );
    }
    
    public void  displayMainMenu(){
        this.displayLine();
        System.out.println("Welcome to Blackjack!");
    }
    
    public ArrayList<String> displayInitializePlayerMenu() {
        ArrayList<String> playerNames = new ArrayList<>(); // Specify type when creating the ArrayList
        
        this.displayLine(); // Assuming this method is defined elsewhere in your class
        int playerCount = 0;
        
        while (true) {
            try {
                System.out.printf("How many players are playing? Type a number.\n-> ");
                playerCount = this.sc.nextInt();
                this.sc.nextLine(); // Consume the leftover newline
                
                if (playerCount < 1) {
                    System.out.println("Please type a number greater than or equal to 1");
                    continue; // Loop again until valid input
                }
                break; // Exit the loop if valid input is given
                
            } catch (Exception e) {
                System.out.println("Please type a number.");
                sc.nextLine(); // Consume the invalid input
            }
        }
        
        // Collect player names
        for (int i = 0; i < playerCount; i++) {
            System.out.printf("Enter player #%d's name: \n-> ", i + 1);
            playerNames.add(sc.nextLine());
        }
        
        return playerNames;
    }
    
    public void displayRound(boolean isShowAll){
        this.displayLine();
        System.out.printf("Round %d:\n", this.game.getRoundCount());
        this.displayPlayer(this.game.getDealer(), isShowAll);
        for(Player player: this.game.getPlayers()){
            this.displayPlayer(player, isShowAll);
        }
    }
    
    
    public String displayPlayerRoundChoice(Player player){
        this.displayLine();
        
        while(true){
            System.out.printf("Player %s, Hit or Stand?\n-> ", player.getName());
            String playerInput = sc.nextLine();
            playerInput = playerInput.toLowerCase();
            
            if(playerInput.equals("hit") ||
               playerInput.equals("stand")
              ) { return playerInput; }
            
            System.out.println("Please type 'hit' or 'stand'. ");
        }
    }

    public void displayHit(Player player, Card card){
        this.displayLine();
        
        System.out.printf(
            "Player %s hits! \n", 
            player.getName()
        );
        
        this.displayCard(card);
    }
    
    public void displayCantContinue(Player player){
        this.displayLine();
        System.out.printf(
            "Player %s bricked! Moving on to the next round...\n", 
            player.getName()
        );
    }
    
    public void displayStand(Player player){
        this.displayLine();
        System.out.printf(
            "Player %s stands! \n", 
            player.getName()
        );
    }

    public void displayResults(HashMap<String, HashMap<Player, String>> result){
        this.displayRound(true);
        
        for(String key: result.keySet()){
            this.displayLine();
            System.out.printf("%s:\n", key);
            
            for(Map.Entry<Player, String> playerResult: result.get(key).entrySet()){
                Player player = playerResult.getKey();
                String stringResult = playerResult.getValue();
                System.out.printf(
                    "%s %s Total Card Value: %d. Card Count: %d\n",
                    player.getName(),
                    stringResult,
                    player.getTotalCardValue(),
                    player.getCards().size()
                );
            }
        }
    }
    
    public String displayPlayAgainMenu(){
        this.displayLine();
        while(true){
            try{
                System.out.printf(
                    "Do you want to play again? Type 'yes' or 'no'.\n-> "
                );
                String choice = sc.nextLine();
                choice = choice.toLowerCase();
                
                if(
                   choice.equals("yes") ||
                   choice.equals("no")
                  ){
                    return choice;
                } else {
                    System.out.println("Please type 'yes' or 'no'.");
                }
            }catch(Exception e){
                System.out.println("Please type 'yes' or 'no'.");
            }
        }
    }
    
    public void displayThankYouMenu(){
        this.displayLine();
        System.out.println("Thank you for playing! Made by Abdullah Alojado.");
        this.displayLine();
    }
}

