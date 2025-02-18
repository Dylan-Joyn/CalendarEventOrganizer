package eventcalendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddEventModal extends JDialog {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final JTextField nameField;
    private final JTextField dateField;
    private final JTextField locationField;
    private final JTextField endDateField;
    private final JComboBox<String> eventTypeCombo;
    private final EventListPanel parentPanel;
    private final JPanel meetingPanel;

    public AddEventModal(JFrame parent, EventListPanel parentPanel) {
        super(parent, "Add Event", true);
        this.parentPanel = parentPanel;

        nameField = new JTextField(20);
        dateField = new JTextField(20);
        locationField = new JTextField(20);
        endDateField = new JTextField(20);
        eventTypeCombo = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        meetingPanel = new JPanel(new GridBagLayout());

        setupDialog();
    }

    private void setupDialog() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add main components
        addFormRow("Event Type:", eventTypeCombo, 0, gbc);
        addFormRow("Name:", nameField, 1, gbc);
        addFormRow("Date (yyyy-MM-dd HH:mm):", dateField, 2, gbc);

        // Setup meeting panel
        addFormRow("End Date (yyyy-MM-dd HH:mm):", endDateField, 0, gbc, meetingPanel);
        addFormRow("Location:", locationField, 1, gbc, meetingPanel);

        // Add meeting panel to main dialog
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(meetingPanel, gbc);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 4;
        add(buttonPanel, gbc);

        // Add listeners
        eventTypeCombo.addActionListener(e -> updateFieldsVisibility());
        addButton.addActionListener(e -> createEvent());
        cancelButton.addActionListener(e -> dispose());

        // Initial visibility
        updateFieldsVisibility();

        // Dialog properties
        pack();
        setLocationRelativeTo(getParent());
    }

    private void addFormRow(String label, JComponent component, int row, GridBagConstraints gbc) {
        addFormRow(label, component, row, gbc, this);
    }

    private void addFormRow(String label, JComponent component, int row, GridBagConstraints gbc, Container container) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        container.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        container.add(component, gbc);
    }

    private void updateFieldsVisibility() {
        boolean isMeeting = eventTypeCombo.getSelectedItem().equals("Meeting");
        meetingPanel.setVisible(isMeeting);
        pack();
    }

    private void createEvent() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name");
                return;
            }

            LocalDateTime dateTime = LocalDateTime.parse(dateField.getText(), FORMATTER);

            if (eventTypeCombo.getSelectedItem().equals("Deadline")) {
                parentPanel.addEvent(new Deadline(name, dateTime));
            } else {
                String location = locationField.getText().trim();
                if (location.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a location");
                    return;
                }

                LocalDateTime endDateTime = LocalDateTime.parse(endDateField.getText(), FORMATTER);
                if (endDateTime.isBefore(dateTime)) {
                    JOptionPane.showMessageDialog(this, "End time cannot be before start time");
                    return;
                }

                parentPanel.addEvent(new Meeting(name, dateTime, endDateTime, location));
            }
            dispose();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid date/time in format: yyyy-MM-dd HH:mm");
        }
    }
}