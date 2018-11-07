package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.*;
import java.lang.*;


public class Controller implements Initializable {
    @FXML
    private TextField roverIP;

    @FXML
    private TextArea logger; // console for HTTP stuff

    @FXML
    protected static ProgressBar motor1;
    @FXML
    protected static ProgressBar motor2;
    @FXML
    protected static ProgressBar motor3;
    @FXML
    protected static ProgressBar motor4;
    @FXML
    protected static ProgressBar motor5;
    @FXML
    protected static ProgressBar motor6;

    private CommunicationsController comms;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* Initialize data points */
        comms = new CommunicationsController();
    }

    public void roverHTTPGet()
    {
        try {
            String response = CommunicationsController.getSensorData(roverIP);
            logger.setText(response);
            CommunicationsController.updateData(response);
            CommunicationsController.updateProgessBar(motor1, 1);
        } catch (Exception e) {
            System.out.println(e.getCause() + " " + e.getMessage());
        }
    }

}
