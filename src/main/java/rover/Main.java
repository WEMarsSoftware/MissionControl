package rover;
//
//import edu.wpi.first.wpilibj.DriverStationLCD;
//import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.command.CommandGroup;
//import edu.wpi.first.wpilibj.command.Scheduler;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import weutils.ControllerService;
/*
Links:
 - communicating with Arduino by reading data from a Serial port in Java:

https://stackoverflow.com/questions/15996345/java-arduino-read-data-from-the-serial-port

*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("WEMars Mission Control");
        Scene primaryScene = new Scene(root, 1000, 600);

        /* Callback for key press events
         *  This may be useful for joystick input (a future TODO)
         */
        primaryScene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();

            // System.out.println(codeString);
        });

        // Set the primary scene
        primaryStage.setScene(primaryScene);

        // Primary Stag configuration
        primaryStage.getIcons().add(new Image("/images/WesternLogo.png"));
        primaryStage.setResizable(true);
        primaryStage.show();

        // Start the Controller Service
        ControllerService conService = new ControllerService();
        conService.setPeriod(1);
        conService.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
