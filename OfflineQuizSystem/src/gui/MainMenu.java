package gui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Advanced Java Quiz System");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        JLabel titleLabel = new JLabel("Advanced Java Programming", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

    
        JPanel adminPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin Panel"));

        JButton addQuestionButton = new JButton("➕ Add Question");
        JButton viewResultsButton = new JButton("📋 View All Results");
        JButton filterResultsButton = new JButton("🔍 Filter Results");

        adminPanel.add(addQuestionButton);
        adminPanel.add(viewResultsButton);
        adminPanel.add(filterResultsButton); 

   
        JPanel studentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        studentPanel.setBorder(BorderFactory.createTitledBorder("Student Panel"));

        JButton studentButton = new JButton("📝 Take Java Quiz");
        studentPanel.add(studentButton);

        
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        centerPanel.add(adminPanel);
        centerPanel.add(studentPanel);

        add(centerPanel, BorderLayout.CENTER);

       
        addQuestionButton.addActionListener(e -> new AddQuestion());
        viewResultsButton.addActionListener(e -> new ViewResults());
        filterResultsButton.addActionListener(e -> new FilteredResults()); // 👈 action for new button
        studentButton.addActionListener(e -> new StartQuiz());

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
