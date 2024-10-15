package com.sweettooth.blackjack.controllers;

import com.sweettooth.blackjack.models.Player;
import com.sweettooth.blackjack.models.Card;
import com.sweettooth.blackjack.views.GameDisplay;
import com.sweettooth.blackjack.Blackjack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game{
    
    private final ArrayList<Card> currentDeck;
    private final ArrayList<Player> players;
    private final Player dealer;
    private final GameDisplay gameDisplay;
    private final Blackjack blackjack;
    
    private int currentPlayerIndex;
    private int roundCount;
    
    
    // Default Constructor for Blackjack
    public Game(Blackjack blackjack) {
        this.currentDeck = new ArrayList();
        this.players = new ArrayList();
        this.dealer = new Player(); // Dealer initialization
        this.gameDisplay = new GameDisplay(this);
        this.blackjack = blackjack;
        
        this.currentPlayerIndex = 0;
        this.roundCount = 0;
        
        this.initializeGame();
    }
    
    // Getter for currentDeck
    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }


    // Getter for players
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // Getter for dealer
    public Player getDealer() {
        return dealer;
    }

    // Getter and Setter for roundCount
    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }
    
    private Card getRandomCard(){
        return this.currentDeck.remove(0);
    }
    
    // Method to initialize the deck of 52 cards
    private void initializeDeck(){        
        for (Card.Value value : Card.Value.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                this.currentDeck.add(new Card(value, suit));
            }
        }
        
        // Shuffle the deck
        Collections.shuffle(this.currentDeck);
    }
    
    private void initializePlayers(){
        ArrayList<String> playerNames = this.gameDisplay.displayInitializePlayerMenu();
        
        for(String playerName: playerNames){
            this.players.add(new Player(playerName));
        }
    }
    
    private void dealStartingCards(){
        for(Player player: this.players){
            player.hit(this.getRandomCard(), false);
            player.hit(this.getRandomCard(), false);
        }
        
        this.dealer.hit(this.getRandomCard(), true);
        this.dealer.hit(this.getRandomCard(), false);
    }
   
    private void playRound(){
        if(this.currentPlayerIndex >= this.players.size()) {
            this.evaluateGame();
            return;
        }
        
        this.roundCount++;
        this.gameDisplay.displayRound(false);
        
        Player currentPlayer = this.players.get(this.currentPlayerIndex);
        
        String playerChoice = this.gameDisplay.displayPlayerRoundChoice(currentPlayer);
        
        if(playerChoice.equals("hit")){
            Card hitCard = this.getRandomCard();
            currentPlayer.hit(hitCard, false);
            this.gameDisplay.displayHit(currentPlayer, hitCard);
            
            boolean isContinuing = currentPlayer.isContinuing();
            
            if(!isContinuing){
                this.currentPlayerIndex++;
                this.gameDisplay.displayCantContinue(currentPlayer);
            }
        } else {
            this.currentPlayerIndex++;
            this.gameDisplay.displayStand(currentPlayer);
        }
        
        
        this.playRound();
    }
    
    private void evaluateGame(){
        HashMap<String, HashMap<Player, String>> result = new HashMap();
        result.put("Winners", new HashMap());
        result.put("Losers", new HashMap());
        
        for(Player player: this.players){
            if(player.getTotalCardValue() > 21){
                result.get("Losers").put(player, "bricked!");
            } else if(player.getTotalCardValue() > dealer.getTotalCardValue()){
                result.get("Winners").put(player, "won!");
            } else if(
                player.getTotalCardValue() > dealer.getTotalCardValue() &&
                player.getCards().size() <= dealer.getCards().size()
            )   {
                result.get("Winners").put(player, "won with a better blackjack!");
            } else {
                result.get("Losers").put(player, "lost!");
            }
        }
        
        this.gameDisplay.displayResults(result);
        
        String choice = this.gameDisplay.displayPlayAgainMenu();
        
        if(choice.equals("yes")){
            this.blackjack.playNewGame();
        }else{
            this.gameDisplay.displayThankYouMenu();
        }
        
    }    
    
        
    private void initializeGame(){
        this.gameDisplay.displayMainMenu();

        this.initializeDeck();
        this.initializePlayers();
        this.dealStartingCards();
        
        this.playRound();
    }
}