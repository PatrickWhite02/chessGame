package com;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class Tile extends JButton {
    private static final Color glow = new Color(51, 165, 50);
    private static final int blank = 0;
    private static final int white = 1;
    private static final int black = 2;
    private static final String[] values = {"blank","whiteKing","whiteQueen","whiteBishop","whiteKnight","whiteRook","whitePawn","blackKing","blackQueen","blackBishop","blackKnight","blackRook","blackPawn"};

    private int pieceType = 0;
    private final int location;
    private final Color defaultColor;
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
        if(v.equals("blank")){
            setIcon(null);
        }
        else{
            URL iconURL = Tile.class.getResource("/img/" + v + ".png");
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
    public void setValue(int i){
        setValue(values[i]);
    }
    private static Integer indexOf(String[] ss, String s){
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
            r.add(tiles[k]);
        }

        return r;
    }
}
