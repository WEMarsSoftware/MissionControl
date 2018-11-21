package wemission;

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

public class AddIPsController implements Initializable {

    private Stage stage;

    @FXML
    private TextField driveIPBox;
    @FXML
    private TextField jetsonIPBox;
    @FXML
    private TextField armIPBox;
    @FXML
    private TextField science1IPBox;

    public void open() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("file:src/fxml/addIPs.fxml"));
            loader.setLocation(new URL("file:src/fxml/addIPs.fxml"));
            root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("Add Rover IP's");
            stage.setScene(new Scene(root, 400, 240));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { /* do nothing */ }

    public void save() {
        // save info in the main control
        Controller.setDriveIP(driveIPBox.getText());
        Controller.setScience1IP(science1IPBox.getText());
        Controller.setJetsonIP(jetsonIPBox.getText());
        Controller.setArmIP(armIPBox.getText());
        close();
    }

    public void close() {
        Stage stg = (Stage) driveIPBox.getScene().getWindow();
        stg.close();
    }
}
