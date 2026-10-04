package com.mycompany.ccdatrcl_finalproject.utils;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    // XAMPP default credentials
    private static final String URL = "jdbc:mysql://localhost:3306/kogmaw_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to save a new appointment to XAMPP
    public static boolean saveAppointment(Appointment app) {
        // Updated query to include the 8th parameter (assessment_phase)
        String query = "INSERT INTO appointments (id, customer_name, device_type, brand, model, reported_issue, appointment_date, assessment_phase) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, app.getId());
            pstmt.setString(2, app.getCustomerName());
            pstmt.setString(3, app.getDeviceType());
            pstmt.setString(4, app.getBrand());
            pstmt.setString(5, app.getModel());
            pstmt.setString(6, app.getReportedIssue());
            pstmt.setString(7, app.getAppointmentDate()); 
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }
}