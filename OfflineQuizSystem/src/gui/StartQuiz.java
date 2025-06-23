package gui;

import javax.swing.*;
import java.awt.*;

public class StartQuiz extends JFrame {

    private JTextField nameField;
    private JComboBox<String> subjectBox;
    private JButton startButton;

    public StartQuiz() {
        setTitle("Start Quiz");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Student Name:"), gbc);

        nameField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0;
        add(nameField, gbc);

        // Subject Label
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Select Subject:"), gbc);

        // Subject Dropdown
        subjectBox = new JComboBox<>(new String[] { "UI components with Swing", "Event handling", "Database Connectivity" });
        gbc.gridx = 1; gbc.gridy = 1;
        add(subjectBox, gbc);

        // Start Button
        startButton = new JButton("Start Quiz");
        gbc.gridx = 1; gbc.gridy = 2;
        add(startButton, gbc);

        // Action
        startButton.addActionListener(e -> {
            String studentName = nameField.getText().trim();
            String subject = (String) subjectBox.getSelectedItem();

            if (studentName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.");
                return;
            }

            if (subject == null || subject.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a subject.");
                return;
            }

            new StudentQuiz(subject, studentName);
            dispose();
        });

        setVisible(true);
    }
}
