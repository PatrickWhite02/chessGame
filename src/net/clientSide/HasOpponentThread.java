package net.clientSide;

import com.Board;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class HasOpponentThread extends Thread{
    private Socket socket;
    InputStream input;
    BufferedReader reader;
    private Client client;
    public HasOpponentThread(Socket socket,Client client){
        this.socket = socket;
        this.client = client;
        try {
            input = this.socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader = new BufferedReader(new InputStreamReader(input));
    }
    public void run(){
        while(true){
            try {
                String message = reader.readLine();
                System.out.println(message);
                if(message.equals("Opponent Joined")){
                    client.setHasOpponent(true);
                    break;
                }
            }catch (IOException e) {
                System.out.println("Host disconnected");
                break;
            }
        }
    }
}
