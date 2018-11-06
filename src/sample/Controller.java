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

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void roverHTTPGet()
    {
        try {
            String response = CommunicationsController.getSensorData(roverIP);
            CommunicationsController.updateLog(response, logger);
        } catch (Exception e) {
            /* do nothing */
        }
    }

}
