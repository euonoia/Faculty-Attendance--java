import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class NewClass extends JFrame {
    private JTable attendanceTable;
    private JButton submitButton;
    private Connection conn;
    private DefaultTableModel tableModel;

    public NewClass() {
        setTitle("Teacher Attendance System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/attendance", "root", "Polaroid8901@:sql");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed!");
        }

        // Create table model with columns
        String[] columns = {"ID", "Teacher Name", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 2) return Boolean.class;
                return String.class;
            }
        };

        // Create table
        attendanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load teachers from database
        loadTeachers();

        // Submit button
        submitButton = new JButton("Submit Attendance");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAttendance();
            }
        });
        add(submitButton, BorderLayout.SOUTH);
    }

    private void loadTeachers() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM teachers");

            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("name"));
                row.add(Boolean.TRUE); // Default present
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading teachers!");
        }
    }

    private void saveAttendance() {
        try {
            String sql = "INSERT INTO teacher_attendance (teacher_id, status, date) VALUES (?, ?, CURRENT_DATE)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for(int i = 0; i < tableModel.getRowCount(); i++) {
                String teacherId = (String)tableModel.getValueAt(i, 0);
                Boolean status = (Boolean)tableModel.getValueAt(i, 2);
                
                pstmt.setString(1, teacherId);
                pstmt.setString(2, status ? "Present" : "Absent");
                pstmt.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "Attendance saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving attendance!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TeacherAttendance app = new TeacherAttendance();
            app.setVisible(true);
        });
    }
}
