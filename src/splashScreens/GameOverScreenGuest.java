package splashScreens;

public class GameOverScreenGuest extends GameLaunchScreen{
    public GameOverScreenGuest(String w){
        System.out.println(w);
        setImage(w);
        setButton1Text("Wait for the host");
        setButton2Text("To make new game");
        buildGraphics();
    }
}
