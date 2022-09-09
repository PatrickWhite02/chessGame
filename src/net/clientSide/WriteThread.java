package net.clientSide;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread{
    private PrintWriter writer;
    private Socket socket;
    private Client client;
    private boolean keepRunning = true;
    private int [] move;
    public WriteThread(Socket socket, Client client){
        this.socket = socket;
        this.client = client;
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void sendNewGame(){
        System.out.println("Writer wrote new game from host");
        //host wants new game
        writer.println("HWNG");
    }
    public void setMove(int [] move){
        this.move = move;
    }
    public void sendCastle(int newKingLocation){
        //this flags ReadThread to receive the new data stream as a castle, not a typical move
        writer.println("c");
        writer.println(newKingLocation);
    }
    public void sendPawnChange(int pawnOldLocation, int pawnNewLocation, int pieceValue){
        //this flags ReadThread to receive the new data stream as a pawn change, not a typical move
        writer.println("p");
        String pawnString = pawnOldLocation + " " + pawnNewLocation + " " + pieceValue;
        writer.println(pawnString);
    }
    public void run(){
        String moveString = move[0] + " " + move[1];
        writer.println(moveString);
        //insert line here about closing the socket once the user leaves
    }
}
