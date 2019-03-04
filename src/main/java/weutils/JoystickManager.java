package weutils;

// jinput controls library
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import sun.reflect.annotation.ExceptionProxy;

import java.lang.*;
import java.lang.reflect.Constructor;

/**
 * LINKING WITH GRADLE - see https://mvnrepository.com/artifact/net.java.jinput/jinput/2.0.6
 */
public class JoystickManager {
    public static Controller joystick;
    public static final String X_AXIS = "AXIS.POSX";
    public static final String Y_AXIS = "AXIS.POSY";


    private static ControllerEnvironment createDefaultEnvironment() throws ReflectiveOperationException {

        // Find constructor (class is package private, so we can't access it directly)
        Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>)
                Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

        // Constructor is package private, so we have to deactivate access control checks
        constructor.setAccessible(true);

        // Create object with default constructor
        return constructor.newInstance();
    }

    /**
     * Poll for connected controller devices
     * and save the joystick.
     * @return true on success
     */
    public static boolean setup()
    {
        // Be aware that creating a new environment is fairly expensive
        try {
            Controller[] controllers = createDefaultEnvironment().getControllers();
            System.out.println(controllers);

            Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
            Controller gamepad = null;
            Component[] components = null;
            EventQueue eventQueue;
            // Run through the list of available input devices and get the gamepad
            for (int i = 0; i < ca.length; i++) {
                if (ca[i].getType().equals(Controller.Type.GAMEPAD)) {
                    gamepad = ca[i];
                }
            }

            //Controller[] controllers = ControllerEnvironment
              //      .getDefaultEnvironment().getControllers();
            for (Controller control : controllers) {
                if (control.getType() == Controller.Type.GAMEPAD) {
                    // found the joystick
                    joystick = control;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @return magnitude (between -1.0 -> +1.0) of X-axis position of joystick
     */
    public static float getXMag()
    {
        joystick.poll();

        // a Component is a button, axis, or slider
        Component[] axes = joystick.getComponents();

        for (Component a : axes)
        {
            // TODO: FOR DEBUGGING
            System.out.println(a.getIdentifier().getName());
            if (a.isAnalog() && a.getIdentifier().getName() == X_AXIS)
            {
                // ie. a range of -1 to 1
                // TODO: check if this is X-axis or Y-axis
                return a.getPollData();
            }
        }

        // default
        return 0;
    }

    /**
     * @return magnitude (-1.0 to +1.0) of Y-axis position
     */
    public static double getYMag()
    {
        joystick.poll();

        // a Component is a button, axis, or slider
        Component[] axes = joystick.getComponents();

        for (Component a : axes)
        {
            System.out.println(a.getName());
            if (a.isAnalog() && a.getIdentifier().getName() == Y_AXIS)
            {
                // ie. a range of -1 to 1
                // TODO: check if this is X-axis or Y-axis
                return a.getPollData();
            }
        }

        // default
        return 0;
    }

    /**
     * FOR DEBUGGING THE CLASS AND DETERMINING RUNTIME CONTROLLER ID's
     * compile in terminal with javac -cp "{path to jinput jar}" JoystickManager.java 
     */
    public static void main(String[] args)
    {
        // TODO: see output
        setup();
        System.out.println(getXMag());

    }
}
