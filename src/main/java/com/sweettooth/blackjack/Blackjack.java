package com.sweettooth.blackjack;

// Some of the code here is for future project reference

import com.sweettooth.blackjack.controllers.Game;

public class Blackjack {
    
    public Blackjack(){};
    
    public void playNewGame(){
        Game game = new Game(this);

    }
    public static void main(String[] args) {
        new Blackjack().playNewGame();
    }
}