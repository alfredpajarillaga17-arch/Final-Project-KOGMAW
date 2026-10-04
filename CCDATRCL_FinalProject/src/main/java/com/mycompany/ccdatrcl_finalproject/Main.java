package com.mycompany.ccdatrcl_finalproject;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.models.CustomHeap;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("    K.O.G.M.A.W. - Custom Heap Testing Module     ");
        System.out.println("==================================================\n");

        // Initialize the heap with a small capacity (3) to test dynamic resizing
        CustomHeap priorityQueue = new CustomHeap(3);

        // Create sample appointments with out-of-order dates
        Appointment app1 = new Appointment("KGW-001", "Ivan Briones", "Laptop", "Lenovo", "IdeaPad", "Keyboard broken", "2026-11-15");
        Appointment app2 = new Appointment("KGW-002", "Carl Dairo", "Phone", "Samsung", "S22", "Screen cracked", "2026-10-01"); // Earliest date
        Appointment app3 = new Appointment("KGW-003", "Darginawin Degollacion", "Tablet", "Apple", "iPad", "Battery drains", "2026-11-20");
        Appointment app4 = new Appointment("KGW-004", "Jhon Ravene Fiel", "Desktop", "ASRock", "Custom", "No POST", "2026-10-15");
        Appointment app5 = new Appointment("KGW-005", "Kier Paras", "Laptop", "Dell", "XPS", "Overheating", "2026-10-05");

        System.out.println("Inserting incoming repair tickets...");
        priorityQueue.insert(app1);
        priorityQueue.insert(app2);
        priorityQueue.insert(app3);
        priorityQueue.insert(app4); // Triggers array resizing
        priorityQueue.insert(app5);

        System.out.println("\nTotal tickets in Priority Queue: " + priorityQueue.getSize());
        
        Appointment nextUrgent = priorityQueue.peekUrgent();
        System.out.println("Next Urgent Ticket to Process: " + nextUrgent.getId() + " scheduled for " + nextUrgent.getAppointmentDate());

        System.out.println("\n--- Extracting Tickets (Should output in chronological order) ---");
        
        // Extract all items until the heap is empty
        while (!priorityQueue.isEmpty()) {
            Appointment processed = priorityQueue.extractUrgent();
            System.out.println("Processing -> Date: " + processed.getAppointmentDate() + 
                               " | ID: " + processed.getId() + 
                               " | Customer: " + processed.getCustomerName());
        }

        System.out.println("\nAll urgent tickets processed. Heap is empty: " + priorityQueue.isEmpty());
    }
}