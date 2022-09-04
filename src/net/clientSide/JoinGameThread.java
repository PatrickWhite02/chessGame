package net.clientSide;

import java.io.*;
import java.net.Socket;

public class JoinGameThread extends Thread{
    private Socket socket;
    private Client client;
    private int tag;
    private boolean isHost;
    private OutputStream output;
    private InputStream input;
    private BufferedReader reader;
    private PrintWriter writer;

    public boolean isInvalidKey() {
        return invalidKey;
    }

    private boolean invalidKey = false;
    public JoinGameThread(Socket socket, Client client, int tag, boolean isHost){
        this.isHost = isHost;
        this.socket = socket;
        this.client = client;
        this.tag = tag;
    }
    private int generateKey(){
        for(int i = 1000; i < 10000; i++){
            writer.println(i);
            try {
                if(!reader.readLine().equals("taken")){
                    return i;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //I don't think I'll ever have this many people playing lol
        return -1;
    }
    public void run(){
        try {
            output = socket.getOutputStream();
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            System.out.println("JoinGameThread is host: " + isHost);
            if(isHost){
                //generates a new tag if the user is a host
                client.setTag(generateKey());
            }else{
                System.out.println("HIT");
                writer.println("join");
                writer.println(tag);
                String response = reader.readLine();
                if(response.equals("full")){
                    client.setInvalidKey(true);
                    client.setGameFull(true);
                }
                else{
                    if(!response.equals("invalid")){
                        client.setTag(tag);
                    }
                    else{
                        client.setInvalidKey(true);
                    }
                }
                System.out.println("HIT");
            }
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
