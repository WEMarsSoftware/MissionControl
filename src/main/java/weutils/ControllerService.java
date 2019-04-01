package weutils;

import com.studiohartman.jamepad.*;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


/**
    This class defines a wrapper around the JamePad library, to perform in a BackgroundService
    https://github.com/williamahartman/Jamepad <--- Library used
    
    Should be able to support Xinput (XbOne, 360) and DS4 (PS4) controllers natively, but requies
    drivers to be installed for each
 */
public class ControllerService {

    ControllerManager controllers;  // List of controllers
    ControllerIndex activeCon;
    String ControllerName; 
    BackgroundService runner;
    int pollingRate;
    boolean ControllerConnected;

    // Calibrated values
    double LEFTX;
    double LEFTY;
    double RIGHTX;
    double RIGHTY;

    // Default constructor
    public ControllerService(){
        

        // Start gamepad service
        controllers = new ControllerManager();
        controllers.initSDLGamepad();
        ControllerIndex activeCon = controllers.getControllerIndex(0);  // Get first controller;

        // Init the background service
        runner = new BackgroundService();
        runner.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                // Controller Stuff here
                controllers.update();       // Check controller

                try{

                    // 
                    if(activeCon.isButtonPressed(ControllerButton.A)) {
                        System.out.println("\"A\" on \"" + activeCon.getName() + "\" is pressed");
                    }

                    if(activeCon.isButtonPressed(ControllerButton.B)) {
                        System.out.println("\"B\" on \"" + activeCon.getName() + "\" is pressed");
                    }

                    if(activeCon.isButtonPressed(ControllerButton.B)) {
                        System.out.println("\"B\" on \"" + activeCon.getName() + "\" is pressed");
                    }

                     if(activeCon.isButtonPressed(ControllerButton.B)) {
                        System.out.println("\"B\" on \"" + activeCon.getName() + "\" is pressed");
                    }

                    // Scan the axis
                    for (ControllerAxis a : ControllerAxis.values()) {
                        System.out.println(a.name() + " has a value of " + activeCon.getAxisState(a));
                    }

                } catch (ControllerUnpluggedException e) {  // No Controller Connected
                    clearControllerStats();
                } 
            }
        });


        System.out.println("Controller Service created");
    }

    private void Calibrate(){

    }

    private void clearControllerStats(){
        ControllerConnected = false;
        ControllerName = "No Controller Connected";
    }

    public void setPeriod(double period){
        if (period > 2){
            System.out.println("[!] Warning: Controller Period is too low, may cause missed inputs (consider <=1)");
        }
        runner.setPeriod(Duration.seconds(1));
    }

    public void setService(BackgroundService ser){
        runner = ser;
    }

    public BackgroundService getService(){
        return runner;
    }

    public void start(){
        runner.start();
    }



    /**
     * Stop the Controller Service 
     */
    public void stopControllerService(){
        controllers.quitSDLGamepad();
    }
}