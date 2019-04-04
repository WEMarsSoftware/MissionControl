package weutils;

import com.studiohartman.jamepad.*;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


//import javafx.scene.paint.Color;
/**
    This class defines a wrapper around the JamePad library, to perform in a BackgroundService
    https://github.com/williamahartman/Jamepad <--- Library used
    
    Should be able to support Xinput (XbOne, 360) and DualShock4 (PS4) controllers natively, but may require
    drivers to be installed for each, which i can't control
 */

 //@TODO this should really extend background task, maybe in the future
public class ControllerService {    

    ControllerManager controllers;  // List of controllers
    ControllerIndex activeCon;
    String ControllerName; 
    BackgroundService runner;
    int pollingRate;
    boolean ControllerConnected;

    // Axis Calibrated Values
    double LEFTX;
    double LEFTY;
    double RIGHTX;
    double RIGHTY;

    // Calibrated triggers
    double L1;
    double R1;

    // Default constructor
    public ControllerService(){
        
    

        // Start gamepad service
        controllers = new ControllerManager();
        controllers.initSDLGamepad();
        ControllerIndex activeCon = controllers.getControllerIndex(0);  // Get first controller;

        Calibrate();    // Calibrate the controller

        // Init the background service
        runner = new BackgroundService();
        runner.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                // Controller Stuff here
                controllers.update();       // Check controller
               
                // Detect controls for XInput or DS4
                try{

                    // A or X
                    if(activeCon.isButtonPressed(ControllerButton.A)) {
                        System.out.println("\"A\" on \"" + activeCon.getName() + "\" is pressed");
                    }
                    // B or O
                    if(activeCon.isButtonPressed(ControllerButton.B)) {
                        System.out.println("\"B\" on \"" + activeCon.getName() + "\" is pressed");
                    }
                    
                    // X or
                    if(activeCon.isButtonPressed(ControllerButton.X)) {
                        System.out.println("\"X\" on \"" + activeCon.getName() + "\" is pressed");
                    }

                     if(activeCon.isButtonPressed(ControllerButton.Y)) {
                        System.out.println("\"Y\" on \"" + activeCon.getName() + "\" is pressed");
                    }

                    // Scan the axis
                    for (ControllerAxis a : ControllerAxis.values()) {
                        System.out.println(a.name() + " has a value of " + activeCon.getAxisState(a));
                    }

                } catch (ControllerUnpluggedException e) {  // No Controller Connected
                    System.out.println("[!] - No Controller connected");
                    clearControllerStats();
                } catch (Exception e) {
                    // Catch everything else
                    System.out.println("[" + e + "]" + " - caught in ControllerService()");

                } 
            }
        });
    }

    /**
     * Calibrates the controller Axis, by detecting the current joystick positions
     * and saving them. This is useful, since joysticks are never zerod out, and calibrating
     * them this way allows us to later detect if there is a difference.
     */
    private void Calibrate(){
       try{
            for (ControllerAxis a : ControllerAxis.values()) {
                System.out.println(a.name() + " has a value of " + activeCon.getAxisState(a));
            }
        } catch (ControllerUnpluggedException e) {  
                // No Controller Connected
                System.out.println("[!] - No Controller connected, Cannot Calibrate");

                // Clear all the controller stats
                clearControllerStats();

        } catch (Exception e){
            System.out.println("[" + e + "]" + " - caught in ControllerService.Calibrate()");
            // Do nothing, just here for safety
        }

        
    }

    /**
     * Resets the stored controller stats, and sets connected to false
     */
    private void clearControllerStats(){
        ControllerConnected = false;
        ControllerName = "No Controller Connected";
    }

    /**
     * Sets the period of the polling service. 
     * DONT USE THIS IT AUTO CONFIGURES TO 5 FOR SOME REASON WHICH IS STUPIDLY SLOW
     */
    public void setPeriod(double period){
        if (period > 2){
            System.out.println("[!] Warning: Controller Period is too low, may cause missed inputs (consider <=1)");
        }
        runner.setPeriod(Duration.seconds(1));
    }

    /**
     * Sets the service attached to the controller service. Probably never use this,
     * but maybe one day we might
     */
    public void setService(BackgroundService ser){
        runner = ser;
    }

    /**
     * Returns the background service attached
     */
    public BackgroundService getService(){
        return runner;
    }

    /**
     * Starts the polling service
     */
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