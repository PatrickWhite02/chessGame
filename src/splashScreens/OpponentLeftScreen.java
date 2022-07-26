package splashScreens;

import com.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OpponentLeftScreen extends GameLaunchScreen {
    private void build(){
        setImage("opponent_disconnected");
        setButton1Font(new Font("Arial", Font.PLAIN, 18));
        setButton1Text("Wait For New Opponent");
        setButton1ActionListener(waitForNewOpponentListener);
        setButton2Text("Close");
        setButton2ActionListener(closeListener);
        buildGraphics();
    }
    public OpponentLeftScreen() {
        build();
    }
    private final ActionListener closeListener = e -> {
        dispose();
        Board.startMenu();
    };
    private final ActionListener waitForNewOpponentListener = e -> {
        dispose();
        Board.startWaitForOpponent();
    };
}