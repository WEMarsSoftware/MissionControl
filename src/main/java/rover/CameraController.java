package rover;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import java.lang.*;
import javafx.scene.layout.AnchorPane;

public class CameraController implements Initializable {
    private static Stage stage;

    @FXML
    private ImageView camera1;
    @FXML
    private ImageView camera2;
    @FXML
    private ImageView camera3;
    @FXML
    private ImageView camera4;
    @FXML
    private AnchorPane root;
    // a little more explicit than an array
    private String camera1Source, camera2Source, camera3Source, camera4Source;

    public CameraController() {
        updateAddresses();
    }

    public void openWindow() {
        try {
            String camPath = "/src/main/resources/fxml/camera.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(camPath));
            loader.setLocation(getClass().getResource(camPath));
            root = (AnchorPane) loader.load();
            stage = new Stage();
            stage.setTitle("Rover Camera Feed");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();

            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO: make this work in the long run
     * For some reason private ImageView's all are null after initialize(), but root AnchorPane isn't
     * This works, as long as Nodes 0-3 of the root AnchorPane are ImageViews
      */
    public void update() {
        try {
            // String address = SettingsData.getJestonIP()+Integer.toString(SettingsData.getPollingRateCamera());
            // ((ImageView)root.getChildren().get(0)).setImage(new Image(address));
            // ((ImageView)root.getChildren().get(1)).setImage(new Image(camera2Source));
            // ((ImageView)root.getChildren().get(2)).setImage(new Image(camera3Source));
            // ((ImageView)root.getChildren().get(3)).setImage(new Image(camera4Source));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateAddresses() {
        /*
        UNCOMMENT ONCE JETSON STUFF IS IMPLEMENTED
        camera1Source = Controller.getJestonIP()+"/camera1";
        camera2Source = Controller.getJestonIP()+"/camera2";
        camera3Source = Controller.getJestonIP()+"/camera3";
        camera4Source = Controller.getJestonIP()+"/camera4";
        */
        // TODO: use commented out stuff in future (this is for testing)
       // camera1Source = camera2Source = camera3Source = camera4Source = "https://static.thenounproject.com/png/302033-200.png";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* Do nothing */
        updateAddresses();
    }

}
