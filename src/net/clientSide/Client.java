package net.clientSide;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

// constructor: 5 digit identifier. For now I'm just gonna make it so only one game can happen at a time
// .sendMove(int from, int to) -> sends move
// .receiveMove() -> waits for opponent to make move, then returns int [] {from, to}
public class Client {
    private String host;
    private int port;
    private boolean isLeader;
    private Socket socket;
    private int[] move;
    private int tag;
    public void setTag(int tag) {this.tag = tag;}
    public int getTag(){ return this.tag;}

    public void setGameFull(boolean gameFull) {
        this.gameFull = gameFull;
    }

    public boolean gameFull;
    public boolean isGameFull(){
        return gameFull;
    }
    public boolean isInvalidKey() {
        return invalidKey;
    }

    public void setInvalidKey(boolean invalidKey) {
        this.invalidKey = invalidKey;
    }
    private boolean invalidKey;

    public Client(boolean leader, int tag){
        isLeader = leader;
        host = "localhost";
        port = 8282;
        this.tag = tag;
        try{
            socket = new Socket(host, port);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        joinGame(tag);
    }
    public boolean joinGame(int tag){
        invalidKey = false;
        gameFull = false;
        JoinGameThread joinGameThread = new JoinGameThread(socket, this, tag, isLeader);
        joinGameThread.start();
        try {
            joinGameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //return if I was successful
        System.out.println(invalidKey);
        return(!invalidKey);
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
    void setMove(int [] move){
        this.move = move;
    }
    public int [] receiveMove(){
        ReadThread readThread = new ReadThread(socket, this);
        readThread.start();
        //waits for readThread to terminate
        try{
            readThread.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        return move;
    }
    public void kill(){
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println("Terminate");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client client = new Client (false, 1000);
        if(client.isInvalidKey()){
            if(client.isGameFull()){
                System.out.println("Game full");
            }
            else{
                System.out.println("Invalid key, try a new one");
                client.joinGame(1000);
            }
        }
        System.out.println("Tag = " + client.getTag());
        int [] ints = new int [2];
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your first int: ");
        ints[0] = scan.nextInt();
        System.out.println("Enter your second int: ");
        ints[1] = scan.nextInt();

        client.sendMove(ints);
        int [] received = client.receiveMove();
        System.out.println(Arrays.toString(received));
        client.kill();
    }
}
