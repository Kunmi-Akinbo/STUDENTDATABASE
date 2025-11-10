package com.example;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main{
    static Connection connection;
    // Establishes connection to PostgreSQL and executes the CRUD operations
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:postgresql://localhost:5433/Students";
        String user = "postgres";
        String password = "admin";
        try { 
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL successfully");
            getAllStudents();
            addStudent("Kunmi", "Akinbo", "kunmi@example.com", "2024-10-07");
            getAllStudents();
            updateStudentEmail(2, "newjane@example.com");
            getAllStudents();
            deleteStudent(1);
            getAllStudents();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Retrieves and displays all records from the student tables
    public static void getAllStudents() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM students");
            System.out.println("List of all students:");
            while(result.next()) {
                System.out.println("ID: " + result.getInt("student_id") + ", FullName: " + result.getString("first_name") + " " + result.getString("last_name") + ", Email: " + result.getString("email") + ", EnrollmentDate: " + result.getDate("enrollment_date"));
            }
            System.out.println();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Inserts a new student record into the students table
    public static void addStudent(String firstName, String lastName, String email, String enrollmentDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setDate(4, Date.valueOf(enrollmentDate));
            statement.executeUpdate();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Updates the email address of a student with the specified student ID
    public static void updateStudentEmail(int studentId, String newEmail) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE students SET email = ? WHERE student_id = ?");
            statement.setString(1, newEmail);
            statement.setInt(2, studentId);
            statement.executeUpdate();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Deletes the record of a student with the specified student ID
    public static void deleteStudent(int studentId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE student_id = ?");
            statement.setInt(1, studentId);
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
            
        