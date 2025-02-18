package eventcalendar;

public interface Completable {
     // Marks the event as complete
    void complete();

    //Checks if the event has been completed.
    boolean isComplete();
}