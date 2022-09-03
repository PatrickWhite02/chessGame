package splashScreens;

import java.awt.*;

public class GameOverScreenGuest extends GameLaunchScreen{
    public GameOverScreenGuest(String w){
        System.out.println(w);
        setImage(w);
        setButton1Text("Wait for the host");
        setButton1Font(new Font("Arial", Font.PLAIN, 18));
        setButton2Text("To make new game");
        setButton2Font(new Font("Arial", Font.PLAIN, 18));
        buildGraphics();
    }
}
