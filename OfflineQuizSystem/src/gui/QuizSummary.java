package gui;

import javax.swing.*;
import java.awt.*;

public class QuizSummary extends JFrame {

    public QuizSummary(String name, String subject, int score, int total) {
        setTitle("Quiz Summary");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        add(new JLabel("🎉 Quiz Completed!", SwingConstants.CENTER));
        add(new JLabel("👤 Student: " + name, SwingConstants.CENTER));
        add(new JLabel("📘 Subject: " + subject, SwingConstants.CENTER));
        add(new JLabel("✅ Score: " + score + " / " + total, SwingConstants.CENTER));

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);
        
        JButton restartBtn = new JButton("🔁 Restart Quiz");
        restartBtn.addActionListener(e -> {
            dispose();
            new StartQuiz(); // Restart quiz from subject selection
        });

        add(restartBtn);

        

        setVisible(true);
    }
}
