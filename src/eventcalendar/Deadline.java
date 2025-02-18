package eventcalendar;

import java.time.LocalDateTime;


 // A deadline is a specific type of event that can be marked as complete.
public class Deadline extends Event implements Completable {
    private boolean complete;

    //Constructs a new Deadline event.
    public Deadline(String name, LocalDateTime deadline) {
        super(name, deadline);
        this.complete = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void complete() {
        this.complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return String.format("%s (Deadline) - %s", super.toString(),
                complete ? "Completed" : "Pending");
    }
}
