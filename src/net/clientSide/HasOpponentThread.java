package net.clientSide;

import java.io.*;
import java.net.Socket;

public class HasOpponentThread extends Thread{
    private Socket socket;
    private Client client;
    public HasOpponentThread(Socket socket,Client client){
        this.socket = socket;
        this.client = client;
    }
    public void run(){
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            while(true){
                String message = reader.readLine();
                System.out.println(message);
                if(message.equals("Opponent Joined")){
                    client.setHasOpponent(true);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
