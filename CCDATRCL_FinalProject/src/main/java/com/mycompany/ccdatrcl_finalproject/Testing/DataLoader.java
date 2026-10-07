package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList;

// Required imports for file reading and date parsing
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

                // Handle standard format (7 fields: Brand and Model separated by |)
                if (fields.length == 7) {
                    String id = fields[0].trim();
                    String customerName = fields[1].trim();
                    String deviceType = fields[2].trim();
                    String brand = fields[3].trim();
                    String model = fields[4].trim();
                    String reportedIssue = fields[5].trim();
                    LocalDate appointmentDate = LocalDate.parse(fields[6].trim(), formatter);

                    Appointment app = new Appointment(id, customerName, deviceType, brand, model, reportedIssue, appointmentDate.toString(), "Pending");
                    appointmentList.insert(app);
                    count++;
                } 
                // Handle Custom PC format (6 fields: Brand and Model combined with /)
                else if (fields.length == 6) {
                    String id = fields[0].trim();
                    String customerName = fields[1].trim();
                    String deviceType = fields[2].trim();
                    
                    String[] brandModel = fields[3].split("/", 2);
                    String brand = brandModel[0].trim();
                    String model = (brandModel.length > 1) ? brandModel[1].trim() : "Unknown";
                    
                    String reportedIssue = fields[4].trim();
                    LocalDate appointmentDate = LocalDate.parse(fields[5].trim(), formatter);

                    Appointment app = new Appointment(id, customerName, deviceType, brand, model, reportedIssue, appointmentDate.toString(), "Pending");
                    appointmentList.insert(app);
                    count++;
                } 
                else {
                    System.out.println("Skipped malformed line: " + line);
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
    
    public static Appointment[] generateSyntheticData(int size) {
        Appointment[] syntheticData = new Appointment[size];
        for (int i = 0; i < size; i++) {
            String id = String.format("A%04d", i + 1);
            String customerName = "Customer" + (i + 1);
            String deviceType = "DeviceType" + ((i % 5) + 1);
            String brand = "Brand" + ((i % 3) + 1);
            String model = "Model" + ((i % 4) + 1);
            String reportedIssue = "Issue" + ((i % 6) + 1);
            String appointmentDate = LocalDate.now().plusDays(i % 30).toString();

            // Updated to include the 8th parameter for synthetic data[cite: 15]
            syntheticData[i] = new Appointment(id, customerName, deviceType, brand, model, reportedIssue, appointmentDate, "Pending");
        }
        return syntheticData;
    }
}