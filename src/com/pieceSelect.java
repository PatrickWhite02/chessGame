package com;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class pieceSelect extends JDialog {
    public static int whiteTurn = 1;
    public static int blackTurn = 2;
    public static String[] fileNames = {"whiteQueen","whiteBishop","whiteKnight","whiteRook", "blackQueen","blackBishop","blackKnight","blackRook"};
    public JButton[] options = new JButton[4];
    public static JPanel p = new JPanel(new GridLayout(4, 1));

    public pieceSelect(JFrame f, int whoTurn){
        setBounds(MouseInfo.getPointerInfo().getLocation().getLocation().x, MouseInfo.getPointerInfo().getLocation().getLocation().y, 63, 250);
        setUndecorated(true);
        setModal(true);
        f.setLocationRelativeTo(null);
        add(p);
        if(whoTurn == whiteTurn){
            for(int i = 0; i < 4; i ++){
                options[i] = new JButton();
                URL iconURL = Tile.class.getResource("/img/" + fileNames[i] + ".png");
                ImageIcon icon = new ImageIcon(new ImageIcon(iconURL).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                options[i].setIcon(icon);
                p.add(options[i]);
                //System.out.println("/img/" + fileNames[i] + ".png");
            }
        }
        setVisible(true);
    }
}
