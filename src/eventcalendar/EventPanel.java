package eventcalendar;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Event event;
    private final JButton completeButton;
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);

    public EventPanel(Event event) {
        this.event = event;
        this.completeButton = new JButton("Mark Complete");

        setupPanel();
        initializeComponents();
    }

    private void setupPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(BACKGROUND_COLOR);
    }

    private void initializeComponents() {
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBackground(BACKGROUND_COLOR);

        JLabel nameLabel = new JLabel(String.format("<html><b>%s</b></html>",
                event.getName()));
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 14f));
        infoPanel.add(nameLabel);
        infoPanel.add(new JLabel("Date: " + event.getDateTime().format(TIME_FORMATTER)));

        if (event instanceof Meeting) {
            Meeting meeting = (Meeting) event;
            infoPanel.add(new JLabel("Location: " + meeting.getLocation()));
            infoPanel.add(new JLabel(String.format("Duration: %d minutes",
                    meeting.getDuration().toMinutes())));
            infoPanel.add(new JLabel("End Time: " +
                    meeting.getEndDateTime().format(TIME_FORMATTER)));
        }

        if (event instanceof Completable) {
            Completable completableEvent = (Completable) event;
            JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            statusPanel.setBackground(BACKGROUND_COLOR);

            completeButton.setEnabled(!completableEvent.isComplete());
            completeButton.addActionListener(e -> {
                completableEvent.complete();
                completeButton.setEnabled(false);
                updateCompletionStatus();
            });

            statusPanel.add(completeButton);
            add(statusPanel, BorderLayout.SOUTH);
        }

        add(infoPanel, BorderLayout.CENTER);
        updateCompletionStatus();
    }

    private void updateCompletionStatus() {
        if (event instanceof Completable && ((Completable) event).isComplete()) {
            setBackground(new Color(220, 255, 220));
        }
    }
}