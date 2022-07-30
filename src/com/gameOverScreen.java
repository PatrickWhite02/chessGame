package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class gameOverScreen extends JDialog{

    private String winner = "black_wins";
    JPanel p = new JPanel();
    private JLabel image;
    private JButton newGame = new JButton("New Game");
    private JButton close = new JButton("Close");
    public gameOverScreen(JFrame f, int w) throws IOException {
        if(w == 0){
            winner = "stalemate";
        }
        if(w == 1){
            winner = "white_wins";
        }
        String iconURL = "/img/backgrounds/" + winner + ".png";
        image = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(iconURL))));
        image.setLayout(new GridLayout());
        close.setPreferredSize(new Dimension(20, 10));
        image.add(close);
        p.add(image);
        add(p);
    }
}
