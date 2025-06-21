package gui;

import javax.swing.*;
import java.awt.*;
import gui.StudentQuiz;



public class StartQuiz extends JFrame {

    private JComboBox<String> subjectBox;
    private JButton startButton;

    public StartQuiz() {
        setTitle("Take a Quiz");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Subject label
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Select Subject:"), gbc);

        // Dropdown for subject
        subjectBox = new JComboBox<>(new String[] { "Java", "OOP", "WEB" });
        gbc.gridx = 1; gbc.gridy = 0;
        add(subjectBox, gbc);

        // Start button
        startButton = new JButton("Start Quiz");
        gbc.gridx = 1; gbc.gridy = 1;
        add(startButton, gbc);

        startButton.addActionListener(e -> {
            String subject = (String) subjectBox.getSelectedItem();
            if (subject != null) {
                String name = JOptionPane.showInputDialog(this, "Enter your name:");
                if (name != null && !name.trim().isEmpty()) {
                    new StudentQuiz(subject, name.trim());
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid name.");
                }
            }
        });


        setVisible(true);
    }
}
