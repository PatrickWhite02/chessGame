package splashScreens;

import com.Board;

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
        setButton2ActionListener(cancelListener);
        buildGraphics();
    }
    private ActionListener cancelListener = e -> {
        //go back to the online game menu
        Board.getClient().kill();
        dispose();
        Board.setUpOnlineGame();
    };
}