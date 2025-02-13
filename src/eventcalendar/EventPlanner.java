package eventcalendar;

import javax.swing.*;
import java.time.LocalDateTime;

public class EventPlanner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            EventListPanel eventListPanel = new EventListPanel();
            frame.add(eventListPanel);
            addDefaultEvents(eventListPanel);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void addDefaultEvents(EventListPanel panel) {
        // Add a sample deadline
        panel.addEvent(new Deadline("Submit Report",
                LocalDateTime.now().plusDays(7)));

        // Add a sample meeting
        panel.addEvent(new Meeting("Team Meeting",
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(1),
                "Conference Room A"));
    }
}