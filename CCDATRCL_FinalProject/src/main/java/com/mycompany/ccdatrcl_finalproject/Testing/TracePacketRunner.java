package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.models.*;
import com.mycompany.ccdatrcl_finalproject.utils.DataManager;
import com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms;

public class TracePacketRunner {

    public static void main(String[] args) {
        System.out.println("=== CCDATRCL TEMPLATE H: TRACE PACKET GENERATOR ===\n");

        // Using primary records from 50_primary_records.sql
        Appointment app1 = new Appointment("KGW-M4X9", "Alfred", "09171234567", "Laptop", "Apple", "MacBook Air M1", "Battery health at 71%", "2026-09-09", "Pending");
        Appointment app2 = new Appointment("KGW-7B2L", "John", "09229876543", "Android Phone", "Xiaomi", "Redmi Note 11", "Volume buttons stuck", "2026-09-29", "Pending");
        Appointment app3 = new Appointment("KGW-9L7X", "Alfred", "09181122334", "Android Phone", "Vivo", "Y20i", "Battery swollen", "2026-09-02", "Pending");

        // 1. Linked List Insertion Trace
        System.out.println("--- 1. LINKED LIST INSERTION TRACE ---");
        CustomLinkedList list = new CustomLinkedList();
        System.out.println("Initial State: Head is " + (list.getHead() == null ? "null" : "populated"));
        list.insert(app1);
        System.out.println("Final State: Head points to " + list.getHead().data.getId());

        // 2. Stack Push Trace
        System.out.println("\n--- 2. STACK PUSH TRACE ---");
        CustomStack stack = new CustomStack();
        stack.push(app1);
        System.out.println("Initial State: Top is " + stack.peek().getId() + ", Size: " + stack.size());
        stack.push(app2);
        System.out.println("Final State: Top is now " + stack.peek().getId() + ", Size: " + stack.size());

        // 3. BST Insertion Trace
        System.out.println("\n--- 3. BST INSERTION TRACE ---");
        CustomBST bst = new CustomBST();
        bst.insert(app1); // Root
        System.out.println("Initial State: Root is KGW-M4X9. Inserting KGW-7B2L...");
        bst.insert(app2); // Will go to the left because '7' < 'M'
        System.out.println("Final State: KGW-7B2L is inserted. Traversing to verify:");
        bst.inOrder(); 

        // 4. Heap Insertion Trace (Priority Processing)
        System.out.println("\n--- 4. HEAP INSERTION TRACE ---");
        CustomHeap heap = new CustomHeap(10);
        heap.insert(app1); // Date: Sept 9
        System.out.println("Initial State: Root/Urgent is " + heap.peekUrgent().getId() + " (Date: " + heap.peekUrgent().getAppointmentDate() + ")");
        heap.insert(app3); // Date: Sept 2 (Earlier date, should float to top)
        System.out.println("Final State: Root/Urgent is now " + heap.peekUrgent().getId() + " (Date: " + heap.peekUrgent().getAppointmentDate() + ")");

        // 5. Hash Table Collision Trace
        System.out.println("\n--- 5. HASH TABLE COLLISION TRACE ---");
        // Forcing a capacity of 1 guarantees a collision for every inserted item
        CustomHashTable hashTable = new CustomHashTable(1); 
        hashTable.put(app1.getId(), app1);
        System.out.println("Initial State: Bucket [0] contains " + app1.getId());
        hashTable.put(app2.getId(), app2);
        System.out.println("Final State: Collision handled via chaining.");
        hashTable.displayTable(); // Will show both IDs linked in Bucket [0]

        // 6 & 7. BFS and DFS Trace
        System.out.println("\n--- 6 & 7. BFS AND DFS TRACE ---");
        CustomGraph graph = DataManager.getInstance().getWorkflowGraph();
        System.out.println("Executing BFS from Initial Diagnostic:");
        graph.bfs("Initial Diagnostic");
        System.out.println("Executing DFS from Initial Diagnostic:");
        graph.dfs("Initial Diagnostic");

        // 8. Recursive Method Trace (BST Search)
        System.out.println("\n--- 8. RECURSIVE BST SEARCH TRACE ---");
        System.out.println("Searching for KGW-7B2L inside the tree...");
        Appointment foundApp = bst.search("KGW-7B2L");
        System.out.println("Final State: Search returned customer " + (foundApp != null ? foundApp.getCustomerName() : "null"));

        // 9. Sorting Pass Trace
        System.out.println("\n--- 9. SORTING PASS TRACE (BUBBLE SORT) ---");
        Appointment[] arrayToSort = {app1, app3}; // Sept 9 at index 0, Sept 2 at index 1
        System.out.println("Initial State: [" + arrayToSort[0].getId() + ", " + arrayToSort[1].getId() + "]");
        SortingAlgorithms.SortMetrics metrics = SortingAlgorithms.bubbleSort(arrayToSort);
        System.out.println("Final State: [" + arrayToSort[0].getId() + ", " + arrayToSort[1].getId() + "]");
        System.out.println("Total Movements (Swaps) executed: " + metrics.movements);
    }
}