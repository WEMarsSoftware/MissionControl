package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
Links:
 - communicating with Arduino by reading data from a Serial port in Java:

https://stackoverflow.com/questions/15996345/java-arduino-read-data-from-the-serial-port

*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("WE MARS MISSION CONTROL");
        primaryStage.setScene(new Scene(root, 600, 600));
       // primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
