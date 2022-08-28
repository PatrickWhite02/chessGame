package net.serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private int port = 8282;
    public HashMap<Integer, UserThread> getAllUsersHashMap() {
        return allUsersHashMap;
    }

    private HashMap <Integer, UserThread> allUsersHashMap = new HashMap<>();
    public void execute() {
        System.out.println("Listening to port: " + port);
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while(true){
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UserThread newUser = new UserThread(socket,this);
                newUser.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.execute();
    }
}
