package gui;

import model.Question;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class StudentQuiz extends JFrame {

    private String subject;
    private String studentName;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> selectedAnswers = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JRadioButton aBtn, bBtn, cBtn, dBtn;
    private ButtonGroup group;
    private JButton nextBtn;

    public StudentQuiz(String subject, String studentName) {
        this.subject = subject;
        this.studentName = studentName;

        setTitle("Quiz - " + subject);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(questionLabel, BorderLayout.NORTH);

        aBtn = new JRadioButton();
        bBtn = new JRadioButton();
        cBtn = new JRadioButton();
        dBtn = new JRadioButton();

        group = new ButtonGroup();
        group.add(aBtn);
        group.add(bBtn);
        group.add(cBtn);
        group.add(dBtn);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionsPanel.add(aBtn);
        optionsPanel.add(bBtn);
        optionsPanel.add(cBtn);
        optionsPanel.add(dBtn);
        add(optionsPanel, BorderLayout.CENTER);

        nextBtn = new JButton("Next");
        add(nextBtn, BorderLayout.SOUTH);

        nextBtn.addActionListener(e -> handleNext());

        loadQuestions();
        showQuestion();

        setVisible(true);
    }

    private void loadQuestions() {
        try (Connection conn = db.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM questions WHERE subject = ?")) {

            stmt.setString(1, subject);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                questions.add(new Question(
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_ans")
                ));
            }

            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No questions found for this subject.");
                dispose();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load questions.");
            dispose();
        }
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) return;

        Question q = questions.get(currentIndex);
        questionLabel.setText("Q" + (currentIndex + 1) + ": " + q.question);
        aBtn.setText(q.a);
        bBtn.setText(q.b);
        cBtn.setText(q.c);
        dBtn.setText(q.d);
        group.clearSelection();
    }

    private void handleNext() {
        if (group.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Please select an option.");
            return;
        }

        String selected = null;
        if (aBtn.isSelected()) selected = aBtn.getText();
        else if (bBtn.isSelected()) selected = bBtn.getText();
        else if (cBtn.isSelected()) selected = cBtn.getText();
        else if (dBtn.isSelected()) selected = dBtn.getText();

        selectedAnswers.add(selected);

        Question q = questions.get(currentIndex);
        if (selected.equalsIgnoreCase(q.correct)) {
            score++;
        }

        currentIndex++;

        if (currentIndex >= questions.size()) {
            // Save result
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

            // Show summary + review
            dispose();
            new QuizSummary(studentName, subject, score, questions.size());
            new QuizReview(questions, selectedAnswers);
        } else {
            showQuestion();
        }
    }
}
