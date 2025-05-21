package game;

import board.ChessboardUI;

import javax.swing.*;

public class ChessGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        frame.add(new ChessboardUI());
        frame.setVisible(true);
    }
}
