package sample;

import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.*;
import java.lang.*;
import javafx.scene.control.*;

public class AddIPsController {
    // reference main app controller
    private Controller mainController;

    @FXML
    private TextField driveIPBox;
    private TextField jetsonIPBox;
    private TextField armIPBox;
    private TextField science1IPBox;

    public AddIPsController() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("addIPs.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Rover IP's");
            stage.setScene(new Scene(root, 400, 200));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWindow() {
        // save info somehow
        mainController.setRoverIPs(driveIPBox.getText());
        mainController.setArmIP(armIPBox.getText());
        mainController.setJetsonIP(jetsonIPBox.getText());
        mainController.setScience1IP(science1IPBox.getText());
    }
}
