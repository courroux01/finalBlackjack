package com.sweettooth.blackjack.models;

import java.util.Objects;
import java.util.ArrayList;


public class Card {

    public enum Suit {
        HEARTS, SPADES, DIAMONDS, CLUBS
    }

    public enum Value {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
        EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    private Value value;
    private Suit suit;
    private ArrayList<String> cardStringList;

    public Card(Value value, Suit suit){
        this.value = value;
        this.suit = suit;
        this.cardStringList = new ArrayList();
    }

    public Value getValue() {
        return this.value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
    
    public ArrayList<String> getCardStringList(){
        return this.cardStringList;
    }

    public void setCardStringList(ArrayList<String> cardStringList){
        this.cardStringList = cardStringList;
    }
        
    public int getHandValue(boolean isOverTen) {
        return switch (this.value) {
            case ACE -> isOverTen ? 1 : 11;
            case KING, QUEEN, JACK, TEN -> 10;
            case NINE -> 9;
            case EIGHT -> 8;
            case SEVEN -> 7;
            case SIX -> 6;
            case FIVE -> 5;
            case FOUR -> 4;
            case THREE -> 3;
            case TWO -> 2;
            default -> throw new IllegalArgumentException("Unexpected value: " + this.value);
        };
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        return this.suit == other.suit && this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }
    
    private String getShortString(Suit suit){
        return switch(suit){
            case Suit.CLUBS -> "C";
            case Suit.HEARTS -> "H";
            case Suit.DIAMONDS -> "D";
            case Suit.SPADES -> "S";
        };
    }
    
    private String getShortString(Value value){
        return switch(value){
            case Value.ACE -> "A";
            case Value.KING -> "K";
            case Value.QUEEN -> "Q";
            case Value.JACK -> "J";
            case Value.TEN-> "T";
            case Value.NINE -> "9";
            case Value.EIGHT -> "8";
            case Value.SEVEN -> "7";
            case Value.SIX -> "6";
            case Value.FIVE -> "5";
            case Value.FOUR -> "4";
            case Value.THREE -> "3";
            case Value.TWO -> "2";
        };
    }
    public ArrayList<String> getUpdatedCardStringList(boolean isHidden){
        this.cardStringList = new ArrayList();
        String v = isHidden ? " " : this.getShortString(this.value);
        String s = isHidden ? " " : this.getShortString(this.suit);

        this.cardStringList.add(" ----- ");
        this.cardStringList.add("|     |");
        this.cardStringList.add("| " + v + "   |");
        this.cardStringList.add("|     |");
        this.cardStringList.add("|   " + s + " |");
        this.cardStringList.add("|     |");
        this.cardStringList.add(" ----- ");
        
        return this.cardStringList;
    }
}
