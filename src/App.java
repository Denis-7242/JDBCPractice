import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {

    // Database connection method
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/schooldb",
                    "root", // your MySQL username
                    "20murithi21" // your MySQL password
            );
        } catch (Exception e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        Connection con = connect();

        if (con != null) {
            System.out.println("Database Connected Successfully\n");

            // 1️⃣ INSERT Operation
            insertStudent(con, 4, "Alice", "Information Security");

            // 2️⃣ SELECT Operation
            System.out.println("All Students:");
            viewStudents(con);

            // 3️⃣ UPDATE Operation
            updateStudentCourse(con, 1, "Cyber Security");

            // 4️⃣ DELETE Operation
            deleteStudent(con, 2);

            // Final View
            System.out.println("\nAfter Update & Delete:");
            viewStudents(con);

            try { con.close(); } catch (Exception e) {}
        }
    }

    // INSERT
    public static void insertStudent(Connection con, int id, String name, String course) {
        try {
            String sql = "INSERT INTO students VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.executeUpdate();
            System.out.println("Inserted Student: " + name);
        } catch (Exception e) {
            System.out.println("Insert Failed");
            e.printStackTrace();
        }
    }

    // SELECT
    public static void viewStudents(Connection con) {
        try {
            String sql = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("course")
                );
            }
        } catch (Exception e) {
            System.out.println("Select Failed");
            e.printStackTrace();
        }
    }

    // UPDATE
    public static void updateStudentCourse(Connection con, int id, String newCourse) {
        try {
            String sql = "UPDATE students SET course=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newCourse);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Updated Student ID: " + id + " to " + newCourse);
        } catch (Exception e) {
            System.out.println("Update Failed");
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteStudent(Connection con, int id) {
        try {
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Deleted Student ID: " + id);
        } catch (Exception e) {
            System.out.println("Delete Failed");
            e.printStackTrace();
        }
    }
}
