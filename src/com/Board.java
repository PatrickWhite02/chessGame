package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel {
    public static Tile [] tiles = new Tile[64];

    public static int whoTurn = 1;
    public static int whiteTurn = 1;
    public static int blackTurn = 2;
    public static int blank = 0;
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

    public static final Color green = new Color(115, 145, 34);
    public static final Color offWhite = new Color(232, 228, 214);

    public static JFrame f= new JFrame("Chess");

    public Tile prevTile = new Tile(100, offWhite);
    public Board(){
        setLayout(new GridLayout(8, 8));
        initializeBoard();
    }
    public void initializeBoard(){
        for(int i=0; i<64; i++){
            if((i%2==0 && i<8) || (i>=8 && i<16 && i%2!=0) || (i>=16 && i<24 && i%2==0) || (i>=24 && i<32 && i%2!=0) || (i>=32 && i<40 && i%2==0) || (i>=40 && i<48 && i%2!=0) || (i>=48 && i<56 && i%2==0) || (i>=56 && i%2!=0)){
                tiles[i] = new Tile(i,offWhite);
            }
            else{
                tiles[i] = new Tile(i, green);
            }
            setTileValues(i);
            tiles[i].addActionListener(tileListener);
            add(tiles[i]);
        }
    }
    public ActionListener tileListener = e -> {
        Tile clicked = (Tile) e.getSource();
        //if the player clicks on a tile that their piece is is on, highlight where they can move it
        if(clicked.getTeam()==whoTurn){
            for(Tile s:clicked.moveOptions(tiles)){
                s.light();
            }
        }
        //if they pick a tile that their piece isn't on, move the previously selected tile value to that square, if it has been highlighted by the above
        else{
            if(clicked.isGlowing()){
                clicked.setValue(prevTile.getValueAsString());
                prevTile.setValue("blank");
                checkIfCastle(clicked);
                checkRookKingMoves(clicked);
                checkPawnReachedEnd(clicked, whoTurn);
                //swap turns
                if(whoTurn == blackTurn){
                    whoTurn = whiteTurn;
                }
                else{
                    whoTurn = blackTurn;
                }
                //wipe all glowing tiles if they move
                for (Tile tile : tiles) {
                    if (tile.isGlowing) {
                        tile.unLight();
                    }
                }
            }
        }
        //if there is a tile that's glowing but isn't in the list of possible move options, make it stop glowing. This is for wiping the list after a new click
        for (Tile tile : tiles) {
            if (tile.isGlowing && !(clicked.moveOptions(tiles).contains(tile))) {
                tile.unLight();
            }
        }
        //store the recently clicked tile, so that we can move it on the next click
        prevTile = clicked;
    };
    public void checkIfCastle(Tile movedTo){
        //If we castled the white king to the right then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == whiteKing && !tiles[0].getWhiteKingHasMoved() && movedTo.getCoords() == 61){
            tiles[63].setValue("blank");
            tiles[60].setValue("whiteRook");
        }
        //If we castled the white king to the left then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == whiteKing && !tiles[0].getWhiteKingHasMoved() && movedTo.getCoords() == 57){
            tiles[56].setValue("blank");
            tiles[58].setValue("whiteRook");
        }
        //If we castled the black king to the right then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == blackKing && !tiles[0].getBlackKingHasMoved() && movedTo.getCoords() == 6){
            tiles[7].setValue("blank");
            tiles[5].setValue("blackRook");
        }
        //If we castled the black king to the left then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == blackKing && !tiles[0].getBlackKingHasMoved() && movedTo.getCoords() == 2){
            tiles[0].setValue("blank");
            tiles[3].setValue("blackRook");
        }
    }
    public void checkRookKingMoves(Tile movedTo){
        //I have to keep track if a rook or a king has moved yet for castling
        if(movedTo.getValue() == whiteKing){
            tiles[0].setWhiteKingHasMoved();
        }
        if(movedTo.getValue() == blackKing){
            tiles[0].setBlackKingHasMoved();
        }
        if(movedTo.getValue() == whiteRook && prevTile.getCoords() == 56){
            tiles[0].setWhiteRookLeftHasMoved();
        }
        if(movedTo.getValue() == whiteRook && prevTile.getCoords() == 63){
            tiles[0].setWhiteRookRightHasMoved();
        }
        if(movedTo.getValue() == blackRook && prevTile.getCoords() == 0){
            tiles[0].setBlackRookLeftHasMoved();
        }
        if(movedTo.getValue() == blackRook && prevTile.getCoords() == 7){
            tiles[0].setBlackRookRightHasMoved();
        }
    }
    public void checkPawnReachedEnd(Tile movedTo, int whoTurn){
        if(movedTo.getValue() == blackPawn && movedTo.getCoords() > 55){
            pieceSelect selectPawnReplacement = new pieceSelect(f, whoTurn, offWhite);
            System.out.println("yeet");
        }
    }
    public void setTileValues(int i){
        tiles[i].setValue("blank");
        if(i == 0 || i ==7){
            tiles[i].setValue("blackRook");
        }
        if(i == 1 || i == 6){
            tiles[i].setValue("blackKnight");
        }
        if(i == 2 || i == 5){
            tiles[i].setValue("blackBishop");
        }
        if(i == 3){
            tiles[i].setValue("blackQueen");
        }
        if(i == 4){
            tiles[i].setValue("blackKing");
        }
        if(i > 7 && i < 16){
            tiles[i].setValue("blackPawn");
        }
        if(i == 56 || i == 63){
            tiles[i].setValue("whiteRook");
        }
        if(i == 57 || i == 62){
            tiles[i].setValue("whiteKnight");
        }
        if(i == 58 || i == 61){
            tiles[i].setValue("whiteBishop");
        }
        if(i == 59){
            tiles[i].setValue("whiteKing");
        }
        if(i == 60){
            tiles[i].setValue("whiteQueen");
        }
        if(i > 47 && i < 56){
            tiles[i].setValue("whitePawn");
        }
    }
    public static void main(String[] args) {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Board());
        f.setBounds(500, 500, 500, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        pieceSelect selectPawnReplacement = new pieceSelect(f, blackTurn, offWhite);
        System.out.println(selectPawnReplacement.getSelection());
    }
}
