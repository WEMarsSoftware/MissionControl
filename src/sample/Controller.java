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
    private static String driveIP;
    private static String armIP;
    private static String science1IP;
    private static String jetsonIP;

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

        /* Initialize camera feeds */
        CameraController cameras = new CameraController();
        cameras.open();
    }

    public void roverHTTPGet()
    {
        try {
            String response = CommunicationsController.getSensorData(driveIP);
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
        AddIPsController ipController = new AddIPsController();
        ipController.open();
    }

    // setters for IP's for device
    public static void setDriveIP(String ip) { driveIP = ip; }
    public static void setScience1IP(String ip) { science1IP = ip; }
    public static void setArmIP(String ip) { armIP = ip; }
    public static void setJetsonIP(String ip) { jetsonIP = ip; }
}
