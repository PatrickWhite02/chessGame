package splashScreens;

import com.Board;

import java.awt.event.ActionListener;

public class ModalWaitingScreen extends GameLaunchScreen{
    public boolean wasCancel() {
        return cancel;
    }

    private boolean cancel = false;
    public ModalWaitingScreen(int tag){
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
    private ActionListener cancelListener = e -> {
        //go back to the online game menu
        cancel = true;
        setVisible(false);
        Board.getClient().kill();
        System.out.println("WaitingForOpponentScreen killed client");
        dispose();
    };
}
