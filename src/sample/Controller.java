package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import java.util.*;
import java.lang.*;


public class Controller implements Initializable {

    @FXML
    private Label timeLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        runApp();
    }

    public void runApp()
    {
        while(true)
        {
            try {

            }
            catch (Exception e)
            {
                // do nothing
            }
        }
    }
}
