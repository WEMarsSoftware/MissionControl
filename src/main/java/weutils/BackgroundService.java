package weutils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.beans.property.IntegerProperty;


/**
 * A BEAUTIFUL timed interval background service
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
