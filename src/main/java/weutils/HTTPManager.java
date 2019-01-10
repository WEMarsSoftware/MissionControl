package weutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Used to de-duplicate code for HTTP requests to rover devices.
 */
public class HTTPManager {

    // temporary
    public static final String USER_AGENT = "";

    /**
     * @param url - URL to GET to (including any parameters
     * @return full String response
     * @throws Exception upon connection or buffer errors
     */
    public static String httpGet(String url) throws Exception
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        // required for certain requests
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        System.out.println("Sending 'GET' request to URL : " + url);
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
}
