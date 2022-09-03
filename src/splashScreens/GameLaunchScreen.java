package splashScreens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameLaunchScreen extends JDialog {
    private JLabel image;
    private JButton button1 = new JButton();
    private JButton button2 = new JButton();
    private Font button1Font = new Font("Arial", Font.PLAIN, 30);
    private Font button2Font = new Font("Arial", Font.PLAIN, 30);
    private Color buttonColor = new Color(232, 228, 214);
    JPanel p;
    private boolean modalOveroad = false;
    public GameLaunchScreen(){
    }
    public GameLaunchScreen(JFrame f){
        super(f);
    }
    public void setImage (String fileName){
        String iconUrl = "/img/backgrounds/" + fileName + ".png";
        try {
            image = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(iconUrl)).getScaledInstance(450, 225, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setButton1Font(Font font){
        button1Font = font;
    }
    public void setButton2Font(Font font){
        button2Font = font;
    }
    public void setButton1Text (String text){
        button1.setBackground(buttonColor);
        button1.setFont(button1Font);
        button1.setText(text);
    }
    public void setButton1ActionListener (ActionListener listener){
        button1.addActionListener(listener);
    }
    public void setButton2Text (String text){
        button2.setBackground(buttonColor);
        button2.setFont(button2Font);
        button2.setText(text);
    }
    public void overrideModality(boolean modal){
        setModal(modal);
        modalOveroad = true;
    }
    public void setButton2ActionListener (ActionListener listener){
        button2.addActionListener(listener);
    }
    //this is how I communicate to the other classes if the user wants a new game. The dialog box is modal, so once they pick a button the program will know if they want a new game or not
    public void buildGraphics(){
        p = new JPanel();
        JPanel buttonsPanel = new JPanel();
        //I use gridlayout for my buttons since I want them to fill, so I added a second panel, buttonsPanel, that will then be added to my main panel, p
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsPanel.add(button1);
        buttonsPanel.add(button2);
        System.out.println("Added buttons to panel");
        GroupLayout layout = new GroupLayout(p);
        p.setLayout(layout);
        //set up our horizontal group, adding the image and the buttons panel in horizontal parallel
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(image)
                        .addComponent(buttonsPanel)
        );
        //set up our vertical group, now with both components in vertical sequential
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(image)
                        .addComponent(buttonsPanel)
        );
        System.out.println("Set layout groups");
        //set up the dialog box
        setBounds(0, 0, 450, 300);
    }
    public void activate(){
        setLocationRelativeTo(null);
        if(!modalOveroad){
            setModal(true);
        }
        add(p);
        setUndecorated(true);
        setVisible(true);
    }
}
