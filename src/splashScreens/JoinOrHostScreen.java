package splashScreens;

import splashScreens.GameLaunchScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JoinOrHostScreen extends GameLaunchScreen {
    private boolean host = false;

    private JTextField jTextField;

    private JDialog jDialog;

    public int getInput() {
        return input;
    }

    private int input;
    public JoinOrHostScreen() throws IOException {
        super("join_or_host");
        setButton1Text("Join Game");
        setButton1ActionListener(joinGameListener);
        setButton2Text("Host Game");
        setButton2ActionListener(hostGameListener);
        buildGraphics();
    }
    public boolean isHost(){
        return host;
    }
    private ActionListener submitListener = e -> {
        try{
            input = Integer.parseInt(jTextField.getText());
            jDialog.dispose();
            dispose();
        } catch (NumberFormatException numberFormatException) {
            jTextField.setText("Error! Only enter numbers!");
        }
    };
    private final ActionListener joinGameListener = e -> {
        JPanel p = new JPanel(new FlowLayout());
        jDialog = new JDialog();
        jTextField = new JTextField("Insert 4 digit key here");
        jTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(submitListener);
        p.add(jTextField);
        p.add(submitButton);
        jDialog.setBounds(new Rectangle(200, 75));
        jDialog.setUndecorated(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.add(p);
        jDialog.setModal(true);
        jDialog.setVisible(true);
    };
    private final ActionListener hostGameListener = e -> {
        host = true;
        dispose();
    };
}