package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.models.*;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("=== TEMPLATE G: TEST EXECUTION ===");

        Appointment app1 = new Appointment("KGW-001", "Customer A", "Contact A", "Phone", "BrandX", "ModelY", "Screen", "2026-10-10", "Intake");
        Appointment app2 = new Appointment("KGW-002", "Customer B", "Contact B", "Laptop", "BrandZ", "ModelW", "Battery", "2026-10-11", "Diagnostics");
        Appointment urgentApp = new Appointment("KGW-003", "Customer C", "Contact C", "Tablet", "BrandA", "ModelB", "Port", "2026-10-01", "Urgent"); // Earliest date

        // T01: Empty structure case
        CustomQueue emptyQueue = new CustomQueue();
        System.out.println("\nT01 - Empty Structure");
        System.out.println("Expected: true");
        System.out.println("Actual: " + emptyQueue.isEmpty());

        // T02: Single record case
        CustomQueue singleQueue = new CustomQueue();
        singleQueue.enqueue(app1);
        System.out.println("\nT02 - Single Record");
        System.out.println("Expected Size: 1");
        System.out.println("Actual Size: " + singleQueue.size());

        // T03: Duplicate record (Hash Table updates existing key)
        CustomHashTable hashTable = new CustomHashTable();
        hashTable.put("KGW-001", app1);
        hashTable.put("KGW-001", app2); // Overwriting same key
        System.out.println("\nT03 - Duplicate Record");
        System.out.println("Expected Name: Customer B (Overwritten)");
        System.out.println("Actual Name: " + hashTable.get("KGW-001").getCustomerName());

        // T04: Search existing key
        CustomBST bst = new CustomBST();
        bst.insert(app1);
        System.out.println("\nT04 - Search Existing");
        System.out.println("Expected: KGW-001");
        System.out.println("Actual: " + bst.search("KGW-001").getId());

        // T05: Search missing key
        System.out.println("\nT05 - Search Missing");
        System.out.println("Expected: null");
        System.out.println("Actual: " + bst.search("INVALID-ID"));

        // T06: Hash collision (Force collision by setting capacity to 1)
        CustomHashTable collisionTable = new CustomHashTable(1);
        collisionTable.put("KGW-001", app1);
        collisionTable.put("KGW-002", app2);
        System.out.println("\nT06 - Hash Collision");
        System.out.println("Expected Size in single bucket: 2");
        System.out.println("Actual Size: " + collisionTable.getSize());

        // T07 & T08: BFS and DFS
        CustomGraph graph = new CustomGraph();
        graph.addEdge("Intake", "Diagnostics", 10);
        graph.addEdge("Diagnostics", "Repair", 60);
        System.out.println("\nT07 - BFS Traversal");
        graph.bfs("Intake"); // Visually verify path
        System.out.println("\nT08 - DFS Traversal");
        graph.dfs("Intake"); // Visually verify path (Assuming you added the dfs method from the previous step)

        // T09: BST deletion
        bst.insert(app2);
        bst.delete("KGW-001");
        System.out.println("\nT09 - BST Deletion");
        System.out.println("Expected Search KGW-001: null");
        System.out.println("Actual Search KGW-001: " + bst.search("KGW-001"));

        // T10: Heap extraction
        CustomHeap heap = new CustomHeap(10);
        heap.insert(app1); // Oct 10
        heap.insert(urgentApp); // Oct 1 (Should be extracted first)
        System.out.println("\nT10 - Heap Extraction");
        System.out.println("Expected ID: KGW-003 (Earliest Date)");
        System.out.println("Actual ID: " + heap.extractUrgent().getId());
    }
}