package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.util.*;
import java.lang.*;


public class Controller implements Initializable {
    @FXML
    private TextField roverIP;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void httpTest()
    {
        System.out.println("HTTP TEST");
        try {
            CommunicationsController.getSensorData();
        } catch (Exception e) {
            // nothing
            System.out.println(e.getMessage());
        }
    }

}
