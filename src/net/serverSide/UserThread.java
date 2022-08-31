package net.serverSide;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class UserThread extends Thread{
    private Socket socket;
    private Server server;
    private int tag;

    public PrintWriter getWriter() {
        return writer;
    }

    private PrintWriter writer;
    private UserThread opponent;

    public void setHasOpponent(boolean hasOpponent) {
        this.hasOpponent = hasOpponent;
    }

    private volatile boolean hasOpponent = false;
    public UserThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }
    public void setOpponent(UserThread opponent){this.opponent = opponent;}
    public void run(){
        try{
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            OutputStream out = socket.getOutputStream();
            writer = new PrintWriter(out, true);
            while(true){
                String firstResponse = reader.readLine();
                //if the user just wants to join a pre existing game
                if(firstResponse.equals("join")){
                    int secondResponse = Integer.parseInt(reader.readLine());
                    if(server.getAllUsersHashMap().containsKey(secondResponse)){
                        //sets my opponent to the other user with the key, and that users opponent to me
                        opponent = server.getAllUsersHashMap().get(secondResponse);
                        //makes sure multiple users cant join the same game
                        if(!opponent.hasOpponent){
                            opponent.setOpponent(this);
                            opponent.setHasOpponent(true);
                            writer.println("valid");
                            tag = secondResponse;
                            break;
                        }
                        else{
                            writer.println("full");
                        }
                    }
                    else{
                        // i dont think i should break this loop, that way it'll keep checking for valid input
                        writer.println("invalid");
                    }
                }
                //the user wants to host, make sure the key i generated for them isn't taken
                else if(server.getAllUsersHashMap().containsKey(Integer.parseInt(firstResponse))){
                    writer.println("taken");
                }
                else{
                    writer.println("new");
                    server.getAllUsersHashMap().put(Integer.parseInt(firstResponse), this);
                    //wait for their opponent to match with them
                    while(!hasOpponent){
                        Thread.sleep(1000);
                    }
                    tag = Integer.parseInt(firstResponse);
                    break;
                }
            }
            //add a boolean for when the player leaves the game
            while(true){
                //takes the ints received by the server and stores it in a string, this will then be passed into broadcast which will run sendMessage for every thread but this one
                String receivedMove = reader.readLine();
                if(receivedMove.equals("Terminate")){
                    server.getAllUsersHashMap().remove(server.getAllUsersHashMap().remove(tag));
                    socket.close();
                    break;
                }
                System.out.println("Server received move " + receivedMove);
                opponent.getWriter().println(receivedMove);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //will print the message in the terminal for every thread but this one
    public void sendMessage(String message){
        writer.println(message);
    }
    public void broadcast(){

    }
}
