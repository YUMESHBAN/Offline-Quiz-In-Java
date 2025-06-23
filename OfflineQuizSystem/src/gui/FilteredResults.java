package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class FilteredResults extends JFrame {

    private JTextField nameField;
    private JComboBox<String> subjectBox;
    private JButton filterButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public FilteredResults() {
        setTitle("Filter Quiz Results");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel for filters
        JPanel filterPanel = new JPanel(new FlowLayout());

        filterPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField(10);
        filterPanel.add(nameField);

        filterPanel.add(new JLabel("Subject:"));
        subjectBox = new JComboBox<>(new String[] { "All", "UI components with Swing", "Event handling", "Database Connectivity" });
        filterPanel.add(subjectBox);

        filterButton = new JButton("Filter Results");
        filterPanel.add(filterButton);

        add(filterPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Student Name", "Subject", "Score", "Total" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load all results initially
        loadResults("", "All");

        filterButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String subject = (String) subjectBox.getSelectedItem();
            loadResults(name, subject);
        });

        setVisible(true);
    }

    private void loadResults(String studentName, String subject) {
        tableModel.setRowCount(0); // clear table

        String query = "SELECT * FROM results WHERE 1=1";
        if (!studentName.isEmpty()) {
            query += " AND student_name LIKE ?";
        }
        if (!subject.equals("All")) {
            query += " AND subject = ?";
        }

        try (Connection conn = db.DBConnection.getConnection();
        	     PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (!studentName.isEmpty()) {
                stmt.setString(paramIndex++, "%" + studentName + "%");
            }
            if (!subject.equals("All")) {
                stmt.setString(paramIndex, subject);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("student_name"),
                    rs.getString("subject"),
                    rs.getInt("score"),
                    rs.getInt("total")
                };
                tableModel.addRow(row);
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No results found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error loading results.");
        }
    }
}
