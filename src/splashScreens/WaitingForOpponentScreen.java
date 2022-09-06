package splashScreens;

import com.Board;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WaitingForOpponentScreen extends GameLaunchScreen{
    public boolean wasCancel() {
        return cancel;
    }

    private boolean cancel = false;
    public WaitingForOpponentScreen(int tag){
        setImage("waiting_for_opponent");
        setButton1Text("Code: " + tag);
        setButton2Text("Cancel");
        System.out.println("Added button Text");
        setButton2ActionListener(cancelListener);
        System.out.println("Added button listener");
        buildGraphics();
        System.out.println("Built graphics");
        System.out.println("Made Screen");
    }
    public void interrupt(){
        System.out.println("Modal interruption");
        dispose();
    }
    private ActionListener cancelListener = e -> {
        //go back to the online game menu
        cancel = true;
        setVisible(false);
        Board.kill();
        System.out.println("WaitingForOpponentScreen killed client");
        dispose();
    };
}