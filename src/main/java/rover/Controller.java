package rover;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

// My Task CODE
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;



import javafx.scene.control.*;

import java.lang.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import weutils.TabManager;

import rover.SettingsData.*;

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

    private static CameraController cameras;

    private SettingsController settings;

    public SettingsData settingsData = SettingsData.getInstance();

    public static DataBackgroundService service;
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
        settings = new SettingsController();

        /*
         * Initialize the ESP-Service background task
         */
        service = new DataBackgroundService();      // Timer Service
        service.setPeriod(Duration.seconds(1));           // Set the period

        // call roverHTTPGet() when a task cycle has complete
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {

                System.out.println("[" + service.getTicks() + "] Rover Data Fetched!");
                roverHTTPGet();

            }
        });


        // Start the Service
        //service.start();
    }
    public void startService(){
        service.start();
    }

    public void stopService(){
        service.cancel();
    }

    public void roverHTTPGet() {
        try {
            String response = CommunicationsController.getSensorData(settingsData.driveIP);
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


    /**
     * Service to constantly make HTTP requests from the background
     *
     */
    private class DataBackgroundService extends ScheduledService<Integer> {
        private IntegerProperty ts = new SimpleIntegerProperty();

        public final void setTicks(Integer value) {
            ts.set(value);
            //roverHTTPGet();
        }

        public final Integer getTicks() {
            //roverHTTPGet();
            return ts.get();
        }

        public final IntegerProperty ticks() {
            return ts;
        }

        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                protected Integer call() {
                    ts.set(getTicks() + 1); // Increase the tick in the timer

                    return getTicks();
                }
            };
        }
    }

}


