package splashScreens;

import com.Board;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WaitForHostScreen extends GameLaunchScreen{
    public WaitForHostScreen(){
        setImage("wait_for_host");
        setButton1Text("Start Menu");
        setButton2Text("Close");
        setButton1ActionListener(startMenu);
        setButton2ActionListener(closeListener);
        buildGraphics();
    }
    private final ActionListener startMenu = e -> {
        dispose();
        Board.startMenu();
    };
    private final ActionListener closeListener = e -> {
        dispose();
        Board.kill();
    };
    public void launchGuest(){
        dispose();
    }
}
