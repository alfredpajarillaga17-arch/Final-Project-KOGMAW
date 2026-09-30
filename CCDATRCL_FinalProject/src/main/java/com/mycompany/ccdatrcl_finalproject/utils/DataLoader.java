package com.mycompany.ccdatrcl_finalproject.utils;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DataLoader {
    public static CustomLinkedList loadAppointments(String filePath) {
            CustomLinkedList appointmentList = new CustomLinkedList();
            int count = 0;

            File file = new File(filePath);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.trim().isEmpty()) continue; 
                    
                    String[] fields = line.split("\\|");

                    if (fields.length == 7) {
                        String id = fields[0].trim();
                        String customerName = fields[1].trim();
                        String deviceType = fields[2].trim();
                        String brand = fields[3].trim();
                        String model = fields[4].trim();
                        String reportedIssue = fields[5].trim();
                        LocalDate appointmentDate = LocalDate.parse(fields[6].trim(), formatter);

                        Appointment app = new Appointment(id, customerName, deviceType, brand, model, reportedIssue, appointmentDate.toString());
                        
                        // Dynamically append to the custom linked list (grows automatically for 5000+ records)
                        appointmentList.insert(app);
                        count++;
                    }
                }
                System.out.println("Successfully loaded " + count + " records dynamically from " + filePath);
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + filePath);
            } catch (Exception e) {
                System.err.println("Error loading data from file: " + e.getMessage());
            }

            return appointmentList;
        }
}

