package eventcalendar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterDisplay;
    private JButton addEventButton;

    public EventListPanel() {
        events = new ArrayList<>();
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        // Initialize control panel
        controlPanel = new JPanel();
        String[] sortOptions = {"Sort by Name", "Sort by Date", "Sort by Name (Reverse)", "Sort by Date (Reverse)"};
        sortDropDown = new JComboBox<>(sortOptions);
        filterDisplay = new JCheckBox("Hide Completed");
        addEventButton = new JButton("Add Event");

        controlPanel.add(sortDropDown);
        controlPanel.add(filterDisplay);
        controlPanel.add(addEventButton);

        // Initialize display panel
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        // Add panels to main panel
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayPanel), BorderLayout.CENTER);

        // Add listeners
        sortDropDown.addActionListener(e -> sortEvents());
        filterDisplay.addActionListener(e -> filterEvents());
        addEventButton.addActionListener(e -> showAddEventDialog());
    }

    private void sortEvents() {
        // Implement sorting logic
    }

    private void filterEvents() {
        // Implement filtering logic
    }

    private void showAddEventDialog() {
        AddEventModal dialog = new AddEventModal((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
    }

    public void addEvent(Event event) {
        events.add(event);
        displayPanel.add(new EventPanel(event));
        revalidate();
        repaint();
    }
}
