package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel {
    public static Tile [] tiles = new Tile[64];

    public static int whoTurn = 2;
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

    public Tile prevTile = new Tile(100, new Color(115, 145, 34));
    public Board(){
        setLayout(new GridLayout(8, 8));
        initializeBoard();
    }
    public void initializeBoard(){
        for(int i=0; i<64; i++){
            if((i%2==0 && i<8) || (i>=8 && i<16 && i%2!=0) || (i>=16 && i<24 && i%2==0) || (i>=24 && i<32 && i%2!=0) || (i>=32 && i<40 && i%2==0) || (i>=40 && i<48 && i%2!=0) || (i>=48 && i<56 && i%2==0) || (i>=56 && i%2!=0)){
                tiles[i] = new Tile(i, new Color(115, 145, 34));
            }
            else{
                tiles[i] = new Tile(i, new Color(232, 228, 214));
            }
            setTileValues(i);
            tiles[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Tile clicked = (Tile) e.getSource();
                    if(clicked.getTeam()==whoTurn){
                        for(Tile s:moveOptions(clicked)){
                            s.light();
                        }
                    }
                    else{
                        if(clicked.isGlowing()){
                            clicked.setValue(prevTile.getValueAsString());
                            prevTile.setValue("blank");
                        }
                    }
                    for(int i = 0; i<tiles.length; i++){
                        if(tiles[i].isGlowing && !(tiles[i]==(clicked))){
                            tiles[i].unLight();
                            //if there is a tile selected that belongs to the person whose turn it is, then they're trying to pick where to move
                        }
                    }
                    prevTile = clicked;
                }
            });
            add(tiles[i]);
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
            tiles[i].setValue("whiteQueen");
        }
        if(i == 60){
            tiles[i].setValue("whiteKing");
        }
        if(i > 47 && i < 56){
            tiles[i].setValue("whitePawn");
        }
    }
    public ArrayList<Tile> moveOptions(Tile t){
        ArrayList<Integer> j  = new ArrayList<Integer>();
        ArrayList<Tile> r = new ArrayList<Tile>();
        if(t.getValue()==blackPawn){
            j.add(t.getCoords() + 8);
        }
        for(int k : j){
            for(int i =0; i< tiles.length; i++){
                if(tiles[i].getCoords()==k){
                    r.add(tiles[i]);
                }
            }
        }
        return r;
    }
    public static void main(String[] args) {
        JFrame f= new JFrame("Chess");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Board());
        for(int i =0; i<tiles.length; i++){
            System.out.println(tiles[i].getValue());
        }
        f.setBounds(500, 500, 500, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }
}
