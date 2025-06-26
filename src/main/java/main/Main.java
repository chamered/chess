package main;

import game.Game;
import game.GameImpl;

public class Main {
    public static void main(String[] args) {
        Game game = new GameImpl();
        game.start();
    }
}