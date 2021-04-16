package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.net.ssl.SNIMatcher;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread {

    final BufferedReader in;
    final PrintWriter out;
    final Socket s;
    final String clientName;
    final ArrayList<String> clients;

    public ClientHandler(Socket s, BufferedReader in, PrintWriter out, String clientName, ArrayList<String> clients) {
        this.s = s;
        this.in = in;
        this.out = out;
        this.clientName = clientName;
        this.clients = clients;
    }

    @Override
    public void run() {
        String clientMessage;
        String nickname;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller myController = loader.getController();

        while (true) {
            try {
                clientMessage = in.readLine();
                nickname = in.readLine();
                System.out.println("Message accepted: '" + clientMessage + "'");
                System.out.println("            from: " + nickname);

                if (clientMessage.startsWith("delete ")) {
                    String newvalue = clientMessage.substring(clientMessage.indexOf(" ") + 1);
                    System.out.println(newvalue);
                    Main.Reservations.remove(newvalue,"reservated by" + nickname);
                    Controller.toDelete=newvalue + " reservated by " + nickname;
                    out.println(newvalue);
                    System.out.println("Deleted: " + newvalue);
                    printReservations();
                }
                if(Main.Reservations.containsKey(clientMessage)) {
                    System.out.println("Already reservated");
                    out.println("already reservated");
                    printReservations();
                }
                if(!(Main.Reservations.containsKey(clientMessage)) && !(clientMessage.startsWith("delete "))){
                    Main.Reservations.put(clientMessage,"reservated by" + nickname);
                    myController.myString=clientMessage + " reservated by " + nickname;//PRZEZ TO BŁĄD
                    out.println(clientMessage);
                    printReservations();
                }
                System.out.println("Message sent to: " + nickname + ".");
            } catch (IOException e) {
                System.out.println("Client: " + clientName + " disconnected. ");
                break;
            }
        }

        try {
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.out.close();
    }

    public void printReservations(){
        System.out.println("Reserved dates: " );
        for (String key : Main.Reservations.keySet()) {
            System.out.println(" Date:" + key + " was reserved");
        }
    }
}