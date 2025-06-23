package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddQuestion extends JFrame {

    private JComboBox<String> subjectBox;
    private JTextArea questionArea;
    private JTextField optionA, optionB, optionC, optionD;
    private JComboBox<String> correctAnswerBox;
    private JButton saveButton;

    private void saveQuestion() {
        String subject = (String) subjectBox.getSelectedItem();
        String question = questionArea.getText().trim();
        String a = optionA.getText().trim();
        String b = optionB.getText().trim();
        String c = optionC.getText().trim();
        String d = optionD.getText().trim();
        String correct = switch (correctAnswerBox.getSelectedIndex()) {
            case 0 -> a;
            case 1 -> b;
            case 2 -> c;
            case 3 -> d;
            default -> "";
        };

        // Simple validation
        if (subject == null || subject.isEmpty() || question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Please fill all fields!");
            return;
        }

        // Save to database
        try (Connection conn = db.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("""
                 INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_ans, subject)
                 VALUES (?, ?, ?, ?, ?, ?, ?)
             """)) {

            stmt.setString(1, question);
            stmt.setString(2, a);
            stmt.setString(3, b);
            stmt.setString(4, c);
            stmt.setString(5, d);
            stmt.setString(6, correct);
            stmt.setString(7, subject);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Question saved!");

            // Clear form
            questionArea.setText("");
            optionA.setText("");
            optionB.setText("");
            optionC.setText("");
            optionD.setText("");
            subjectBox.setSelectedIndex(0);
            correctAnswerBox.setSelectedIndex(0);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to save question.");
        }
    }

    public AddQuestion() {
        setTitle("Add New Question");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

      
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Subject:"), gbc);

        subjectBox = new JComboBox<>(new String[] { "UI components with Swing", "Event handling", "Database Connectivity" });
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        add(subjectBox, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Question:"), gbc);

        questionArea = new JTextArea(4, 20);
        JScrollPane scroll = new JScrollPane(questionArea);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        add(scroll, gbc);

        // Option A
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        add(new JLabel("Option A:"), gbc);
        optionA = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        add(optionA, gbc);

        // Option B
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Option B:"), gbc);
        optionB = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        add(optionB, gbc);

        // Option C
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Option C:"), gbc);
        optionC = new JTextField();
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        add(optionC, gbc);

        // Option D
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Option D:"), gbc);
        optionD = new JTextField();
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2;
        add(optionD, gbc);

        // Correct Answer
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        add(new JLabel("Correct Answer:"), gbc);

        correctAnswerBox = new JComboBox<>(new String[]{"Option A", "Option B", "Option C", "Option D"});
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2;
        add(correctAnswerBox, gbc);

        // Save Button
        saveButton = new JButton("Save Question");
        gbc.gridx = 1; gbc.gridy = 7; gbc.gridwidth = 2;
        add(saveButton, gbc);

        saveButton.addActionListener(e -> saveQuestion());

        setVisible(true);
    }
}
