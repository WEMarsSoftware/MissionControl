package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/*
Links:
 - communicating with Arduino by reading data from a Serial port in Java:

https://stackoverflow.com/questions/15996345/java-arduino-read-data-from-the-serial-port

*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("WE MARS Mission Control");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image("file:src/sample/WesternLogo.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
