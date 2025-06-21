package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class StudentQuiz extends JFrame {
    
    private ArrayList<Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private String subject;
    private String studentName;

    private JLabel questionLabel;
    private JRadioButton[] options = new JRadioButton[4];
    private ButtonGroup optionGroup;
    private JButton nextButton;

    public StudentQuiz(String subject, String studentName)
 {
    	

        this.subject = subject;
        this.studentName = studentName;
        questions = loadQuestions(subject);

        setTitle("Quiz - " + subject);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // North - Question
        questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        // Center - Options
        JPanel optionPanel = new JPanel(new GridLayout(4, 1));
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            optionPanel.add(options[i]);
        }
        add(optionPanel, BorderLayout.CENTER);

        // South - Next Button
        nextButton = new JButton("Next");
        add(nextButton, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> handleNext());

        // Load first question
        if (!questions.isEmpty()) {
            showQuestion(0);
        } else {
            JOptionPane.showMessageDialog(this, "No questions available for subject: " + subject);
            dispose();
        }

        setVisible(true);
    }

    private ArrayList<Question> loadQuestions(String subject) {
        ArrayList<Question> list = new ArrayList<>();
        try (Connection conn = db.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM questions WHERE subject = ?")) {
            stmt.setString(1, subject);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Question(
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_ans")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private void showQuestion(int index) {
        Question q = questions.get(index);
        questionLabel.setText("Q" + (index + 1) + ": " + q.question);
        options[0].setText(q.a);
        options[1].setText(q.b);
        options[2].setText(q.c);
        options[3].setText(q.d);
        optionGroup.clearSelection();
    }

    private void handleNext() {
        if (currentIndex < questions.size()) {
            Question q = questions.get(currentIndex);
            String selected = null;

            for (JRadioButton btn : options) {
                if (btn.isSelected()) {
                    selected = btn.getText();
                    break;
                }
            }

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Please select an option.");
                return;
            }

            if (selected.equalsIgnoreCase(q.correct)) {
                score++;
            }

            currentIndex++;
            if (currentIndex < questions.size()) {
            	try (Connection conn = db.DBConnection.getConnection();
           		     PreparedStatement stmt = conn.prepareStatement(
           		         "INSERT INTO results (student_name, subject, score, total) VALUES (?, ?, ?, ?)"
           		     )) {
           		    stmt.setString(1, studentName);
           		    stmt.setString(2, subject);
           		    stmt.setInt(3, score);
           		    stmt.setInt(4, questions.size());
           		    stmt.executeUpdate();
           		} catch (Exception ex) {
           		    ex.printStackTrace();
           		}
                
            	dispose(); // Close quiz window
            	
            	new QuizSummary(studentName, subject, score, questions.size());

            }
        }
    }

    // Simple class to hold question data
    private static class Question {
        String question, a, b, c, d, correct;

        public Question(String question, String a, String b, String c, String d, String correct) {
            this.question = question;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.correct = correct;
        }
    }
}
