package com;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class gameOverScreen extends GameLaunchScreen{
    private boolean wantNewGame = false;

    public gameOverScreen(JFrame f, int w) throws IOException {
        super(f, w);
        setButton1Text("New Game");
        setButton2Text("Close");
        setButton1ActionListener(newGameListener);
        setButton2ActionListener(closeListener);
        buildGraphics();
    }
    //action listener for the close button
    private final ActionListener closeListener = e -> dispose();
    //action listener for the new game button
    private final ActionListener newGameListener = e -> {
        wantNewGame = true;
        dispose();
    };
    //this is how I communicate to the other classes if the user wants a new game. The dialog box is modal, so once they pick a button the program will know if they want a new game or not
    public boolean getNewGame(){
        return wantNewGame;
    }
}
