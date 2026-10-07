package com.mycompany.ccdatrcl_finalproject.models;

public class CustomLinkedList {

    public Node getHead() {
        return head;
    }

    public static class Node {
        public Appointment data;
        public Node next; // Pointer to the next node in memory

        public Node(Appointment data) {
            this.data = data;
            this.next = null;
        }
    }
    // Entry point of the Linked List (Points to the first node)
    private Node head;

    public CustomLinkedList() {
        this.head = null;
    }
    
    // Appends a new appointment to the tail of the list
    public void insert(Appointment appointment) {
        Node newNode = new Node(appointment);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
    // Finds and unlinks a node by its appointment ID
    public boolean delete(String appointmentId) {
        if (head == null) return false;

        // Case 1: The node to remove is the head
        if (head.data.getId().equals(appointmentId)) {
            head = head.next;
            return true;
        }
        // Case 2: Node is in the middle or end
        Node current = head;
        while (current.next != null) {
            if (current.next.data.getId().equals(appointmentId)) {
                // Unlink the target node from the chain
                current.next = current.next.next; 
                return true;
            }
            current = current.next;
        }
        return false;
    }
    // Scans the list to locate an appointment by ID
    public Appointment search(String appointmentId) {
        Node current = head;
        while (current != null) {
            if (current.data.getId().equals(appointmentId)) {
                return current.data;
            }
            current = current.next;
        }
        return null; 
    }
    // Iterates through every node sequentially to display history logs
    public void traverse() {
        Node current = head;
        System.out.println("\n--- Linked List Repair Logs ---");
        while (current != null) {
            System.out.println("ID: " + current.data.getId() + " | Customer: " + current.data.getCustomerName());
            current = current.next;
        }
    }
}