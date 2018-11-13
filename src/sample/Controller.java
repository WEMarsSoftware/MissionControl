package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;

import java.util.*;
import java.lang.*;


public class Controller implements Initializable {
    /**
     * IP's of various controllers
     */
    private String driveIP;
    private String armIP;
    private String science1IP;
    private String jetsonIP;

    @FXML
    private TextField roverIP;

    @FXML
    private TextArea logger; // console for HTTP stuff

    @FXML
    private Label motor1;
    @FXML
    private Label motor2;
    @FXML
    private Label motor3;
    @FXML
    private Label motor4;
    @FXML
    private Label motor5;
    @FXML
    private Label motor6;

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
            CommunicationsController.updateLabel(motor1, 0);
            CommunicationsController.updateLabel(motor2, 1);
            CommunicationsController.updateLabel(motor3, 2);
            CommunicationsController.updateLabel(motor4, 3);
            CommunicationsController.updateLabel(motor5, 4);
            CommunicationsController.updateLabel(motor6, 5);
        } catch (Exception e) {
            System.out.println("ROVER HTTP GET ERROR");
            System.out.println(e.getCause() + " " + e.getMessage());
        }
    }

    public void setRoverIPs() {
        // opens new window
        AddIPsController newWindow = new AddIPsController();
    }

    // setters for IP's for device
    public void setDriveIP(String ip) { driveIP = ip; }
    public void setScience1IP(String ip) { science1IP = ip; }
    public void setArmIP(String ip) { armIP = ip; }
    public void setJetsonIP(String ip) { jetsonIP = ip; }
}
