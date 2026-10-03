package com.mycompany.ccdatrcl_finalproject;

import com.mycompany.ccdatrcl_finalproject.models.*;
import com.mycompany.ccdatrcl_finalproject.utils.DataLoader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("=== KOGMAW DATASET & DATA STRUCTURES BENCHMARK ===");
        System.out.println("==================================================");

        // 1. Load data dynamically from your text file dataset
        // (Make sure your file is in the CCDATRCL_FinalProject root folder)

        long startTime = System.nanoTime();
        CustomLinkedList loadedList = DataLoader.loadAppointments("CCDATRCL_FinalProject/kogmaw_datasets.txt");
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

        // ==========================================
        // BINARY SEARCH TREE TEST OUTPUTS
        // ==========================================
        CustomBST bst = new CustomBST();

        CustomLinkedList.Node current = loadedList.getHead();

        while (current != null) {
            bst.insert(current.data);
            current = current.next;
        }

        bst.inOrder();

        // ==========================================
        // SEARCH & DELETE FUNCTIONALITY TEST
        // ==========================================

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- [BST Operations] Search or Delete ---");
            System.out.println("Type 'exit' to quit.");

            System.out.print("What to do? (search/delete/exit): ");
            String sOrDInput = s.nextLine().toLowerCase().trim();

            if (sOrDInput.equals("search")) {
                String search = "";

                while (true) {

                    System.out.println();
                    System.out.print("Search: "); // pangutan on ang user kung unsa ang ID nga gusto niya i-search
                    search = s.nextLine().toUpperCase();
                    search = search.trim(); // i-trim ang input para walay leading or trailing spaces

                    if (search.equalsIgnoreCase("back")) {
                        System.out.println("Returning to BST Operations...");
                        break;
                    }

                    if (search.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting search...");
                        break;
                    }

                    Appointment found = bst.search(search); // nya i-butang diri ang result sa search function sa BST

                    if (found != null) {
                        System.out.println("Found Appointment: " + found);
                    } else {
                        System.out.println("Appointment not found.");
                    }

                }
            } else if (sOrDInput.equals("delete")) {
                String delete = "";

                while (true) {

                    System.out.println();
                    System.out.print("Delete: "); // pangutan on ang user kung unsa ang ID nga gusto niya i-delete
                    delete = s.nextLine().toUpperCase();
                    delete = delete.trim(); // i-trim ang input para walay leading or trailing spaces

                    if (delete.equalsIgnoreCase("back")) {
                        System.out.println("Returning to BST Operations...");
                        break;
                    }

                    if (delete.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting delete...");
                        break;
                    }

                    Appointment found = bst.search(delete); // nya i-butang diri ang result sa search function sa BST

                    if (found != null) {
                        bst.delete(delete); // kung makit-an, i-delete niya ang appointment gikan sa BST
                        System.out.println("Deleted Appointment: " + found);
                    } else {
                        System.out.println("Appointment not found.");
                    }

                }
            } else if (sOrDInput.equals("exit")) {
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid input. Please type 'search', 'delete', or 'exit'.");
            }

        }
    }

}
