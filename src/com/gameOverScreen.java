package com;

import javax.swing.*;
import java.awt.*;

public class gameOverScreen extends JDialog{
    private JPanel p = new JPanel(new GridLayout(4, 3));
    private JLabel messageToUser = new JLabel();
    private String message = "Black wins!";

    public gameOverScreen(JFrame f, int winner){
        if(winner == 0){
            message = "Stalemate!";
        }
        if(winner == 1){
            message = "White wins!";
        }
        createUIComponents();
        add(p);
        setVisible(true);
    }
    private void createUIComponents() {
        messageToUser.setText(message);
        p.add(messageToUser);
    }
}
