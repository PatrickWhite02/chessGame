package splashScreens;

import com.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OpponentLeftScreen extends GameLaunchScreen {
    public OpponentLeftScreen() {
        setImage("opponent_disconnected");
        setButton1Font(new Font("Arial", Font.PLAIN, 18));
        setButton1Text("Wait For New Opponent");
        setButton1ActionListener(waitForNewOpponentListener);
        setButton2Text("Close");
        setButton2ActionListener(closeListener);
        buildGraphics();
    }
    private final ActionListener closeListener = e -> {
        dispose();
        Board.kill();
    };
    private final ActionListener waitForNewOpponentListener = e -> {
        dispose();
        Board.startWaitForOpponent();
    };
}