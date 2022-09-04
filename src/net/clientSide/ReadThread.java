package net.clientSide;

import com.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread{
    private BufferedReader reader;
    Socket socket;
    Client client;
    boolean pawnChange = false;
    boolean castle = false;
    private int [] move = new int[2];
    private Board board;
    private boolean stayOn = true;
    private boolean opponentLeft = false;
    public ReadThread(Socket socket, Client client, Board board){
        this.board = board;
        this.socket = socket;
        this.client = client;
        try{
            //this is the data stream coming in from Server -> Client -> ReadThread
            InputStream in = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));

        }catch(Exception e){
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //thread for prompting a new gameOverScreen in Board
    //this needs to be a thread so it can run in tandem
    public void promptGameOverScreen(){
        new Thread() {
            @Override
            public void run(){
                Board.checkTest();
            }
        }.start();
    }
    public void run(){
        while(true){
            try{
                System.out.println("Read thread waiting for response");
                String response = reader.readLine();
                System.out.println("Read thread got a response: " + response);
                if(response.equals("Opponent left")){
                    System.out.println("Opponent left");
                    opponentLeft = true;
                    break;
                }
                else if(response.equals("Host wants new game")){
                    //disposes the "Waiting for host.." screen and prompts new game
                    Board.getGameOverScreenGuest().launchGuest();
                }
                else if(castle){
                    castle = false;
                   int kingLocation = Integer.parseInt(response);
                   //black castle to the left
                   if(kingLocation == 2){
                       Board.getTile(0).setValue("blank");
                       Board.getTile(2).setValue("blackKing");
                       Board.getTile(3).setValue("blackRook");
                       Board.getTile(4).setValue("blank");
                   }
                   //black castle to the right
                   else if(kingLocation == 6){
                       Board.getTile(4).setValue("blank");
                       Board.getTile(5).setValue("blackRook");
                       Board.getTile(6).setValue("blackKing");
                       Board.getTile(7).setValue("blank");
                   }
                   //white king castle left
                   else if(kingLocation == 58){
                       Board.getTile(56).setValue("blank");
                       Board.getTile(58).setValue("whiteKing");
                       Board.getTile(59).setValue("whiteRook");
                       Board.getTile(60).setValue("blank");
                   }
                   //white king castle right
                   else{
                       Board.getTile(60).setValue("blank");
                       Board.getTile(61).setValue("whiteRook");
                       Board.getTile(62).setValue("whiteKing");
                       Board.getTile(63).setValue("blank");
                   }
                }
                //if we were previously flagged to pawnChange
                else if(pawnChange){
                    pawnChange = false;
                    String [] splitResponse = response.split(" ");
                    int prevPawnLocation = Integer.parseInt(splitResponse[0]);
                    int locationOfPawn = Integer.parseInt(splitResponse[1]);
                    int pieceValue = Integer.parseInt(splitResponse[2]);
                    Board.getTile(prevPawnLocation).setValue("blank");
                    Board.getTile(locationOfPawn).setValue(pieceValue);
                }
                else if (response.equals("castle")){
                    castle = true;
                }
                else if (response.equals("pawnChange")){
                    pawnChange = true;
                }
                //if it's a normal move
                else {
                    //converts response back to int array
                    String [] splitResponse = response.split(" ");
                    move[0] = Integer.parseInt(splitResponse[0]);
                    move[1] = Integer.parseInt(splitResponse[1]);
                    //shoots it to Client
                    client.setMove(move);
                    System.out.println("Tile Readthread has been instructed to move from: " + Board.getTile(move[0]).getValueAsString());
                    System.out.println("Tile Readthread has been instructed to move to: " + Board.getTile(move[1]).getValueAsString());
                    Board.getTile(move[1]).setValue(Board.getTile(move[0]).getValueAsString());
                    Board.getTile(move[0]).setValue("blank");
                    System.out.println("Checktest");
                    System.out.println(Board.getWhoTurn());
                    if(!Board.checkForMate()){
                        //only swap turns if it isn't game over
                        Board.setWasGameOver(false);
                        System.out.println("End of opponent's turn, swapping turns");
                        Board.swapTurns();
                    }else{
                        promptGameOverScreen();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        if(opponentLeft){
            System.out.println("ReadThread terminated");
            Board.opponentLeft();
        }
    }
}
