package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class gameOverScreen extends JDialog{

    JPanel p = new JPanel();
    JPanel buttonsPanel = new JPanel();
    boolean wantNewGame = false;
    private final JLabel image;
    private JButton newGame = new JButton("New Game");
    private JButton close = new JButton("Close");

    public gameOverScreen(JFrame f, int w) throws IOException {
        //store who won in a String that can later be used to set the image
        String winner = "black_wins";
        if(w == 0){
            winner = "stalemate";
        }
        if(w == 1){
            winner = "white_wins";
        }
        if(w == 3){
            winner = "start";
        }
        String iconURL = "/img/backgrounds/" + winner + ".png";
        image = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(iconURL)).getScaledInstance(450, 225, Image.SCALE_SMOOTH)));
        //set the color of the buttons
        newGame.setBackground(new Color(232, 228, 214));
        newGame.setFont(new Font("Arial", Font.PLAIN, 30));
        close.setBackground(new Color(232, 228, 214));
        close.setFont(new Font("Arial", Font.PLAIN, 30));
        //add to the panel
        createLayout();
        //set up the dialog box
        setBounds(0, 0, 450, 300);
        setLocationRelativeTo(null);
        setModal(true);
        setUndecorated(true);
        add(p);
        setVisible(true);
    }
    public ActionListener closeListener = e -> {
        dispose();
    };
    public ActionListener newGameListener = e -> {
        wantNewGame = true;
        dispose();
    };
    //this is how I communicate to the other classes if the user wants a new game. The dialog box is modal, so once they pick a button the program will know if they want a new game or not
    public boolean getNewGame(){
        return wantNewGame;
    }
    public void createLayout(){
        newGame.addActionListener(newGameListener);
        close.addActionListener(closeListener);
        //I use gridlayout for my buttons since I want them to fill, so I added a second panel, buttonsPanel, that will then be added to my main panel, p
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsPanel.add(newGame);
        buttonsPanel.add(close);

        GroupLayout layout = new GroupLayout(p);
        p.setLayout(layout);
        //set up our horizontal group, adding the image and the buttons panel in horizontal parallel
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(image)
                .addComponent(buttonsPanel)
        );
        //set up our vertical group, now with both components in vertical sequential
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(image)
                .addComponent(buttonsPanel)
        );
    }
}
