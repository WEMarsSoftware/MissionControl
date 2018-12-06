package weutils;

import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Controller;
import java.lang.*;

public class JoystickManager implements Runnable {
    private Controller[] controllers;

    public JoystickManager() {
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for( Controller c : controllers) {
            System.out.println("Controller" + c.getName());
        }
    }

    @Override
    public void run() {

    }

}
