package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    public static HashMap<String, String> Reservations = new HashMap<String, String>();
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller myController = loader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 250, 500));
        primaryStage.show();
        //myController.generate();
        myController.refresh();
        ServerStart myRunnable = new ServerStart();
        Thread t = new Thread(myRunnable);
        t.start();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
