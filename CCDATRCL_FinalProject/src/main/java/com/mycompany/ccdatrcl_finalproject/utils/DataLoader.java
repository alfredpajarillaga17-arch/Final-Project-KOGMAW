package com.mycompany.ccdatrcl_finalproject.utils;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DataLoader {
    public static Appointment[] loadAppointments(String filePath, int maxRecords) {
        Appointment[] records = new Appointment[maxRecords];
        int count = 0;

        File file = new File(filePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try(Scanner scanner = new Scanner(file)){
            while(scanner.hasNextLine() && count < maxRecords) {
                String line = scanner.nextLine();
                String[] fields = line.split("\\|");

                if(fields.length == 7) {
                    String id = fields[0].trim();
                    String customerName = fields[1].trim();
                    String deviceType = fields[2].trim();
                    String brand = fields[3].trim();
                    String model = fields[4].trim();
                    String reportedIssue = fields[5].trim();
                    LocalDate appointmentDate = LocalDate.parse(fields[6].trim(), formatter);

                    records[count] = new Appointment(id, customerName, deviceType, brand, model, reportedIssue, appointmentDate.toString());
                }
                count++;
            }
            System.out.println("Loaded " + count + " records from " + filePath);
        } catch(FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch(Exception e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }

        Appointment[] finalRecords = new Appointment[count];
        System.arraycopy(records, 0, finalRecords, 0, count);
        return finalRecords;
    }    
}

