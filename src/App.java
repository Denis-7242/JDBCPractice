import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.SwingUtilities;

public class App {

    // Database connection method
    public static Connection connect() {
        Connection con = null;
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(".env"));

            String user = props.getProperty("DB_USER");
            String pass = props.getProperty("DB_PASSWORD");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/schooldb",
                    user,
                    pass
            );
        } catch (Exception e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentFrame().setVisible(true);
        });
    }

    // INSERT (Create)
    public static void addStudent(int id, String name, String course) {
        try (Connection con = connect()) {
            if (con == null) return;
            String sql = "INSERT INTO students (id, name, course) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error adding student: " + e.getMessage());
        }
    }

    // UPDATE
    public static void updateStudent(int id, String name, String course) {
        try (Connection con = connect()) {
            if (con == null) return;
            String sql = "UPDATE students SET name=?, course=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, course);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error updating student: " + e.getMessage());
        }
    }

    // DELETE
    public static void deleteStudent(int id) {
        try (Connection con = connect()) {
            if (con == null) return;
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error deleting student: " + e.getMessage());
        }
    }

    // SELECT (Read)
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection con = connect()) {
            if (con == null) return students;
            String sql = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("course")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage());
        }
        return students;
    }
}
