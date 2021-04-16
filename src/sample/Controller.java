package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Controller {
    @FXML
    public ListView<String> reservationsList;
    public static String myString="pusto";
    public static String toDelete="pusto";
    public int listSize=1;

    //public void generate() {
    //    reservationsList.getItems().add("Aktualne rezerwacje: ");
   // }

    public void refresh(){
        refreshClass myRunnable = new refreshClass();
        Thread t = new Thread(myRunnable);
        t.start();
    }

    public class refreshClass extends Thread {

        @Override
        public void run() {
            while(true){

                try {
                    if (!myString.equals("pusto")) {
                        Platform.runLater(() -> {
                            reservationsList.getItems().add(myString);
                            listSize++;
                            myString = "pusto";
                        });
                    }
                    if (!toDelete.equals("pusto")) {
                        Platform.runLater(() -> {
                            if (reservationsList.getSelectionModel().equals(toDelete))
                            reservationsList.getSelectionModel().select(toDelete);
                            reservationsList.getItems().removeAll(toDelete);
                            //listSize++;
                            toDelete = "pusto";
                        });
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
