package com;

import net.clientSide.Client;
import splashScreens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Board extends JPanel {
    private static Tile [] tiles = new Tile[64];

    public static int getWhoTurn() {
        return whoTurn;
    }

    private static int whoTurn = 1;
    private final static int whiteTurn = 1;
    private final static int blackTurn = 2;
    private final static int white = 1;
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

    public static Client getClient() {
        return client;
    }

    private static Client client;
    private static boolean host = false;

    private final static String [] pieceValues = {"blank", "whiteKing", "whiteQueen", "whiteBishop", "whiteKnight", "whiteRook", "whitePawn", "blackKing", "blackQueen", "blackBishop", "blackKnight", "blackRook", "blackPawn"};

    private static final Color green = new Color(115, 145, 34);
    private static final Color offWhite = new Color(232, 228, 214);

    private static boolean onlineGame = false;
    private static boolean wasGameOver = false;
    private static int myTeam = white;

    private static JFrame f = new JFrame("Chess");
    public static WaitingForOpponentScreen getWaitingForOpponentScreen(){
        return waitingForOpponentScreen;
    }
    private static Tile prevTile = new Tile(100, offWhite);
    private static WaitingForOpponentScreen waitingForOpponentScreen;
    private boolean online = false;
    private boolean wasCastle = false;
    private boolean wasPawnChange = false;
    public static Tile getTile(int i){
        return tiles[i];
    }
    private static Board board;

    public static GameOverScreen getGameOverScreen() {
        return gameOverScreen;
    }

    public static GameOverScreen gameOverScreen;

    public Board(){
        setLayout(new GridLayout(8, 8));
        addTiles();
    }
    public void addTiles(){
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
        System.out.println("whoTurn: " + whoTurn);
        System.out.println("myTurn: "  + myTeam);
        System.out.println("Is host: " + host);
        Tile clicked = (Tile) e.getSource();
        //if the player clicks on a tile that their piece is is on, highlight where they can move it
        ArrayList<Tile> moveOptions = clicked.moveOptions(tiles);
        //if the tile clicked belongs to whomever turn it is
        if((!onlineGame && clicked.getTeam()==whoTurn) || (onlineGame && clicked.getTeam() == myTeam && myTeam == whoTurn)){
            wasGameOver = false;
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
                checkPawnReachedEnd(clicked);
                if(!((wasCastle) || (wasPawnChange))){
                    if(onlineGame){
                        //send the move
                        System.out.println("Board is Sending move");
                        System.out.println(prevTile.getCoords() + ", " + clicked.getCoords());
                        System.out.println(prevTile.getValueAsString() + ", " + clicked.getValueAsString());
                        client.sendMove(new int [] {prevTile.getCoords(), clicked.getCoords()});
                        System.out.println("Board: Move sent");
                    }
                }
                checkRookKingMoves(clicked);
                wasCastle = false;
                wasPawnChange = false;
                //wipe all glowing tiles if they move
                for (Tile tile : tiles) {
                    if (tile.isGlowing()) {
                        tile.unLight();
                    }
                }
                //check for checkmate
                //only swap turns if it wasn't game over
                checkTest();
                System.out.println("check for mate: " + checkForMate());
                System.out.println("was game over? : " + wasGameOver);
                if(!wasGameOver){
                    System.out.println("end of users turn, swapping turns");
                    swapTurns();
                }
                wasGameOver = false;
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
    public static void setWasGameOver(boolean i){
        wasGameOver = i;
    }
    public static void swapTurns(){
        System.out.println("Board is swapping turns");
        if(whoTurn == whiteTurn){
            whoTurn = blackTurn;
        }
        else{
            whoTurn = whiteTurn;
        }
    }
    public static void checkTest(){
        System.out.println(checkForMate());
        if(checkForMate()){
            wasGameOver = true;
            String w = "stalemate";
            if (inCheckMate()) {
                w = "black_wins";
                //if it's white's turn, that means black is in checkmate. 0 = stalemate, 1 = white wins, 2 = black wins
                if(whoTurn == 1){
                    w = "white_wins";
                }
            }
            if((!onlineGame) || host){
                gameOverScreen = new GameOverScreen(w, true);
                System.out.println("Game over screen Host");
                gameOverScreen.activate();
                //reset everything
                if(gameOverScreen.getNewGame()){
                    System.out.println("Switching teams for new game");
                    //switch teams for the new game
                    if(myTeam == white){
                        myTeam = black;
                    }
                    else{
                        myTeam = white;
                    }
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
                    System.out.println("Board is sending new game from host");
                    client.sendNewGame();
                    System.out.println("whoTurn: " + whoTurn);
                    f.setVisible(true);
                }
                //kill the program, but only if the user specified it
                //I need this extra condition since OpponentLeft can also dispose of gameOverScreens.
                else if (gameOverScreen.getKillGame()){
                    System.out.println("Kill order from board");
                    kill();
                }
            }
            //if they're a guest user they don't have perms to start a new game
            else{
                gameOverScreen = new GameOverScreen(w, false);
                System.out.println("New Game over screen guest");
                gameOverScreen.activate();
                //after the above is closed, meaning if we get confirmation that the host wants a new game
                //this condition may seem irrelevant, but it actually makes sure the user hasn't become the host, ie. the host left
                if(!host){
                    launchGuest();
                }
            }
        }
    }
    public static void launchGuest(){
        if(myTeam == white){
            System.out.println("Setting guest from white to black");
            myTeam = black;
        }
        else{
            System.out.println("Setting guest from black to white");
            myTeam = white;
        }
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
        f.setVisible(true);
    }
    public static void kill(){
        f.dispose();
        if(onlineGame){
            client.kill();
        }
        System.out.println("Intentional crash");
        System.gc();
        System.exit(0);
    }
    private void checkIfCastle(Tile movedTo){
        //If we castled the white king to the right then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == whiteKing && !whiteKingHasMoved && movedTo.getCoords() == 62){
            tiles[63].setValue("blank");
            tiles[61].setValue("whiteRook");
            if(onlineGame){
                client.sendCastle(62);
            }
            wasCastle =  true;
        }
        //If we castled the white king to the left then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == whiteKing && !whiteKingHasMoved && movedTo.getCoords() == 58){
            tiles[56].setValue("blank");
            tiles[59].setValue("whiteRook");
            if(online){
                client.sendCastle(58);
            }
            wasCastle = true;
        }
        //If we castled the black king to the right then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == blackKing && !blackKingHasMoved && movedTo.getCoords() == 6){
            tiles[7].setValue("blank");
            tiles[5].setValue("blackRook");
            if(onlineGame){
                client.sendCastle(6);
            }
            wasCastle = true;
        }
        //If we castled the black king to the left then we need to manually move the rook. I don't have to include a rook boolean
        if(movedTo.getValue() == blackKing && !blackKingHasMoved && movedTo.getCoords() == 2){
            tiles[0].setValue("blank");
            tiles[3].setValue("blackRook");
            if(onlineGame){
                client.sendCastle(2);
            }
            wasCastle = true;
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
            if(onlineGame){
                client.sendPawnChange(prevTile.getCoords(), movedTo.getCoords(), movedTo.getValue());
            }
            wasPawnChange = true;
        }
    }
    public static boolean checkForMate(){
        for (Tile t : tiles) {
            //the turn hasn't flipped at this point
            if (t.getTeam() != whoTurn) {
                //if any pieces can move, then return false for checkmate
                if (!(t.moveOptions(tiles).isEmpty())) {
                    return false;
                }
            }
        }
        return true;
    }
    //this only runs once at then end so it isn't crucial to be efficient
    private static boolean inCheckMate(){
        int whoKing = whiteKing;
        if(whoTurn == white){
            whoKing = blackKing;
        }
        for(Tile t : tiles) {
            if (t.getTeam() == whoTurn) {
                for(Tile j: t.moveOptions(tiles)){
                    if(j.getValue() == whoKing){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static void setTileValues(int i){
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
    public static void startMenu(){
        if(onlineGame){
            client.kill();
        }
        if(f.isVisible()){
            f.setVisible(false);
        }
        onlineGame = false;
        whiteKingHasMoved = false;
        blackKingHasMoved = false;
        whiteRookLeftHasMoved = false;
        whiteRookRightHasMoved = false;
        blackRookLeftHasMoved = false;
        blackRookRightHasMoved = false;
        whoTurn = whiteTurn;
        System.out.println("back to start");
        GameStartScreen startMenu = new GameStartScreen();
        startMenu.activate();
        if(startMenu.getOnlineGame()){
            setUpOnlineGame();
        }
        if(host){
            f.setTitle("Host");
        }
        else{
            f.setTitle("Guest");
        }
        //disconnect from server before the program ends
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(onlineGame){
                    client.kill();
                }
            }
        });
        //make game
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Board());
        f.setBounds(500, 500, 500, 500);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }
    public static void startWaitForOpponent(){
        int tag = client.getTag();
        System.out.println("got tag" + tag);
        //tell client to start WaitForOpponent thread, which will wait for the opponent to join
        waitingForOpponentScreen = new WaitingForOpponentScreen(tag);
        client.waitForOpponent(waitingForOpponentScreen);
        f.setVisible(false);
        waitingForOpponentScreen.activate();
        System.out.println("waiting for opponent screen is visible?: " + waitingForOpponentScreen.isVisible());
        System.out.println("waiting for opponent screen is visible?: " + waitingForOpponentScreen.isAlwaysOnTop());
        System.out.println("Waiting for opponent screen's parent: " + waitingForOpponentScreen.getParent().getName());
        System.out.println("wait stopped");
        System.out.println(waitingForOpponentScreen.wasCancel());
        if(waitingForOpponentScreen.wasCancel()){
            onlineGame = false;
            startMenu();
        }
        else{
            f.setVisible(true);
            System.out.println("Opponent joined");
            waitingForOpponentScreen.dispose();
            client.startReading();
        }
    }
    public static void opponentLeft(){
        //if the host disconnects, make the guest the host of a new game
        host = true;
        myTeam = white;
        client.kill();
        client = new Client(board, true, -1);
        System.out.println("Resetting tiles..");
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
        //if there are any game over screens active, kill them
        if(gameOverScreen!= null){
            if(gameOverScreen.isVisible()){
                gameOverScreen.dispose();
            }
            if(gameOverScreen.getWaitForHostScreen() != null){
                if(gameOverScreen.getWaitForHostScreen().isVisible()){
                    gameOverScreen.getWaitForHostScreen().dispose();
                }
            }
        }
        OpponentLeftScreen opponentLeftScreen = new OpponentLeftScreen();
        opponentLeftScreen.activate();
    }
    public static void setUpOnlineGame(){
        JoinOrHostScreen joinOrHostScreen = new JoinOrHostScreen();
        joinOrHostScreen.activate();
        //set  up a client for the host
        if(joinOrHostScreen.isHost()){
            host = true;
            myTeam = white;
            client = new Client(board, true, -1);
            onlineGame = true;
            System.out.println("made client");
            System.out.println(client.getTag());
            startWaitForOpponent();
        }
        else{
            host = false;
            myTeam = black;
            int tag = joinOrHostScreen.getInput();
            client = new Client(board, false, tag);
            onlineGame = true;
            if(client.isGameFull()){
                JOptionPane.showMessageDialog(f, "The game you entered is already full!", "Error!", JOptionPane.ERROR_MESSAGE);
                //recur
                setUpOnlineGame();
            }
            else if(client.isInvalidKey()){
                JOptionPane.showMessageDialog(f, "The game you entered doesn't exist", "Error!", JOptionPane.ERROR_MESSAGE);
                //recur
                setUpOnlineGame();
            }
        }
    }
    public static void main(String[] args){
        //create a start menu
        startMenu();
    }
}
