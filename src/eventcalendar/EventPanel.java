package eventcalendar;

import javax.swing.*;
import java.awt.*;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;

    public EventPanel(Event event) {
        this.event = event;
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        // Create main info panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.add(new JLabel("Name: " + event.getName()));
        infoPanel.add(new JLabel("Date: " + event.getDateTime().toString()));

        // Add specific info for Meeting type
        if (event instanceof Meeting) {
            Meeting meeting = (Meeting) event;
            infoPanel.add(new JLabel("Location: " + meeting.getLocation()));
            infoPanel.add(new JLabel("Duration: " + meeting.getDuration().toHours() + " hours"));
        }

        // Add complete button for Completable events
        if (event instanceof Completable) {
            completeButton = new JButton("Mark Complete");
            completeButton.addActionListener(e -> {
                ((Completable) event).complete();
                completeButton.setEnabled(false);
            });
            add(completeButton, BorderLayout.SOUTH);
        }

        add(infoPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
