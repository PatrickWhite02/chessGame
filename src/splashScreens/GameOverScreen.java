package splashScreens;

import com.Board;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameOverScreen extends GameLaunchScreen {
    private boolean wantNewGame = false;
    private boolean wantKillGame = false;
    private WaitForHostScreen waitForHostScreen;
    public WaitForHostScreen getWaitForHostScreen(){ return waitForHostScreen;}
    public GameOverScreen(String w, boolean host){
        System.out.println(w);
        setImage(w);
        setButton1Text("New Game");
        setButton2Text("Close");
        setButton2ActionListener(closeListener);
        if(host){
            setButton1ActionListener(newGameListenerHost);
        }else{
            setButton1ActionListener(newGameListenerGuest);
        }
        buildGraphics();
    }
    //action listener for the close button
    private final ActionListener closeListener = e -> {
        System.out.println("gameOverScreen close button");
        wantKillGame = true;
        Board.kill();
        dispose();
    };
    //action listener for the new game button
    private final ActionListener newGameListenerHost = e -> {
        wantNewGame = true;
        dispose();
    };
    private final ActionListener newGameListenerGuest = e -> {
        dispose();
        waitForHostScreen = new WaitForHostScreen();
        waitForHostScreen.activate();
    };
    //this is how I communicate to the other classes if the user wants a new game. The dialog box is modal, so once they pick a button the program will know if they want a new game or not
    public boolean getNewGame(){
        return wantNewGame;
    }
    public boolean getKillGame(){
        return wantKillGame;
    }
    public void launchGuest(){
        dispose();
    }
}
