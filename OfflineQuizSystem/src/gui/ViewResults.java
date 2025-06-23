package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewResults extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ViewResults() {
        setTitle("📊 Quiz Results");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Student Name", "Subject", "Score", "Total"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);

        loadResults();

        setVisible(true);
    }

    private void loadResults() {
        try (Connection conn = db.DBConnection.getConnection();
             Statement stmt = conn.createStatement();
        		ResultSet rs = stmt.executeQuery("SELECT * FROM results ORDER BY id DESC")) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("student_name"),
                    rs.getString("subject"),
                    rs.getInt("score"),
                    rs.getInt("total"),
                    
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to load results.");
        }
    }
}
