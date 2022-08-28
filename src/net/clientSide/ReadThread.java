package net.clientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread{
    private BufferedReader reader;
    Socket socket;
    Client client;
    private int [] move = new int[2];
    public ReadThread(Socket socket, Client client){
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
    public void run(){
        while(true){
            try{
                String response = reader.readLine();
                System.out.println("got response");
                //converts response back to int array
                String [] splitResponse = response.split(" ");
                move[0] = Integer.parseInt(splitResponse[0]);
                move[1] = Integer.parseInt(splitResponse[1]);
                //shoots it to Client
                client.setMove(move);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
