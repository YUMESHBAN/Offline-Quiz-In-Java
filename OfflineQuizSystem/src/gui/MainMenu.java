package gui;


import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Online Quiz Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Panel to hold buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Buttons
        JButton adminButton = new JButton("Admin Panel");
        JButton studentButton = new JButton("Take Quiz");
        JButton exitButton = new JButton("Exit");

        // Add buttons to panel
        panel.add(adminButton);
        panel.add(studentButton);
        panel.add(exitButton);

        // Add panel to frame
        add(panel);

        // Event handlers
        adminButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Admin panel opens here");
            // You will later open: new AddQuestion() or new ViewQuestions()
        });

        studentButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Student quiz opens here");
            // You will later open: new StudentQuiz()
        });

        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        // Use a proper theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Launch main window
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
