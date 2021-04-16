package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ServerStart extends Thread{

    @Override
    public void run(){
        System.out.println("Server started.");
        try {
            ServerSocket ss = new ServerSocket(5056);
            ArrayList<String> clients = new ArrayList<>();

            while (true) {
                Socket s = null;

                try {
                    s = ss.accept();
                    String clientName = "Client-" + s.getPort();
                    System.out.println("A new client is connected: " + clientName);

                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true);
                    clients.add(clientName);

                    Thread t = new ClientHandler(s, in, out, clientName, clients);
                    t.start();

                    System.out.println("Thread assigned! Communication started...");
                } catch (Exception e) {
                    assert s != null;
                    s.close();
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException | IOException npe) {
            System.out.println("Null pointer exepti");
        }
    }
}
