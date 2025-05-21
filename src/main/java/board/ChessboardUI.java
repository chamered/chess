package board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessboardUI extends JPanel {
    private static final int TILE_SIZE = 75;
    private static final int ROWS = 8, COLS = 8;

    public ChessboardUI(){
        setPreferredSize(new Dimension(TILE_SIZE * COLS, TILE_SIZE * ROWS));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;
                System.out.println("Clicked on: " + row + "," + col);
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boolean isLight = (row + col) % 2 == 0;
                g.setColor(isLight ? Color.WHITE : Color.GRAY);
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setFont(new Font("Serif", Font.PLAIN, 60));
                g.drawString("♜", col * TILE_SIZE + 10, row * TILE_SIZE + 60);
            }
        }
    }
}
