package com;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Tile extends JButton {
    public static Color glow = new Color(51, 165, 50);

    public static ArrayList<Integer> leftSide = new ArrayList<>(Arrays.asList(0,8,16,24,32,40,48,56));
    public static ArrayList<Integer> rightSide = new ArrayList<>(Arrays.asList(7, 15, 23, 31, 39, 47, 55, 63));

    public static int straight = 0;
    public static int right = 1;
    public static int left  = 2;

    public static int down = 1;
    public static int up = -1;
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
    //method to help the moveOptions method filter out disallowed moves
    //returns true if it successfully moves the piece
    //need to add functionality to stop it from checking pieces off the board
    public boolean moveOptionsFilter(Tile [] tiles, ArrayList<Integer> j, int moveDelta, int direction, boolean pawn){
        //if the requested piece isn't occupied, add it to the list
        //I have to put in a special case for a pawn since they can't take a piece directly in front of it
        if(moveDelta + location > 63 || moveDelta + location < 0){
            return false;
        }
        boolean r = false;
        if(!tiles[location + moveDelta].isOccupied() || (!pawn && tiles[location + moveDelta].getTeam()!=team)) {
            if (direction == straight) {
                j.add(location + moveDelta);
                r=true;
            } else if (direction == left && !rightSide.contains(location + moveDelta)) {
                j.add(location +  moveDelta);
                r=true;
            } else if (direction == right && !leftSide.contains(location + moveDelta)){
                j.add(location + moveDelta);
                r=true;
            }
            if(tiles[location + moveDelta].isOccupied()){
                return false;
            }
        }
        return r;
    }
    public void checkLeftDiagonal (Tile [] tiles, ArrayList<Integer> j){
        int tmp = location;
        if(!(leftSide.contains(tmp))){
            //bottom left Diagonal
            while(!leftSide.contains(tmp)){
                tmp = tmp + (7);
                if(!(tmp == location)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tiles, j, tmp-location, left, false))){
                        break;
                    }
                }
            }
        }
        tmp = location;
        if(!(leftSide.contains(tmp))){
            //bottom right diagonal
            while(!leftSide.contains(tmp)){
                tmp = tmp - (9);
                if(!(tmp == location)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tiles, j, tmp-location, left, false))){
                        break;
                    }
                }
            }
        }
    }
    public void checkRightDiagonal (Tile [] tiles, ArrayList<Integer> j){
        int tmp = location;
        if(!(rightSide.contains(tmp))){
            //bottom right diagonal
            while(!rightSide.contains(tmp)){
                tmp = tmp + (9);
                if(!(tmp == location)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tiles, j, tmp-location, right, false))){
                        break;
                    }
                }
            }
        }
        tmp = location;
        if(!(rightSide.contains(tmp))){
            //bottom right diagonal
            while(!rightSide.contains(tmp)){
                tmp = tmp - (7);
                if(!(tmp == location)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tiles, j, tmp-location, right, false))){
                        break;
                    }
                }
            }
        }
    }
    public void checkUpDown(Tile [] tiles, ArrayList<Integer> j){
        int tmp = location;
        //keep looping until we hit the top line
        while(tmp > 8){
            tmp = tmp - 8;
            if(!(moveOptionsFilter(tiles, j, tmp - location, straight, false))){
                break;
            }
        }
        tmp = location;
        //keep looping until we hit the bottom line
        while(tmp < 63){
            tmp = tmp + 8;
            if(!(moveOptionsFilter(tiles, j, tmp - location, straight, false))){
                break;
            }
        }
    }
    public ArrayList<Tile> moveOptions(Tile [] tiles){
        ArrayList<Integer> j  = new ArrayList<>();
        ArrayList<Tile> r = new ArrayList<>();
        //move options for a black pawn
        if(pieceType==blackPawn){
            //move straight
            moveOptionsFilter(tiles, j, 8, straight, true);
            //if the pawn is still in it's starting position, it can move forward 2
            if(location<16){
                moveOptionsFilter(tiles, j, 16, straight, true);
            }
            //move diagonal to the left
            if(tiles[location + 7].getTeam()!=blank && tiles[location +7].getTeam()!=team){
                moveOptionsFilter(tiles, j, 7, left, false);
            }
            //move diagonal to the right
            if(tiles[location + 9].getTeam()!=blank && tiles[location +9].getTeam()!=team){
                moveOptionsFilter(tiles, j, 9, left, false);
            }
        }
        //move options for a white pawn
        if(pieceType==whitePawn){
            //move straight
            moveOptionsFilter(tiles, j, -8, straight, true);
            //if the pawn is still in it's starting position, it can move forward 2
            if(location>47){
                moveOptionsFilter(tiles, j, -16, straight, true);
            }
            //if the pawn can diagonally take a piece of the other player, then do so
            //diagonal to the left
            if(tiles[location - 9].getTeam()!=blank && tiles[location - 9].getTeam()!=team){
                moveOptionsFilter(tiles, j, -9, right, false);
            }
            //diagonal to the right. I inverted the directions because technically the pawns are facing the opposite ways lol
            if(tiles[location - 7].getTeam()!=blank && tiles[location - 7].getTeam()!=team){
                moveOptionsFilter(tiles, j, -7, left, false);
            }
        }
        //move options for knights
        if(pieceType == blackKnight || pieceType == whiteKnight){
            //left horizontal L
            j.add(location + 6);
            //right horizontal L
            j.add(location + 10);
            //left vertical L
            j.add(location + 15);
            //right vertical L
            j.add(location + 17);
        }
        //move options for bishops
        if(pieceType == blackBishop || pieceType == whiteBishop){
            checkLeftDiagonal(tiles, j);
            checkRightDiagonal(tiles, j);
        }
        //move option for rooks
        if(pieceType == blackRook || pieceType == whiteRook){
            checkUpDown(tiles, j);
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
