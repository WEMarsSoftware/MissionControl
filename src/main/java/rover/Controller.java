package rover;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.scene.control.*;

import java.lang.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import weutils.TabManager;

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

    @FXML
    private TabPane testTab;


    private CommunicationsController comms;

    private static CameraController cameras;

    private SettingsController settings;

    /**
     * Any tasks that affect GUI elements need to run in a timeline handle()
     * Background tasks not affecting GUI stuff can run in Worker threads
     * <p>
     * https://stackoverflow.com/questions/9966136/javafx-periodic-background-task
     */
    Timeline secondLoop;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* Initialize data points */
        comms = new CommunicationsController();


        //
        TabManager.create().setAllDockable(testTab);  // Convert to WEMars TabManager Utility

        /* Initialize camera feeds */
        cameras = new CameraController();
        cameras.openWindow();

        settings = new SettingsController();

        secondLoop = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    cameras.update();

                } catch (Exception e) {
                    /* Do nothing */
                }
            }
        }));
        secondLoop.setCycleCount(Animation.INDEFINITE);
        secondLoop.play();
    }

    public void roverHTTPGet() {
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

    public void openSettings() {
        settings.open();
    }
    // setters for IP's for device
    // Note: updateAddresses() pulls IP's from this main controller
    public static void setDriveIP(String ip) {
        driveIP = ip;
        cameras.updateAddresses();
    }
    public static void setScience1IP(String ip) {
        science1IP = ip;
        cameras.updateAddresses();
    }
    public static void setArmIP(String ip) {
        armIP = ip;
        cameras.updateAddresses();
    }
    public static void setJetsonIP(String ip) {
        jetsonIP = ip;
        cameras.updateAddresses();
    }

    public static String getJestonIP() { return jetsonIP; }
}
