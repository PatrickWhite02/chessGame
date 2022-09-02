package splashScreens;

import java.awt.event.ActionListener;

public class GameStartScreen extends GameLaunchScreen {
    private boolean onlineGame = false;
    public GameStartScreen() {
        setImage("start");
        setButton1Text("Local Game");
        setButton1ActionListener(localListener);
        setButton2Text("Online Game");
        setButton2ActionListener(onlineListener);
        //add to the panel
        buildGraphics();
    }
    //action listener for the online game button
    private final ActionListener onlineListener = e -> {
        onlineGame = true;
        dispose();
    };
    //action listener for the local game button
    private final ActionListener localListener = e -> {
        dispose();
    };
    //this is how I communicate to the other classes if the user wants a new game. The dialog box is modal, so once they pick a button the program will know if they want a new game or not
    public boolean getOnlineGame(){
        return onlineGame;
    }
}
