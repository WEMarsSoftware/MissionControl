package rover;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import rover.SettingsData.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    // GLOBAL SETTINGS
    public static String driveIPText, armIPText, cameraIPText;
    public static int pollingRateCamera, pollingRateDrive;

    public static SettingsData settingsData = SettingsData.getInstance();;

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

    @FXML
    public void open() {
        System.out.println("Opened");

        Parent root;
        try {
            String addIPSPath = "/fxml/settings.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(addIPSPath));
            loader.setLocation(getClass().getResource(addIPSPath));
            root = loader.load();
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
        // save selections -- May not need these
        System.out.println("Opened-save");
        driveIPText = driveIP.getText();
        armIPText = armIP.getText();
        cameraIPText = cameraIP.getText();
        pollingRateCamera = (int)(cameraFPS.getValue());
        pollingRateDrive = (int)(driveFPS.getValue());

        // Save Singleton Datagra
        settingsData.driveIP = driveIPText;
        settingsData.armIP = armIPText;
        settingsData.jetsonIP = cameraIPText;
        close();
    }

    public void close() {
        Stage stg = (Stage) driveIP.getScene().getWindow();
        stg.close();
    }

}
