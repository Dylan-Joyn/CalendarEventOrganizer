package eventcalendar;
//updated
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Objects;


 //Represents a meeting event with a start time, end time, and location.
 //A meeting is a specific type of event that has duration and can be marked as complete.
public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;


     // Constructs a new Meeting event.
    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start);
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        this.endDateTime = end;
        this.location = Objects.requireNonNull(location, "Location cannot be null");
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


     //Gets the end date and time of the meeting.
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

     // Calculates the duration of the meeting.
    public Duration getDuration() {
        return Duration.between(dateTime, endDateTime);
    }

    public String getLocation() {
        return location;
    }

    //Sets the end date and time of the meeting.
    public void setEndDateTime(LocalDateTime end) {
        if (end.isBefore(dateTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        this.endDateTime = end;
    }

    public void setLocation(String location) {
        this.location = Objects.requireNonNull(location, "Location cannot be null");
    }

    @Override
    public String toString() {
        return String.format("%s (Meeting) - Location: %s, Duration: %d minutes%s",
                super.toString(),
                location,
                getDuration().toMinutes(),
                complete ? " - Completed" : "");
    }
}
