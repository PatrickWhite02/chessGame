package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel {
    private static Tile [] tiles = new Tile[64];

    private int whoTurn = 1;
    private final static int whiteTurn = 1;
    private final static int blackTurn = 2;
    private final static int black = 2;
    private final static int whiteKing = 1;
    private final static int whiteRook = 5;
    private final static int whitePawn = 6;
    private final static int blackKing = 7;
    private final static int blackRook = 11;
    private final static int blackPawn = 12;

    private static boolean whiteKingHasMoved = false;
    private static boolean blackKingHasMoved = false;
    private static boolean whiteRookLeftHasMoved = false;
    private static boolean whiteRookRightHasMoved = false;
    private static boolean blackRookLeftHasMoved = false;
    private static boolean blackRookRightHasMoved = false;
    public static boolean getWhiteKingHasMoved(){
        return whiteKingHasMoved;
    }
    public static boolean getWhiteRookLeftHasMoved(){
        return whiteRookLeftHasMoved;
    }
    public static boolean getWhiteRookRightHasMoved(){
        return whiteRookRightHasMoved;
    }
    public static boolean getBlackKingHasMoved(){
        return blackKingHasMoved;
    }
    public static boolean getBlackRookLeftHasMoved(){
        return blackRookLeftHasMoved;
    }
    public static boolean getBlackRookRightHasMoved(){
        return blackRookRightHasMoved;
    }

    private final static String [] pieceValues = {"blank", "whiteKing", "whiteQueen", "whiteBishop", "whiteKnight", "whiteRook", "whitePawn", "blackKing", "blackQueen", "blackBishop", "blackKnight", "blackRook", "blackPawn"};

    private static final Color green = new Color(115, 145, 34);
    private static final Color offWhite = new Color(232, 228, 214);

    private static boolean onlineGame = false;

    private static JFrame f= new JFrame("Chess");

    private static Tile prevTile = new Tile(100, offWhite);

    private boolean online = false;

    public Board(){
        setLayout(new GridLayout(8, 8));
        addTiles();
    }
    private void addTiles(){
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
    private ActionListener tileListener = e -> {
        Tile clicked = (Tile) e.getSource();
        //if the player clicks on a tile that their piece is is on, highlight where they can move it
        ArrayList<Tile> moveOptions = clicked.moveOptions(tiles);
        if(clicked.getTeam()==whoTurn){
            for(Tile s:moveOptions){
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
                checkPawnReachedEnd(clicked);
                //swap turns
                if(whoTurn == blackTurn){
                    whoTurn = whiteTurn;
                }
                else{
                    whoTurn = blackTurn;
                }
                //wipe all glowing tiles if they move
                for (Tile tile : tiles) {
                    if (tile.isGlowing()) {
                        tile.unLight();
                    }
                }
                //check for checkmate
                try {
                    if(checkForMate()){
                        int w = 0;
                        if (inCheckMate()) {
                            w = 1;
                            //our turn has already flipped, so if it's white's turn, that means white is in checkmate. 0 = stalemate, 1 = white wins, 2 = black wins
                            if(whoTurn == 1){
                                w = 2;
                            }
                        }
                        gameOverScreen gameOverScreen = new gameOverScreen(f, w);
                        //reset everything
                        if(gameOverScreen.getNewGame()){
                            for(int i =0; i < 64; i++){
                                setTileValues(i);
                            }
                            whiteKingHasMoved = false;
                            blackKingHasMoved = false;
                            whiteRookLeftHasMoved = false;
                            whiteRookRightHasMoved = false;
                            blackRookLeftHasMoved = false;
                            blackRookRightHasMoved = false;
                            whoTurn = whiteTurn;
                        }
                        //kill the program
                        else{
                            f.dispose();
                            System.gc();
                            System.exit(0);
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        //if there is a tile that's glowing but isn't in the list of possible move options, make it stop glowing. This is for wiping the list after a new click
        for (Tile tile : tiles) {
            if (tile.isGlowing() && !(moveOptions.contains(tile))) {
                tile.unLight();
            }
        }
        //store the recently clicked tile, so that we can move it on the next click
        prevTile = clicked;
    };
    private void checkIfCastle(Tile movedTo){
        //If we castled the white king to the right then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == whiteKing && !whiteKingHasMoved && movedTo.getCoords() == 62){
            tiles[63].setValue("blank");
            tiles[61].setValue("whiteRook");
        }
        //If we castled the white king to the left then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == whiteKing && !whiteKingHasMoved && movedTo.getCoords() == 58){
            tiles[56].setValue("blank");
            tiles[59].setValue("whiteRook");
        }
        //If we castled the black king to the right then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == blackKing && !blackKingHasMoved && movedTo.getCoords() == 6){
            tiles[7].setValue("blank");
            tiles[5].setValue("blackRook");
        }
        //If we castled the black king to the left then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == blackKing && !blackKingHasMoved && movedTo.getCoords() == 2){
            tiles[0].setValue("blank");
            tiles[3].setValue("blackRook");
        }
    }
    private void checkRookKingMoves(Tile movedTo){
        //I have to keep track if a rook or a king has moved yet for castling
        if(movedTo.getValue() == whiteKing){
            whiteKingHasMoved = true;
        }
        if(movedTo.getValue() == blackKing){
            blackKingHasMoved = true;
        }
        if(movedTo.getValue() == whiteRook && prevTile.getCoords() == 56){
            whiteRookLeftHasMoved =true;
        }
        if(movedTo.getValue() == whiteRook && prevTile.getCoords() == 63){
            whiteRookRightHasMoved = true;
        }
        if(movedTo.getValue() == blackRook && prevTile.getCoords() == 0){
            blackRookLeftHasMoved = true;
        }
        if(movedTo.getValue() == blackRook && prevTile.getCoords() == 7){
            blackRookRightHasMoved = true;
        }
    }
    private void checkPawnReachedEnd(Tile movedTo){
        if(movedTo.getValue() == blackPawn && movedTo.getCoords() > 55 || movedTo.getValue() == whitePawn && movedTo.getCoords() < 8){
            pieceSelect selectPawnReplacement = new pieceSelect(f, whoTurn, movedTo.getColor());
            movedTo.setValue(pieceValues[selectPawnReplacement.getSelection()]);
        }
    }
    private boolean checkForMate() throws IOException {
        for (Tile t : tiles) {
            //the turn has already flipped at this point, so really the below is checking if the other team is in mate
            if (t.getTeam() == whoTurn) {
                //if any pieces can move, then return false for checkmate
                if (!(t.moveOptions(tiles).isEmpty())) {
                    return false;
                }
            }
        }
        return true;
    }
    //this only runs once at then end so it isn't crucial to be efficient
    private boolean inCheckMate(){
        int whoKing = whiteKing;
        if(whoTurn == black){
            whoKing = blackKing;
        }
        for(Tile t : tiles) {
            if (t.getTeam() != whoTurn) {
                for(Tile j: t.moveOptions(tiles)){
                    if(j.getValue() == whoKing){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void setTileValues(int i){
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
    public static void main(String[] args) throws IOException {
        //create a start menu with an exit button
        GameStartScreen startMenu = new GameStartScreen(f, 3);
        if(startMenu.getOnlineGame()){
            onlineGame = true;
        }
        //make game
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Board());
        f.setBounds(500, 500, 500, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }
}
