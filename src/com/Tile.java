package com;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Tile extends JButton {
    private static Color glow = new Color(51, 165, 50);
    private static int blank = 0;
    private static int white = 1;
    private static int black = 2;
    private static String[] values = {"blank","whiteKing","whiteQueen","whiteBishop","whiteKnight","whiteRook","whitePawn","blackKing","blackQueen","blackBishop","blackKnight","blackRook","blackPawn"};


    private int pieceType = 0;
    private int location;
    private Color defaultColor;
    private boolean isGlowing = false;
    private int team = blank;
    private String valueAsString = "";


    public Tile(int i, Color c){
        location=i;
        defaultColor = c;
        setBackground(c);
    }
    public void light(){
        setBackground(glow);
        isGlowing = true;
    }
    public void unLight(){
        setBackground(defaultColor);
        isGlowing = false;
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
    public int getTeam(){
        return team;
    }
    public boolean isGlowing() {
        return isGlowing;
    }
    public boolean isOccupied(){
        return !(pieceType==blank);
    }
    public Color getColor(){
        return defaultColor;
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
        return -1;
    }
    //method to help the moveOptions method filter out disallowed moves
    //returns true if it successfully moves the piece
    //need to add functionality to stop it from checking pieces off the board

    public ArrayList<Tile> moveOptions(Tile [] tiles){
        ArrayList<Tile> r = new ArrayList<>();
        moveOptions moveOptions = new moveOptions(tiles, this);
        for(int k : moveOptions){
            System.out.println("Tile is registering tile #" + k + " as a move option for" + this.getValueAsString());
            r.add(tiles[k]);
        }
        return r;
    }
}
