import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentFrame extends JFrame {
    private JTextField idField, nameField, courseField;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentFrame() {
        // Make it look like the native OS
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        setTitle("Student Manager");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94)); // Dark Blue-Grey
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel titleLabel = new JLabel("🎓 Student Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Side Panel (Input Form) ---
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sidePanel.setBackground(new Color(236, 240, 241)); // Light Grey
        sidePanel.setPreferredSize(new Dimension(250, 0));

        // Fields
        idField = createStyledTextField();
        nameField = createStyledTextField();
        courseField = createStyledTextField();

        sidePanel.add(createLabel("Student ID:"));
        sidePanel.add(idField);
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(createLabel("Name:"));
        sidePanel.add(nameField);
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(createLabel("Course:"));
        sidePanel.add(courseField);
        sidePanel.add(Box.createVerticalStrut(30));

        // Buttons
        JButton addButton = createStyledButton("Add Student", new Color(46, 204, 113));
        JButton updateButton = createStyledButton("Update", new Color(52, 152, 219));
        JButton deleteButton = createStyledButton("Delete", new Color(231, 76, 60));
        JButton clearButton = createStyledButton("Clear Fields", new Color(149, 165, 166));

        sidePanel.add(addButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(updateButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(deleteButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(clearButton);

        add(sidePanel, BorderLayout.WEST);

        // --- Center Panel: Table ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Course"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Disable direct table editing
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(189, 195, 199));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- Action Listeners ---
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());
        
        // Table Row Click Listener
        table.getSelectionModel().addListSelectionListener(e -> selectStudent());

        // Initial Load
        loadStudents();
    }

    private void addStudent() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();

        if (idText.isEmpty() || name.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            App.addStudent(id, name, course);
            clearFields();
            loadStudents();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateStudent() {
        String idText = idField.getText();
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a student from the table first.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        App.updateStudent(Integer.parseInt(idText), name, course);
        clearFields();
        loadStudents();
    }

    private void deleteStudent() {
        String idText = idField.getText();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a student from the table first.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            App.deleteStudent(Integer.parseInt(idText));
            clearFields();
            loadStudents();
        }
    }

    private void selectStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            courseField.setText(tableModel.getValueAt(selectedRow, 2).toString());
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0); // Clear existing data
        List<Student> students = App.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getCourse()});
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        table.clearSelection();
    }

    // Helper methods for styling
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Ensure color shows on some LookAndFeels
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        return btn;
    }
}