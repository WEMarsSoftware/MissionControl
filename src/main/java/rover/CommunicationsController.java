package rover;

import weutils.HTTPManager;

import java.net.URL;

import javafx.scene.control.*;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.lang.*;

import org.json.*;

import rover.SettingsData.*;

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
    * Send a chassis drive command and return the response as a string.
    * Returns motor speed and current values in JSON.
    *
    * @param left - percentage speed for left side motors (-100 to +100)
    * @param right - percentage speed for right side motors (-100 to +100)
    * @return response as String
    * @throws if bad things happen
    */
    public static String sendDriveCommand(int left, int right) throws Exception
    {
        String url = ("http://"+SettingsData.getDriveIP()+"/?left-side="+Integer.toString(left)+"&right-side="+Integer.toString(right));
        return HTTPManager.httpGet(url);
    }
    /**
    * Send command to ESP-32 controlling arm.
    * will return JSON with sensor data:
    *
    *   { 
    *     "Current-Motor1":0,
    *       ...
    *     "Current-Motor6":0,
    *     "Pot-Motor1":15,
    *       ...
    *     "Pot-Motor6":20 
    *    }
    *
    * @param motorPowers -> 6 "percentage power values" (from -100 to +100) for 6 arm motors
    * @return entire JSON response as string w/ newlines removes
    */
    public static String sendArmCommand(int[] motorPowers) throws Exception
    {
        String url = ("http://"+SettingsData.getArmIP()
            +"/?motor1="+Integer.toString(motorPowers[0])
            +"&motor2="+Integer.toString(motorPowers[1])
            +"&motor3="+Integer.toString(motorPowers[2])
            +"&motor4="+Integer.toString(motorPowers[3])
            +"&motor5="+Integer.toString(motorPowers[4])
            +"&motor6="+Integer.toString(motorPowers[5]));
            return HTTPManager.httpGet(url);

    }

    public SettingsData settingsData = SettingsData.getInstance();

    /**
     * @param url - IP of controller with current sensors
     * @return the entire HTTP response as a String
     * @throws Exception if bad things happen
     */
    public static String getSensorData(String url) throws Exception
    {
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
        words.setText(newCurrentVal);
    }
}
