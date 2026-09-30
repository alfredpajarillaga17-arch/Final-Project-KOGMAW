package com.mycompany.ccdatrcl_finalproject.models;

/**
 * Custom Hash Table implementation using Separate Chaining for collision resolution.
 * Provides O(1) average time complexity for insertions, lookups, and deletions
 * using the appointment ID (e.g., KGW-001) as the key.
 */
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

    private HashNode[] table;
    private int capacity;
    private int size;

    public CustomHashTable() {
        this(16); // Default initial capacity
    }

    public CustomHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new HashNode[capacity];
        this.size = 0;
    }

    /**
     * Hash function that generates an index for a given key string.
     */
    private int getIndex(String key) {
        if (key == null) return 0;
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % capacity;
    }

    /**
     * Inserts or updates an appointment in the hash table.
     * Time Complexity: O(1) average, O(N) worst case.
     */
    public void put(String key, Appointment value) {
        if (key == null || value == null) return;

        int index = getIndex(key);
        HashNode head = table[index];

        // Check if key already exists, update value if found
        HashNode current = head;
        while (current != null) {
            if (current.key.equalsIgnoreCase(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        // Insert new node at the head of the chain
        HashNode newNode = new HashNode(key, value);
        newNode.next = head;
        table[index] = newNode;
        size++;
    }

    /**
     * Retrieves an appointment by its ID key (e.g., KGW-001).
     * Time Complexity: O(1) average, O(N) worst case.
     */
    public Appointment get(String key) {
        if (key == null) return null;

        int index = getIndex(key);
        HashNode current = table[index];

        while (current != null) {
            if (current.key.equalsIgnoreCase(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    /**
     * Removes an appointment by its ID key.
     * Time Complexity: O(1) average, O(N) worst case.
     */
    public Appointment remove(String key) {
        if (key == null) return null;

        int index = getIndex(key);
        HashNode current = table[index];
        HashNode prev = null;

        while (current != null) {
            if (current.key.equalsIgnoreCase(key)) {
                if (prev != null) {
                    prev.next = current.next;
                } else {
                    table[index] = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null; // Key not found
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Prints all entries currently stored in the Hash Table.
     */
    public void displayTable() {
        System.out.println("\n--- Custom Hash Table Contents ---");
        for (int i = 0; i < capacity; i++) {
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

    // Main method for independent testing
    public static void main(String[] args) {
        CustomHashTable hashTable = new CustomHashTable(10);

        Appointment app1 = new Appointment("KGW-001", "Juan", "Laptop", "Lenovo", "IdeaPad 3", "No power", "2026-09-01");
        Appointment app2 = new Appointment("KGW-002", "Maria", "Washing Machine", "Panasonic", "NA-F70S7", "Spin cycle fail", "2026-09-02");
        Appointment app3 = new Appointment("KGW-003", "Pedro", "Refrigerator", "Samsung", "RT22", "Not cooling", "2026-09-01");

        // Test Insertion
        hashTable.put(app1.getId(), app1);
        hashTable.put(app2.getId(), app2);
        hashTable.put(app3.getId(), app3);

        // Test Lookup
        System.out.println("Lookup KGW-002: " + hashTable.get("KGW-002"));

        // Test Removal
        hashTable.remove("KGW-001");
        System.out.println("Lookup KGW-001 after removal: " + hashTable.get("KGW-001"));

        hashTable.displayTable();
    }
}