package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class pieceSelect extends JDialog {
    private int whoTurn;
    private int selection = -1;
    private static final int whiteQueen = 2;
    private static final int whiteBishop = 3;
    private static final int whiteKnight = 4;
    private static final int whiteRook = 5;
    private static final int whiteTurn = 1;
    private static final int blackTurn = 2;
    private static final String[] fileNames = {"whiteQueen","whiteBishop","whiteKnight","whiteRook", "blackQueen","blackBishop","blackKnight","blackRook"};
    private ImageIcon[] icons = new ImageIcon[8];
    public JButton[] options = new JButton[4];
    public JPanel p = new JPanel(new GridLayout(4, 1));

    public pieceSelect(JFrame f, int whosTurn, Color c){
        whoTurn = whosTurn;
        setUpIconArray();
        setBounds(MouseInfo.getPointerInfo().getLocation().getLocation().x, MouseInfo.getPointerInfo().getLocation().getLocation().y, 50, 200);
        setUndecorated(true);
        setModal(true);
        add(p);
            for(int i = 0; i < 4; i ++){
                options[i] = new JButton();
                //single line if statement, left of colon is if, right is else. Increase the working index by 4 if the pieces we're choosing from are black
                int x = ((whoTurn == whiteTurn) ? i:i+4);
                options[i].setIcon(icons[x]);
                options[i].setBackground(c);
                options[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                options[i].addActionListener(buttonListener);
                p.add(options[i]);
            }
        setVisible(true);
    }
    private void setUpIconArray(){
        for(int i = 0; i < icons.length; i++){
            URL iconURL = Tile.class.getResource("/img/" + fileNames[i] + ".png");
            icons[i] = new ImageIcon((new ImageIcon(iconURL).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        }
    }
    private int indexOfButton(JButton[] bs, JButton b){
        for(int i = 0; i < bs.length; i++){
            if(bs[i] == b){
                return i;
            }
        }
        return -1;
    }
    private ActionListener buttonListener = e -> {
        JButton clicked = (JButton) e.getSource();
        int v = indexOfButton(options, clicked);
        switch(v){
            case 0: selection = whiteQueen;
            break;
            case 1: selection = whiteBishop;
            break;
            case 2: selection = whiteKnight;
            break;
            case 3: selection = whiteRook;
            break;
        }
        //if it's black's turn then we need to increase the above
        if(whoTurn == blackTurn){
            selection = selection + 6;
        }
        dispose();
    };
    public int getSelection(){
        return selection;
    }
}
