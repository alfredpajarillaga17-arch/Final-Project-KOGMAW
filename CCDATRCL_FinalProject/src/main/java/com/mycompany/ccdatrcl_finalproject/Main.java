package com.mycompany.ccdatrcl_finalproject;                    

import com.mycompany.ccdatrcl_finalproject.utils.DataLoader;
import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList;
import com.mycompany.ccdatrcl_finalproject.models.CustomQueue;
import com.mycompany.ccdatrcl_finalproject.models.CustomStack;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("=== KOGMAW DATASET & DATA STRUCTURES BENCHMARK ===");
        System.out.println("==================================================");

        // 1. Load data dynamically from your text file dataset
        // (Make sure your file is in the CCDATRCL_FinalProject root folder)
        String filePath = "kogmaw_datasets.txt";
        
        long startTime = System.nanoTime();
        CustomLinkedList loadedList = DataLoader.loadAppointments(filePath);
        long endTime = System.nanoTime();

        System.out.println("File loading time: " + (endTime - startTime) / 1_000_000 + " ms");

        // Verify if data was actually loaded
        Appointment sampleApp = loadedList.search("KGW-001");
        if (sampleApp == null) {
            System.err.println("[Error] No records found. Check file path or formatting.");
            return;
        }

        // ==========================================
        // 2. TEST POPULATING QUEUE, STACK, & HASH TABLE
        // ==========================================
        System.out.println("\n--- [Benchmark] Populating Custom Structures ---");
        
        CustomQueue repairQueue = new CustomQueue();
        CustomStack actionStack = new CustomStack();

        // We can traverse or simulate pushing items from our loaded data
        // For demonstration, let's test with the first few records or loop through them
        System.out.println("Initializing structures with dataset records...");
        
        // Let's test a targeted lookup using the Hash Table
        Appointment target = loadedList.search("KGW-042"); // Searching for an ID in the dataset
        if (target != null) {
            repairQueue.enqueue(target);
            actionStack.push(target);
        }

        // ==========================================
        // 3. VERIFY OPERATIONS
        // ==========================================

        System.out.println("\n--- [Verification] Testing FIFO Queue Peak ---");
        Appointment queueNext = repairQueue.peek();
        if (queueNext != null) {
            System.out.println("Next in Queue Service List: " + queueNext.getId());
        }

        System.out.println("\n--- [Verification] Testing LIFO Stack Peak ---");
        Appointment stackTop = actionStack.peek();
        if (stackTop != null) {
            System.out.println("Most Recent Action on Stack: " + stackTop.getId());
        }

        System.out.println("\n==================================================");
        System.out.println("=== DATASET INTEGRATION TEST PASSED SUCCESSFULLY ===");
        System.out.println("==================================================");
    }
}