package com.mycompany.ccdatrcl_finalproject.models;

public class CustomHashTable {

    private static class HashNode {
        String key; // Appointment ID (e.g., KGW-001)
        Appointment value;
        HashNode next;

        public HashNode(String key, Appointment value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private HashNode[] table;                                   // Array of HashNode representing the hash table
    private int capacity;                                       // Total number of buckets in the hash table
    private int size;                                           // Current number of key-value pairs stored in the hash table

    public CustomHashTable() {                                   // Default constructor to initialize the hash table with a default capacity
        this(16);                                      // Default initial capacity
    }

    public CustomHashTable(int capacity) {                      // Constructor to initialize the hash table with a specified capacity
        this.capacity = capacity;                               // Set the initial capacity of the hash table
        this.table = new HashNode[capacity];                    // Initialize the hash table array
        this.size = 0;
    }

    /**
     * Hash function that generates an index for a given key string.
     */
    private int getIndex(String key) {                          // Hash function to compute index for a given key
        if (key == null) return 0;                              // Return 0 for null keys to avoid NullPointerException
        int hashCode = key.hashCode();                          // Get the hash code of the key string
        return Math.abs(hashCode) % capacity;                   // Ensure non-negative index within table bounds, Computes the absolute value modulo capacity to guarantee a valid array index within bounds.
    }

    /**
     * Inserts or updates an appointment in the hash table.
     * Time Complexity: O(1) average, O(N) worst case.
     */
    public void put(String key, Appointment value) {            // Inserts or updates an appointment in the hash table
        if (key == null || value == null) return;               // Return without inserting if the key or value is null to avoid NullPointerException

        int index = getIndex(key);                              // Compute the index for the key using the hash function
        HashNode head = table[index];                           // Get the head of the linked list at the computed index

        // Check if key already exists, update value if found
        HashNode current = head;                                // Start traversing the linked list at the computed index
        while (current != null) {                               // Traverse the linked list to check for existing key
            if (current.key.equalsIgnoreCase(key)) {            // If the key matches (case-insensitive), update the value
                current.value = value;                          // Update the value associated with the existing key
                return;                                         // Return after updating the value to avoid inserting a duplicate key
            }
            current = current.next;                             // Move to the next node in the linked list
        }

        // Insert new node at the head of the chain
        HashNode newNode = new HashNode(key, value);            // Create a new HashNode for the key-value pair
        newNode.next = head;                                    // Link the new node to the existing head of the linked list
        table[index] = newNode;                                 //  Set the new node as the head of the linked list at the computed index
        size++;                                                 // Increment the size of the hash table to reflect the addition of a new key-value pair
    }

    /**
     * Retrieves an appointment by its ID key (e.g., KGW-001).
     * Time Complexity: O(1) average, O(N) worst case.
     */
    public Appointment get(String key) {                        // Retrieves an appointment by its ID key (e.g., KGW-001)
        if (key == null) return null;                           // Return null if the key is null to avoid NullPointerException

        int index = getIndex(key);                              // Compute the index for the key using the hash function
        HashNode current = table[index];                        // Get the head of the linked list at the computed index

        while (current != null) {                               // Traverse the linked list to find the key
            if (current.key.equalsIgnoreCase(key)) {            // If the key matches (case-insensitive), return the associated value
                return current.value;                           // Return the Appointment object associated with the key
            }
            current = current.next;                             // Move to the next node in the linked list
        }
        return null; // Key not found
    }

    /**
     * Removes an appointment by its ID key.
     * Time Complexity: O(1) average, O(N) worst case.
     */
    public Appointment remove(String key) {                     // Removes an appointment by its ID key
        if (key == null) return null;                           // Return null if the key is null to avoid NullPointerException

        int index = getIndex(key);                              // Compute the index for the key using the hash function
        HashNode current = table[index];                        // Get the head of the linked list at the computed index
        HashNode prev = null;                                   // Initialize a reference to the previous node

        while (current != null) {                               // Traverse the linked list to find the key
            if (current.key.equalsIgnoreCase(key)) {            // If the key matches (case-insensitive), remove the node from the linked list
                if (prev != null) {                             // If the node to be removed is not the head of the linked list, update the previous node's next reference
                    prev.next = current.next;                   // Bypass the current node to remove it from the linked list
                } else {
                    table[index] = current.next;                // If the node to be removed is the head of the linked list, update the head reference in the hash table
                }
                size--;                                         // Decrement the size of the hash table to reflect the removal of a key-value pair
                return current.value;                           // Return the Appointment object that was removed
            }
            prev = current;                                     // Move the previous reference to the current node
            current = current.next;                             // Move to the next node in the linked list
        }
        return null; // Key not found
    }

    public int getSize() {                                      // Returns the current number of key-value pairs stored in the hash table
        return size;
    }

    public boolean isEmpty() {                                  // Checks if the hash table is empty (i.e., contains no key-value pairs)
        return size == 0;
    }

    /**
     * Prints all entries currently stored in the Hash Table.
     */
    public void displayTable() {                                                                               // Prints all entries currently stored in the Hash Table
        System.out.println("\n--- Custom Hash Table Contents ---");                                         // Print a header message indicating the start of the hash table contents display
        for (int i = 0; i < capacity; i++) {                                                                   // Loop through each bucket in the hash table to display its contents
            HashNode current = table[i];
            if (current != null) {
                System.out.print("Bucket [" + i + "]: ");
                while (current != null) {
                    System.out.print("[" + current.key + " -> " + current.value.getDeviceType() + "] -> ");
                    current = current.next;
                }
                System.out.println("null");
            }
        }
    }
}
