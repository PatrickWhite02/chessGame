package com;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Tile extends JButton {
    public static Color glow = new Color(51, 165, 50);

    public static int blank = 0;
    public static int white = 1;
    public static int black = 2;
    public static int whiteKing = 1;
    public static int whiteQueen = 2;
    public static int whiteBishop = 3;
    public static int whiteKnight = 4;
    public static int whiteRook = 5;
    public static int whitePawn = 6;
    public static int blackKing = 7;
    public static int blackQueen = 8;
    public static int blackBishop = 9;
    public static int blackKnight = 10;
    public static int blackRook = 11;
    public static int blackPawn = 12;
    public static String[] values = {"blank","whiteKing","whiteQueen","whiteBishop","whiteKnight","whiteRook","whitePawn","blackKing","blackQueen","blackBishop","blackKnight","blackRook","blackPawn"};

    public int pieceType = 0;
    public int location = 0;
    public Color defaultColor;
    public boolean isGlowing = false;
    public int team = blank;
    public String valueAsString = "";

    public Tile(int i, Color c){
        location=i;
        defaultColor = c;
        setBackground(c);
    }
    public int getValue(){
        return pieceType;
    }
    public String getValueAsString(){
        return valueAsString;
    }
    public int getCoords(){
        return location;
    }
    public void light(){
        setBackground(glow);
        isGlowing = true;
    }
    public void unLight(){
        setBackground(defaultColor);
        isGlowing = false;
    }
    public int getTeam(){
        return team;
    }
    public boolean isGlowing() {
        return isGlowing;
    }

    public void setValue(String v){
        pieceType = indexOf(values, v);
        URL iconURL = Tile.class.getResource("/img/" + v + ".png");
        if(v.equals("blank")){
            setIcon(null);
        }
        else{
            ImageIcon icon = new ImageIcon(new ImageIcon(iconURL).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            setIcon(icon);
        }
        if(indexOf(values, v)<7 && indexOf(values, v)>0){
            team = white;
        }
        else if(indexOf(values, v)>0) {
            team = black;
        }
        else{
            team = blank;
        }
        valueAsString = v;
    }
    public static Integer indexOf(String[] ss, String s){
        for(int i=0; i<ss.length; i++){
            if(ss[i].equals(s)){
                return i;
            }
        }
        return 100;
    }
}
