package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.utils.DatabaseConnection;

import java.util.Random;
// Think of this class as your Automated Ticket Factory.
// Its only purpose is to rapidly generate thousands of fake repair tickets 
// and push them into your MySQL database so your BenchmarkRunner has enough data to test.
public class DataCreation {

    // These arrays hold predefined dummy data.
    // The factory will blindly reach into these buckets to pick random details for each ticket.
    private static final String[][] CUSTOMER_POOL = {
        {"Juan Dela Cruz", "09171234567"}, {"Maria Santos", "09229876543"},
        {"Jose Reyes", "09355551212"}, {"Ana Garcia", "09988884321"},
        {"Mark Bautista", "09192223344"}, {"Rica Ramos", "09087776655"},
        {"Carlo Mendoza", "09273334455"}, {"Bianca Villanueva", "09431112233"},
        {"Gabriel Aquino", "09156667788"}, {"Jasmine Castro", "09669990011"},
        {"Kevin Morales", "09181122334"}, {"Patricia Torres", "09235567788"},
        {"Christian Navarro", "09369988776"}, {"Nicole Pascual", "09994433221"},
        {"Joshua Lim", "09121110099"}, {"Bea Corpuz", "09095544332"},
        {"Angelo Mercado", "09287788990"}, {"Samantha Tan", "09423322110"},
        {"Vincent Soriano", "09164455667"}, {"Chloe Macaraeg", "09678899001"}
    };

    private static final String[][] GADGET_CATALOG = {
        {"Laptop", "Apple", "MacBook Air M1"},
        {"Android Phone", "Xiaomi", "Redmi Note 11"},
        {"Laptop", "Acer", "Nitro 5 AN515"},
        {"Laptop", "Lenovo", "Legion 5 15ACH6"},
        {"Android Phone", "OPPO", "Reno 6 5G"},
        {"Desktop", "Custom PC", "Core i3-10100F"},
        {"Laptop", "HP", "EliteBook 840 G5"},
        {"Apple Phone", "Apple", "iPhone 13 Pro"},
        {"Desktop", "Custom PC", "Ryzen 5 3600"},
        {"Laptop", "Lenovo", "ThinkPad E14"}
    };

    private static final String[] REPORTED_ISSUES = {
        "Battery health degradation; fast drain",
        "Stuck in bootloop on logo screen",
        "Internal fan making grinding noise",
        "Display backlight died",
        "Front camera glass cracked",
        "Computer turning on then shutting off",
        "Internal CMOS battery expired",
        "Back glass shattered replacement",
        "RAM upgrade request",
        "Laptop battery not charging"
    };
    // This method runs a loop that refuses to stop until exactly 5,000 unique tickets are saved.
    public static void populate5000Records() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rnd = new Random();
        int successCount = 0;

        System.out.println("Starting batch insertion of 5,000 records from DataSeeder...");

        while (successCount < 5000) {
            // Step 1: Generate a random Ticket ID starting with "KGW-" followed by 4 random characters
            StringBuilder sb = new StringBuilder("KGW-");
            for (int j = 0; j < 4; j++) {
                sb.append(chars.charAt(rnd.nextInt(chars.length())));
            }
            String id = sb.toString();
            
            // Step 2: Pick random ingredients from our buckets.
            String[] customer = CUSTOMER_POOL[rnd.nextInt(CUSTOMER_POOL.length)];
            String[] gadget = GADGET_CATALOG[rnd.nextInt(GADGET_CATALOG.length)];
            String reportedIssue = REPORTED_ISSUES[rnd.nextInt(REPORTED_ISSUES.length)];
            String appointmentDate = "2026-" + String.format("%02d", rnd.nextInt(9) + 1) + "-" + String.format("%02d", rnd.nextInt(28) + 1);
            // Step 3: Package all this random information into a brand new Appointment object.
            Appointment app = new Appointment(id, customer[0], customer[1], gadget[0], gadget[1], gadget[2], reportedIssue, appointmentDate, "Pending");
            
            // Step 4: Quality Control - Send it to the database.
            // If the database accepts it (meaning it's not an accidental duplicate ID), we count it as a success.
            if (DatabaseConnection.insertAppointment(app)) {
                successCount++;
                
                // Print progress based on successful inserts
                if (successCount % 500 == 0) {
                    System.out.println("Progress: " + successCount + " records inserted...");
                }
            }
        }
        System.out.println("Finished! Successfully saved " + successCount + " records to database.");
    }
}
