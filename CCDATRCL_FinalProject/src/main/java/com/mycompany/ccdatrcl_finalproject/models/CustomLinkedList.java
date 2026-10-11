package com.mycompany.ccdatrcl_finalproject.models;
// Used for tracking history logs ng repair tickets
public class CustomLinkedList {
    // USED IN: DashboardController during initialization.
    // Nag-aallow ng other parts ng program to access yung very first node sa list
    public Node getHead() {
        return head;
    }
    
    /* 
     * Node class na nagrerepresent ng bawat appointment sa linked list
     * 'Pag may inaadd na ticket laging may node na nagagawa which contains two things: yung appointment details and yung next node pointer
     */ 
    public static class Node {
        public Appointment data; //ticket details
        public Node next; //pointer to the next node
        
        //constructor na nagiinitialize ng node with appointment data and sets next pointer to null
        public Node(Appointment data) {
            this.data = data;
            this.next = null;
        }
    }
    // Head ng linked list, which is yung very first node
    private Node head;
    // Constructor na nagiinitialize ng linked list with no nodes
    public CustomLinkedList() {
        this.head = null;
    }
    // USED IN: DatabaseConnection and DashboardController.
    // Nilalagay niya yung new appointment sa tail ng linked list
    public void insert(Appointment appointment) {
        Node newNode = new Node(appointment);
        // If yung head is null or empty, yung new appointment yung magiging head
        if (head == null) {
            head = newNode;
        } else {
            // else  magttravel siya sa dulo hanggang makita niya yung last node tapos doon niya ilalagay yung new appointment
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            // Iaattach niya yung new node sa dulo ng list
            current.next = newNode;
        }
    }
    // USED IN: DashboardController.
    // Hahanapin niya yung appointment details using Ticket id then ireremove niya yung node na yun sa linked list 
    public boolean delete(String appointmentId) {
        if (head == null) return false;

        //Case 1: yung node na aalisin is yung nasa front ng list, so iaassign niya yung head sa next node
        if (head.data.getId().equals(appointmentId)) {
            head = head.next;
            return true;
        }
        /* Case 2: yung node na aalisin is nasa gitna or dulo ng list, so magttravel 
         * siya sa list hanggang makita niya yung node na may matching id tapos iaassign 
         * niya yung next pointer ng previous node sa next pointer ng current node 
         * para maalis yung current node sa list
         */
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
    // USED IN: TracePacketRunner.
    // Hahanapin niya yung appointment details using Ticket id 
    public Appointment search(String appointmentId) {
        Node current = head;
        while (current != null) {
            if (current.data.getId().equals(appointmentId)) {
                // if nahanap niya na yung node na may matching id, ibabalik niya yung details ng ticdket
                return current.data;
            }
            current = current.next;
        }
        return null; 
    }
    // USED IN: DashboardController.
    // Iiiterate niya sa linked list para i-display yung repair logs sa console once iclick mo yung "Export Log" button sa GUI
    public void traverse() {
        Node current = head;
        System.out.println("\n--- Linked List Repair Logs ---");
        while (current != null) {
            System.out.println("ID: " + current.data.getId() + " | Customer: " + current.data.getCustomerName() + " | Date : " + current.data.getAppointmentDate());
            current = current.next;
        }
    }
    // USED IN: BenchmarkRunner.
    // Returns the total number of nodes in the linked list
    public int getSize() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}
