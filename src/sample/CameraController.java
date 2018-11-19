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
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import java.lang.*;

public class CameraController implements Initializable {
    private Stage stage;

    @FXML
    private static ImageView camera1, camera2, camera3, camera4;

    // a little more explicit than an array
    private String camera1Source, camera2Source, camera3Source, camera4Source;

    public CameraController() {
        camera1Source = Controller.getJestonIP()+"/camera1";
        camera2Source = Controller.getJestonIP()+"/camera2";
        camera3Source = Controller.getJestonIP()+"/camera3";
        camera4Source = Controller.getJestonIP()+"/camera4";
    }
    public void openWindow() {
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

    // TODO: fix this
    public void update() {
        // deprecated but works
        final Image image1 = new Image("https://images.ctfassets.net/zw48pl1isxmc/4QYN7VubAAgEAGs0EuWguw/165749ef2fa01c1c004b6a167fd27835/ab-testing.png");
        camera1 = new ImageView(image1);
       // camera1.setImage(camera1Image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { /* do nothing */ }
}
