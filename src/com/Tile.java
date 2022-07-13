package com;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

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
    public int location;
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
    public boolean isOccupied(){
        return !(pieceType==blank);
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
    public ArrayList<Tile> moveOptions(Tile [] tiles){
        ArrayList<Integer> j  = new ArrayList<>();
        ArrayList<Tile> r = new ArrayList<>();
        //move options for a black pawn
        if(pieceType==blackPawn){
            //you can always move a pawn forward unless that piece is occupied, the game won't let you go off the map
            if(!tiles[location + 8].isOccupied()){
                j.add(location + 8);
            }
            //if the pawn is still in it's starting position, it can move forward 2
            if(location<16){
                j.add(location + 16);
            }
            //if the pawn can diagonally take a piece of the other player, then do so
            //left diagonal
            if(Board.tiles[location + 7].isOccupied() && Board.tiles[location + 7].getTeam() != team && !Arrays.asList(7,15,23,31,39,47,55,63).contains(location + 7)){
                j.add(location + 7);
            }
            if(Board.tiles[location + 9].isOccupied() && Board.tiles[location + 9].getTeam() != team && !Arrays.asList(0,8,16,24,32,40,48,56).contains(location + 9)){
                j.add(location + 9);
            }
        }
        //move options for knights
        if(pieceType == blackKing || pieceType == whiteKnight){

        }
        //move options for a white pawn
        if(pieceType==whitePawn){
            if(!tiles[location - 8].isOccupied()){
                j.add(location - 8);
            }
            //if the pawn is still in it's starting position, it can move forward 2
            if(location>47){
                j.add(location - 16);
            }
            //if the pawn can diagonally take a piece of the other player, then do so
            //diagonal to the right
            if(Board.tiles[location - 7].isOccupied() && Board.tiles[location - 7].getTeam() != team && !Arrays.asList(0,8,16,24,32,40,48,56).contains(location - 7)){
                j.add(location - 7);
            }
            //diagonal to the left
            if(Board.tiles[location - 9].isOccupied() && Board.tiles[location - 9].getTeam() != team && !Arrays.asList(7,15,23,31,39,47,55,63).contains(location - 9)){
                j.add(location - 9);
            }
        }
        for(int k : j){
            for (Tile tile : tiles) {
                if (tile.getCoords() == k) {
                    r.add(tile);
                }
            }
        }
        return r;
    }
}
