package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameStartScreen extends JDialog {
    private final JPanel p = new JPanel();
    private final JPanel buttonsPanel = new JPanel();
    private boolean onlineGame = false;
    private final JLabel image;
    private final JButton local = new JButton("Local Game");
    private final JButton online = new JButton("Online Game");
    private Board board;
    public GameStartScreen(JFrame f, int w) throws IOException {
        this.board = board;
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
        image = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(iconURL)).getScaledInstance(400, 225, Image.SCALE_SMOOTH)));
        //set the color of the buttons
        local.setBackground(new Color(232, 228, 214));
        local.setFont(new Font("Arial", Font.PLAIN, 30));
        online.setBackground(new Color(232, 228, 214));
        online.setFont(new Font("Arial", Font.PLAIN, 30));
        //add to the panel
        createLayout();
        //set up the dialog box
        setBounds(0, 0, 450, 325);
        setLocationRelativeTo(null);
        setModal(true);
        add(p);
    }
    //action listener for the close button
    private final ActionListener onlineListener = e -> {
        onlineGame = true;
        dispose();
    };
    //action listener for the new game button
    private final ActionListener localListener = e -> {
        dispose();
    };
    //this is how I communicate to the other classes if the user wants a new game. The dialog box is modal, so once they pick a button the program will know if they want a new game or not
    public boolean getOnlineGame(){
        return onlineGame;
    }
    private void createLayout(){
        local.addActionListener(localListener);
        online.addActionListener(onlineListener);
        //I use gridlayout for my buttons since I want them to fill, so I added a second panel, buttonsPanel, that will then be added to my main panel, p
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsPanel.add(local);
        buttonsPanel.add(online);

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
