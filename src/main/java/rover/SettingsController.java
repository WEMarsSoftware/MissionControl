package rover;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    // GLOBAL SETTINGS
    public static String driveIPText, armIPText, cameraIPText;
    public static int pollingRateCamera, pollingRateDrive;



    private Stage stage;
    @FXML
    private TextField driveIP;
    @FXML
    private TextField armIP;
    @FXML
    private TextField cameraIP;
    @FXML
    private Slider cameraFPS;
    @FXML
    private Slider driveFPS;

    public void open() {
        Parent root;
        try {
            String addIPSPath = "/fxml/settings.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(addIPSPath));
            loader.setLocation(getClass().getResource(addIPSPath));
            root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 500, 300));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/images/WesternLogo.png"));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) { /* do nothing */ }

    // confirmed this works
    public void save() {
        // save selections
        driveIPText = driveIP.getText();
        armIPText = armIP.getText();
        cameraIPText = cameraIP.getText();
        pollingRateCamera = (int)(cameraFPS.getValue());
        pollingRateDrive = (int)(driveFPS.getValue());

        close();
    }

    public void close() {
        Stage stg = (Stage) driveIP.getScene().getWindow();
        stg.close();
    }

}
