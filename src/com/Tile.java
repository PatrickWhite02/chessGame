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

    public static boolean whiteKingHasMoved = false;
    public static boolean blackKingHasMoved = false;
    public static boolean whiteRookLeftHasMoved = false;
    public static boolean whiteRookRightHasMoved = false;
    public static boolean blackRookLeftHasMoved = false;
    public static boolean blackRookRightHasMoved = false;

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
    public void setWhiteKingHasMoved(){
        whiteKingHasMoved = true;
    }
    public void setWhiteRookLeftHasMoved(){
        whiteRookLeftHasMoved = true;
    }
    public void setWhiteRookRightHasMoved(){
        whiteRookRightHasMoved = true;
    }
    public void setBlackKingHasMoved(){
        blackKingHasMoved = true;
    }
    public void setBlackRookLeftHasMoved(){
        blackRookLeftHasMoved = true;
    }
    public void setBlackRookRightHasMoved(){
        blackRookRightHasMoved = true;
    }
    public boolean getWhiteKingHasMoved(){
        return whiteKingHasMoved;
    }
    public boolean getBlackKingHasMoved(){
        return blackKingHasMoved;
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
            }
            else if (direction == left && !rightSide.contains(location + moveDelta)) {
                j.add(location +  moveDelta);
                r=true;
            } else if (direction == right && !leftSide.contains(location + moveDelta) && !leftSide.contains(location + moveDelta - 1)){
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
    public void checkLeftRight(Tile [] tiles, ArrayList<Integer> j){
        int tmp = location;
        //keep looping until we hit the right side
        while(!(rightSide.contains(tmp))){
            tmp = tmp + 1;
            if(!(moveOptionsFilter(tiles, j, tmp - location, straight, false))){
                break;
            }
        }
        tmp = location;
        //keep looping until we hit the bottom line
        while(!(leftSide.contains(tmp))){
            tmp = tmp - 1;
            if(!(moveOptionsFilter(tiles, j, tmp - location, straight, false))){
                break;
            }
        }
    }
    public void checkKingMoves(Tile [] tiles, ArrayList<Integer> j){
        //move up
        moveOptionsFilter(tiles, j, -8, straight, false);
        //move down
        moveOptionsFilter(tiles, j, 8, straight, false);
        //move left
        if(!leftSide.contains(location)){
            moveOptionsFilter(tiles, j, -1, straight, false);
            //move top left
            moveOptionsFilter(tiles, j, -9, straight, false);
            //move bottom left
            moveOptionsFilter(tiles, j, 7, straight, false);
        }
        //move right
        if(!rightSide.contains(location)){
            moveOptionsFilter(tiles, j, 1, straight, false);
            //move top right
            moveOptionsFilter(tiles, j, -7, straight, false);
            //move bottom right
            moveOptionsFilter(tiles, j, 9, straight, false);
        }
        //black castling
        if(pieceType == whiteKing && !whiteKingHasMoved){
            if(!whiteRookRightHasMoved){
                if(tiles[62].getValue() == blank && tiles[61].getValue() == blank && tiles[60].getValue() == blank){
                    //castle black to the right
                    j.add(62);
                }
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
        //move option for rooks
        if(pieceType == blackRook || pieceType == whiteRook){
            checkUpDown(tiles, j);
            checkLeftRight(tiles, j);
        }
        //move options for knights
        if(pieceType == blackKnight || pieceType == whiteKnight){
             //Check to make sure we aren't one away from the edge or on the edge
             if(!leftSide.contains(location - 1) && !leftSide.contains(location)){
                 //bottom left horizontal L.
                 moveOptionsFilter(tiles, j, 6, left, false);
                 //top left horizontal L
                 moveOptionsFilter(tiles, j, -10, left, false);
             }
             //bottom left vertical L
             moveOptionsFilter(tiles, j,  15, left, false);
             //top left vertical L
             moveOptionsFilter(tiles, j, -17, left, false);
             //check to make sure we aren't one away from the edge or on the edge
             if(!rightSide.contains(location + 1) && !rightSide.contains(location)){
                 //bottom right horizontal L
                 moveOptionsFilter(tiles, j, 10, right, false);
                 //top right horizontal L
                 moveOptionsFilter(tiles, j, -6, right, false);
             }
            //bottom right vertical L
            moveOptionsFilter(tiles, j,  17, right, false);
            //top right vertical L
            moveOptionsFilter(tiles, j, -15, right, false);
        }
        //move options for bishops
        if(pieceType == blackBishop || pieceType == whiteBishop){
            checkLeftDiagonal(tiles, j);
            checkRightDiagonal(tiles, j);
        }
        //move option for queens
        if(pieceType == blackQueen || pieceType == whiteQueen){
            checkUpDown(tiles, j);
            checkLeftRight(tiles, j);
            checkRightDiagonal(tiles, j);
            checkLeftDiagonal(tiles, j);
        }
        //move option for kings. Need to add check functionality
        if(pieceType == blackKing || pieceType == whiteKing){
            checkKingMoves(tiles, j);
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
