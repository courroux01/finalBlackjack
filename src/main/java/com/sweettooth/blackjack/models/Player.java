package com.sweettooth.blackjack.models;

import java.util.ArrayList;
public class Player {
    private final String name;
    private final boolean isDealer;

    private ArrayList<Card> cards;
    private ArrayList<ArrayList<String>> cardStringLists;

    private int totalCardValue;
    
    
    public Player(){
        this.cards = new ArrayList();
        this.totalCardValue = 0;
        this.name = "Dealer";
        this.cardStringLists = new ArrayList();
        this.isDealer = true;
    }
    
    public Player(String name){
        this.cards = new ArrayList();
        this.totalCardValue = 0;
        this.name = name;
        this.cardStringLists = new ArrayList();
        this.isDealer = false;
    }
    
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getTotalCardValue() {
        return totalCardValue;
    }

    public void setTotalCardValue(int totalCardValue) {
        this.totalCardValue = totalCardValue;
    }
 
    public String getName() {
        return name;
    }
    
    public boolean isDealer(){
        return this.isDealer;
    }
    
    public ArrayList<ArrayList<String>> getCardStringLists(){
        return this.cardStringLists;
    }
    
    public ArrayList<ArrayList<String>> getVisibleCardStringLists(){
        this.cardStringLists = new ArrayList();
        for(Card card: this.cards){
            this.cardStringLists.add(card.getUpdatedCardStringList(false));
        }
        return this.cardStringLists;
    }
    
    public void hit(Card card, boolean isHidden){
        boolean isHandValueOverTen = this.totalCardValue > 10;
        
        this.cards.add(card);
        this.totalCardValue += card.getHandValue(isHandValueOverTen);
        this.cardStringLists.add(card.getUpdatedCardStringList(isHidden));
    };
    
    public boolean isContinuing(){
        return this.totalCardValue <= 21;
    }
}
