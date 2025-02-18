package eventcalendar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


//Abstract base class for all calendar events.
public abstract class Event implements Comparable<Event> {

    protected String name;
    protected LocalDateTime dateTime;

    // formats date string
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    // Constructs a new Event with the specified name and date/time.

    public Event(String name, LocalDateTime dateTime) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be null or empty");
        }
        this.name = name.trim();
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime cannot be null");
    }

    //Gets the name of the event.
    public abstract String getName();

    //Gets the date and time of the event.
    public LocalDateTime getDateTime() {
        return dateTime;
    }

     //Sets a new date and time for the event.
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime cannot be null");
    }

    //Sets a new name for the event.
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be null or empty");
        }
        this.name = name.trim();
    }

     //Compares this event with another event based on their date/time.
    @Override
    public int compareTo(Event e) {
        return this.dateTime.compareTo(e.getDateTime());
    }

    //Returns a string representation of the event.
    @Override
    public String toString() {
        return String.format("%s - %s",
                name,
                dateTime.format(formatter));
    }


     // Checks if this event is equal to another object.
     //Events are considered equal if they have the same name and date/time.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Event)) return false;
        Event other = (Event) obj;
        return Objects.equals(name, other.name) &&
                Objects.equals(dateTime, other.dateTime);
    }


     //Generates a hash code for this event.
    @Override
    public int hashCode() {
        return Objects.hash(name, dateTime);
    }
}
