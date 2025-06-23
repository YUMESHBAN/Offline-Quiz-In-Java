package gui;

import model.Question;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class QuizReview extends JFrame {

    public QuizReview(ArrayList<Question> questions, ArrayList<String> selectedAnswers) {
        setTitle("📖 Quiz Review");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Question", "Your Answer", "Correct Answer", "Result"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String yourAnswer = selectedAnswers.get(i);
            String result = yourAnswer.equalsIgnoreCase(q.correct) ? "✅ Correct" : "❌ Wrong";

            model.addRow(new Object[]{q.question, yourAnswer, q.correct, result});
        }

        setVisible(true);
    }
}
