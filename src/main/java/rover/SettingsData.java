package rover;
/**
 * Implements a Singleton pattern to hold data across the application
 */
public class SettingsData {
    private static SettingsData instance = new SettingsData();  // Single instance of GlobalData

    // Variables
    public static String armIP;
    public static String jetsonIP;
    public static String driveIP;
    public static int pollingRateCamera, pollingRateDrive;

    private SettingsData(){
        SettingsData.driveIP = "0.0.0.0";
    }
    /**
     * Returns the instance of SettingsDatas
     * @return
     */
    public static SettingsData getInstance() {

        // return
        return instance;
    }

    /**
     * returns the Arm IP address
     * @return armIP
     */
    public static String getArmIP() {
        return armIP;
    }

    /**
     * returns the jetsonIP
     * @return jetsonIP
     */
    public static String getJetsonIP() {
        return jetsonIP;
    }

    /**
     * returns the Drive IP
     * @return driveIP
     */
    public static String getDriveIP() {
        return driveIP;
    }

    /**
     * Sets the drive IP
     * @param driveIP
     */
    public void setDriveIP(String driveIPS) {

        SettingsData.driveIP = driveIPS;
        System.out.println("Made it here");
    }

    /**
     * Sets the jestonIP
     * @param jetsonIP
     */
    public static void setJetsonIP(String jetsonIP) {
        SettingsData.jetsonIP = jetsonIP;
    }

    /**
     * Sets the armIP
     * @param armIP
     */
    public static void setArmIP(String armIP) {
        SettingsData.armIP = armIP;
    }

    public static void setPollingRateCamera(int x) {
        pollingRateCamera = x;
    }

    public static void setPollingRateDrive(int x) {
        pollingRateDrive = x;
    }
}