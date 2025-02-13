package eventcalendar;

import javax.swing.*;
import java.awt.*;

public class AddEventModal extends JDialog {
    private JTextField nameField;
    private JTextField dateField;
    private JComboBox<String> eventTypeCombo;
    private JTextField locationField;
    private JTextField endDateField;

    public AddEventModal(JFrame parent) {
        super(parent, "Add Event", true);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Event type selection
        eventTypeCombo = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        nameField = new JTextField(20);
        dateField = new JTextField(20);
        locationField = new JTextField(20);
        endDateField = new JTextField(20);

        // Add components
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Event Type:"), gbc);
        gbc.gridx = 1;
        add(eventTypeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Date (yyyy-MM-dd HH:mm):"), gbc);
        gbc.gridx = 1;
        add(dateField, gbc);

        // Add buttons
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        gbc.gridx = 0; gbc.gridy = 5;
        add(addButton, gbc);
        gbc.gridx = 1;
        add(cancelButton, gbc);

        // Add listeners
        addButton.addActionListener(e -> addEvent());
        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(getParent());
    }

    private void addEvent() {
        // Implement event creation logic
        dispose();
    }
}

