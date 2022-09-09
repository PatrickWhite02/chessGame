package net.clientSide;

import com.Board;
import splashScreens.WaitingForOpponentScreen;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// constructor: 5 digit identifier. For now I'm just gonna make it so only one game can happen at a time
// .sendMove(int from, int to) -> sends move
// .receiveMove() -> waits for opponent to make move, then returns int [] {from, to}
public class Client {
    private String host;
    private int port;

    private boolean isLeader;
    public boolean getHost(){
        return isLeader;
    }
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private BufferedReader reader;
    private PrintWriter writer;
    private int[] move;
    private int tag;
    private boolean hasOpponent = false;
    public void setTag(int tag) {this.tag = tag;}
    public int getTag(){ return this.tag;}
    public void setHasOpponent(boolean hasOpponent){
        this.hasOpponent = hasOpponent;
    }
    private ReadThread readThread;

    public Client(boolean leader){
        isLeader = leader;
        host = "164.90.253.22";
        port = 8282;
        try{
            socket = new Socket(host, port);
            output = socket.getOutputStream();
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public int joinGame(int tag){
        this.tag = tag;
        //lets the server know to expect input from a joiner, not a host
        writer.println("j");
        writer.println(tag);
        try {
            int response = Integer.parseInt(reader.readLine());
            //if connection was successful
            if(response == 0){
                startReading();
            }
            return response;
        } catch (IOException e) {
            //shouldnt ever hit
            return -1;
        }
    }
    public int makeGame(){
        tag = generateKey();
        return tag;
    }
    private int generateKey(){
        for(int i = 1000; i < 10000; i++){
            writer.println(i);
            try {
                //error code for key is taken
                if(!reader.readLine().equals("0")){
                    return i;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //I don't think I'll ever have this many people playing lol
        return -1;
    }
    public void waitForOpponent(WaitingForOpponentScreen waitingForOpponentScreen){
        HasOpponentThread hasOpponentThread = new HasOpponentThread(waitingForOpponentScreen, socket, this);
        hasOpponentThread.start();
        System.out.println("started thread");
    }
    public void startReading(){
        System.out.println("Starting readThread");
        readThread = new ReadThread(socket, this);
        readThread.start();
        System.out.println("Started reading");
    }
    public void sendMove(int[] ints){
        move = ints;
        //send the move
        WriteThread writeThread = new WriteThread(socket, this);
        writeThread.setMove(move);
        writeThread.start();
        //wait for this thread to end
        try {
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void sendNewGame(){
        System.out.println("Client is sending new game from host");
        WriteThread writeThread = new WriteThread(socket, this);
        writeThread.sendNewGame();
    }
    public void sendCastle(int location){
        WriteThread writeThread = new WriteThread(socket, this);
        writeThread.sendCastle(location);
    }
    public void sendPawnChange(int pawnOldLocation, int pawnLocation, int pieceValue){
        WriteThread writeThread = new WriteThread(socket, this);
        writeThread.sendPawnChange(pawnOldLocation, pawnLocation, pieceValue);
    }
    void setMove(int [] move){
        this.move = move;
    }
    public void kill(){
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            System.out.println("Terminate");
            //terminate
            writer.println("T");
            System.out.println("Sent terminate");
            System.out.println(writer);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
