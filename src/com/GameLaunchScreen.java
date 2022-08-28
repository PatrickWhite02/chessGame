package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameLaunchScreen extends JDialog {
    private final JLabel image;
    private final JButton button1 = new JButton();
    private final JButton button2 = new JButton();
    public GameLaunchScreen(JFrame f, int w) throws IOException {
        //store who won in a String that can later be used to set the image
        String winner = "black_wins";
        System.out.println(w);
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
        button1.setBackground(new Color(232, 228, 214));
        button1.setFont(new Font("Arial", Font.PLAIN, 30));
        button2.setBackground(new Color(232, 228, 214));
        button2.setFont(new Font("Arial", Font.PLAIN, 30));
    }
    public void setButton1Text (String text){
        button1.setText(text);
    }
    public void setButton1ActionListener (ActionListener listener){
        button1.addActionListener(listener);
    }
    public void setButton2Text (String text){
        button2.setText(text);
    }
    public void setButton2ActionListener (ActionListener listener){
        button2.addActionListener(listener);
    }
    //this is how I communicate to the other classes if the user wants a new game. The dialog box is modal, so once they pick a button the program will know if they want a new game or not
    public void buildGraphics(){
        JPanel p = new JPanel();
        JPanel buttonsPanel = new JPanel();
        //I use gridlayout for my buttons since I want them to fill, so I added a second panel, buttonsPanel, that will then be added to my main panel, p
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsPanel.add(button1);
        buttonsPanel.add(button2);

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
        //set up the dialog box
        setBounds(0, 0, 450, 300);
        setLocationRelativeTo(null);
        setModal(true);
        add(p);
        setUndecorated(true);
        setVisible(true);
    }
}
