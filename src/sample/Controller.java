package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.*;
import java.lang.*;


public class Controller implements Initializable {
    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        runApp();
    }

    public void runApp()
    {
        double val = 0.0;
        while(true)
        {
            break;
        }
        progressBar.setProgress(0);
    }
    public void win()
    {
        progressBar.setProgress(progressBar.getProgress()+0.1);
        if (progressBar.getProgress() > 0.95)
        {
            progressBar.setProgress(0);
        }
    }
}
