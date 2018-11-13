package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.*;

public class CameraController implements Initializable {
    private Stage stage;

    public void open() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("file:src/fxml/camera.fxml"));
            loader.setLocation(new URL("file:src/fxml/camera.fxml"));
            root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("Rover Camera Feed");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { /* do nothing */ }
}
