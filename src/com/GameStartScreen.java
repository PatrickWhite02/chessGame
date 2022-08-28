package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameStartScreen extends GameLaunchScreen {
    private boolean onlineGame = false;
    public GameStartScreen(JFrame f, int w) throws IOException {
        super(f, w);
        setButton1Text("Local Game");
        setButton1ActionListener(localListener);
        setButton2Text("Online Game");
        setButton1ActionListener(onlineListener);
        //add to the panel
        createLayout();
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
}
