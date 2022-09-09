package net.clientSide;

import splashScreens.WaitingForOpponentScreen;

import java.io.*;
import java.net.Socket;

public class HasOpponentThread extends Thread{
    private Socket socket;
    InputStream input;
    BufferedReader reader;
    private Client client;
    WaitingForOpponentScreen waitingForOpponentScreen;
    public HasOpponentThread(WaitingForOpponentScreen waitingForOpponentScreen, Socket socket, Client client){
        this.waitingForOpponentScreen = waitingForOpponentScreen;
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
        System.out.println("HasOpponentThreadStarted");
        while(true){
            try {
                System.out.println("Has opponent thread is working with tag: " + client.getTag());
                System.out.println("Has opponent thread is the host? " + client.getHost());
                String message = reader.readLine();
                System.out.println(message);
                if(message.equals("OJ")){
                    client.setHasOpponent(true);
                    System.out.println("Interrupting");
                    waitingForOpponentScreen.interrupt();
                    break;
                }
            }catch (IOException e) {
                System.out.println("Host disconnected");
                break;
            }
        }
        System.out.println("Has open thread terminated");
    }
}
