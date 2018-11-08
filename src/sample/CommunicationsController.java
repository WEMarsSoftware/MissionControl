package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.ProgressBar;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.soap.Text;
import java.util.*;

import java.lang.*;

import org.json.*;

public class CommunicationsController {

    /* This may need to be changed for different systems */
    // TODO: check if this can be null
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";

    private final static int NUM_MOTORS = 6;
    private final static double MAX_CURRENT_IN_AMPS = 50.0;

    private static double[] motorCurrents;

    private final static String MOTOR_CURRENTS_LABEL_PREFIX = "Motor1: ";

    public CommunicationsController() {
        motorCurrents = new double[NUM_MOTORS];
    }

    /**
     * @param roverIP - textField in GUI containing entered IP
     * @return the entire HTTP response as a String
     * @throws Exception if bad things happen
     */
    public static String getSensorData(TextField roverIP) throws Exception
    {

        String url = roverIP.getText();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        System.out.println("URL: " + url);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static double getCurrentAsPercentage(double current) {
        return (current/MAX_CURRENT_IN_AMPS);
    }
    /**
     * Update progress bars with values
     * @param response string to set textArea to show
     */
    public static void updateData(String response)
    {
        parseJSON(response);
    }

    /**
     * Parse JSON in response and store values in private data variables (ie. sensor data)
     * @param response - full HTTP response from rover
     */
    public static void parseJSON(String response) {
        /* use org.json library */
        /* set progress bars for motor currents */
        String text = "";
        String temp = "";


        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);

            // NOTE: motor currents appear in JSON 1-indexed (1->6)
            // parse out motor currents
            for (int i = 1; i <= motorCurrents.length; i++) {
                temp = jsonObject.getString("Motor"+ Integer.toString(i));
                motorCurrents[i-1] = Double.parseDouble(temp);
                System.out.println("Motor " + i + ": " + motorCurrents[i-1]);
                text = text + " " + temp;
            }
        } catch (org.json.JSONException e) {
            /* do something */
            System.out.println("JSON Parse Error:");
            System.out.println(e.getCause() + " " + e.getMessage());
            e.printStackTrace();
        }

    }
    
    public static void updateLabel(Label words, int motorNumber) {
        String newCurrentVal =  Double.toString(motorCurrents[motorNumber]);
        newCurrentVal = words.getText().substring(0, MOTOR_CURRENTS_LABEL_PREFIX.length()) + " " + newCurrentVal;
        System.out.println("UPDATE LABEL");
        System.out.println(newCurrentVal);
        words.setText(newCurrentVal);
    }
}