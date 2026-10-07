package com.mycompany.ccdatrcl_finalproject.utils;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // XAMPP default credentials
    private static final String URL = "jdbc:mysql://localhost:3306/kogmaw_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    // Static block to ensure the JDBC driver loads
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Ensure the connector JAR is added to your library.");
        }
    }

    // 1. Establish connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 2. Update phase for Quick Edit feature
    public static boolean updateAppointmentPhase(String id, String newPhase) {
        String query = "UPDATE appointments SET assessment_phase = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPhase);
            pstmt.setString(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Database Update Error: " + e.getMessage());
            return false;
        }
    }
    
    public static CustomLinkedList fetchAllAppointments() {
        CustomLinkedList appointmentList = new CustomLinkedList();
        String query = "SELECT * FROM appointments";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                Appointment app = new Appointment(
                    rs.getString("id"),
                    rs.getString("customer_name"),
                    rs.getString("device_type"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("reported_issue"),
                    rs.getString("appointment_date"),
                    rs.getString("assessment_phase")
                );
                appointmentList.insert(app);
            }
            System.out.println("Successfully fetched records from Database.");
        } catch (SQLException e) {
            System.err.println("Database Fetch Error: " + e.getMessage());
        }
        
        return appointmentList;
    }
}