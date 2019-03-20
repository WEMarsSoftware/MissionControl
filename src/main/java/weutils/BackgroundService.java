package weutils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.beans.property.IntegerProperty;

/**
 * A BEAUTIFUL timed interval background service, that extends JavaFx's
 * Scheduled Service, but also allows use of external methods outside of class
 *
 * USAGE:
 * 1) Define globally
 *          public static BackgroundService service;
 *
 * 2) Initialize in init/open function
 *          espService = new BackgroundService();
 *
 * 3) Set Duration
 *          service.setPeriod(Duration.seconds(1));
 *
 * 4) Assign finish method
 *  // call roverHTTPGet() when a task cycle has complete
 *         service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
 *
 *             @Override
 *             public void handle(WorkerStateEvent t) {
 *                      methodInParentClass();
 *               }
 *          });
 *
 *  5) Start -> service.start();
 */

public class BackgroundService extends ScheduledService<Integer> {
    private IntegerProperty ts = new SimpleIntegerProperty();

    public final void setTicks(Integer value) {
        ts.set(value);
    }

    public final Integer getTicks() {
        return ts.get();
    }

    public final IntegerProperty ticks() {
        return ts;
    }

    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            protected Integer call() {
                ts.set(getTicks() + 1); // Increase the tick in the timer
                return getTicks();
            }
        };
    }
}
