package eventcalendar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel that displays a list of events and provides controls for
 * managing and filtering them.
 */
public class EventListPanel extends JPanel {
    private final ArrayList<Event> events;
    private final JPanel controlPanel;
    private final JPanel displayPanel;
    private final JComboBox<String> sortDropDown;
    private final JCheckBox hideCompletedCheckbox;
    private final JCheckBox hideDeadlinesCheckbox;
    private final JCheckBox hideMeetingsCheckbox;
    private final JButton addEventButton;

    public EventListPanel() {
        events = new ArrayList<>();
        controlPanel = new JPanel();
        displayPanel = new JPanel();
        sortDropDown = new JComboBox<>(new String[]{
                "Sort by Name",
                "Sort by Date",
                "Sort by Name (Reverse)",
                "Sort by Date (Reverse)"
        });
        hideCompletedCheckbox = new JCheckBox("Hide Completed");
        hideDeadlinesCheckbox = new JCheckBox("Hide Deadlines");
        hideMeetingsCheckbox = new JCheckBox("Hide Meetings");
        addEventButton = new JButton("Add Event");

        setupPanel();
        initializeComponents();
        setupListeners();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
    }

    private void initializeComponents() {
        // Setup control panel
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(sortDropDown);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(hideCompletedCheckbox);
        controlPanel.add(hideDeadlinesCheckbox);
        controlPanel.add(hideMeetingsCheckbox);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(addEventButton);

        // Add scroll pane for display panel
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupListeners() {
        sortDropDown.addActionListener(e -> refreshDisplay());
        hideCompletedCheckbox.addActionListener(e -> refreshDisplay());
        hideDeadlinesCheckbox.addActionListener(e -> refreshDisplay());
        hideMeetingsCheckbox.addActionListener(e -> refreshDisplay());
        addEventButton.addActionListener(e -> showAddEventDialog());
    }

    private void showAddEventDialog() {
        AddEventModal dialog = new AddEventModal(
                (JFrame) SwingUtilities.getWindowAncestor(this), this);
        dialog.setVisible(true);
    }

    /**
     * Adds a new event to the calendar and refreshes the display.
     *
     * @param event the event to add
     */
    public void addEvent(Event event) {
        events.add(event);
        refreshDisplay();
    }

    private void refreshDisplay() {
        displayPanel.removeAll();
        List<Event> filteredEvents = events.stream()
                .filter(this::shouldShowEvent)
                .sorted(getSelectedComparator())
                .collect(Collectors.toList());

        for (Event event : filteredEvents) {
            EventPanel eventPanel = new EventPanel(event);
            eventPanel.setMaximumSize(new Dimension(
                    Integer.MAX_VALUE,
                    eventPanel.getPreferredSize().height));
            displayPanel.add(Box.createVerticalStrut(10));
            displayPanel.add(eventPanel);
        }
        displayPanel.add(Box.createVerticalStrut(10));

        revalidate();
        repaint();
    }

    private boolean shouldShowEvent(Event event) {
        if (hideCompletedCheckbox.isSelected() &&
                event instanceof Completable &&
                ((Completable) event).isComplete()) {
            return false;
        }
        if (hideDeadlinesCheckbox.isSelected() && event instanceof Deadline) {
            return false;
        }
        if (hideMeetingsCheckbox.isSelected() && event instanceof Meeting) {
            return false;
        }
        return true;
    }

    private Comparator<Event> getSelectedComparator() {
        String selected = (String) sortDropDown.getSelectedItem();
        Comparator<Event> comparator = Comparator.comparing(Event::getDateTime);

        if (selected != null) {
            switch (selected) {
                case "Sort by Name":
                    comparator = Comparator.comparing(Event::getName);
                    break;
                case "Sort by Name (Reverse)":
                    comparator = Comparator.comparing(Event::getName).reversed();
                    break;
                case "Sort by Date (Reverse)":
                    comparator = Comparator.comparing(Event::getDateTime).reversed();
                    break;
                default:
                    // Default is already sort by date
                    break;
            }
        }
        return comparator;
    }
}
