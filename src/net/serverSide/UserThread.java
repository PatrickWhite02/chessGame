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
    private boolean terminated = false;

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
                System.out.println("Waiting for input");
                String firstResponse = reader.readLine();
                if(firstResponse.equals("Terminate")){
                    terminated = true;
                    System.out.println("First got terminate");
                    server.getAllUsersHashMap().remove(Integer.parseInt(firstResponse));
                    System.out.println(Integer.parseInt(firstResponse));
                    System.out.println(server.getAllUsersHashMap().containsKey(Integer.parseInt(firstResponse)));
                    server.getAllUsersHashMap().containsKey(Integer.parseInt(firstResponse));
                    socket.close();
                    break;
                }
                System.out.println(firstResponse);
                //if the user just wants to join a pre existing game
                // code for user wanting to join as opposed to host
                if(firstResponse.equals("j")){
                    int secondResponse = Integer.parseInt(reader.readLine());
                    System.out.println(secondResponse);
                    if(server.getAllUsersHashMap().containsKey(secondResponse)){
                        //sets my opponent to the other user with the key, and that users opponent to me
                        opponent = server.getAllUsersHashMap().get(secondResponse);
                        //makes sure multiple users cant join the same game
                        if(!opponent.hasOpponent){
                            hasOpponent = true;
                            opponent.setOpponent(this);
                            //tell the opponent that their opponent joined
                            opponent.getWriter().println("OJ");
                            opponent.setHasOpponent(true);
                            //connection successful
                            writer.println("0");
                            tag = secondResponse;
                            break;
                        }
                        else{
                            //error code for game full
                            writer.println("1");
                        }
                    }
                    else{
                        // i dont think i should break this loop, that way it'll keep checking for valid input
                        //error code for invalid key/game doesnt exist
                        writer.println("2");
                    }
                }
                //the user wants to host, make sure the key i generated for them isn't taken
                //error code for key taken
                else if(server.getAllUsersHashMap().containsKey(Integer.parseInt(firstResponse))){
                    writer.println("0");
                }
                else{
                    //successful key generation
                    System.out.println("1");
                    writer.println("1");
                    tag = Integer.parseInt(firstResponse);
                    server.getAllUsersHashMap().put(tag, this);
                    break;
                }
            }
            //if the user didn't leave by the time someone joined
            if(!terminated){
                while(true){
                    System.out.println("Second waiting for input");
                    //takes the ints received by the server and stores it in a string, this will then be passed into broadcast which will run sendMessage for every thread but this one
                    String receivedMove = reader.readLine();
                    System.out.println(receivedMove);
                    if(receivedMove.equals("T")){
                        System.out.println(hasOpponent);
                        if(hasOpponent){
                            System.out.println("Sending terminate to opponent");
                            //error code for opponent left
                            opponent.getWriter().println("OL");
                        }
                        System.out.println("Got terminate");
                        System.out.println(tag);
                        server.getAllUsersHashMap().remove(tag);
                        socket.close();
                        break;
                    }
                    System.out.println("Server received move " + receivedMove);
                    opponent.getWriter().println(receivedMove);
                }
            }
        } catch (Exception e) {
            System.out.println(hasOpponent);
            if(hasOpponent){
                System.out.println("Sending terminate to opponent");
                //opponent left
                opponent.getWriter().println("OL");
            }
            //if they're the only user in this game, wipe that game from the list
            //I only need the condition for a host, since Board generates a new client altogether if they are a user
            else{
                System.out.println("Removing tag");
                server.getAllUsersHashMap().remove(tag);
            }
            System.out.println("Got terminate");
            System.out.println(tag);
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
