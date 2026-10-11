package com.mycompany.ccdatrcl_finalproject.models;
// First-In, First-Out (FIFO) data structure
// To manage the line of devices waiting to be repaired
public class CustomQueue {
    // Hinohold nito yung ticket details and think of it na the node that is holding the ticket know exactly what node is directly behind them
    private static class Node {
        Appointment data;
        Node next;

        Node(Appointment data) {
            this.data = data;
            this.next = null;
        }
    }
    // These are the variables for the lines
    private Node front;
    private Node rear;
    private int size;
    // Constructor: Think of it na line na kakabukas pa lang front is empty then rear is empty then yung size niya is 0
    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }
    // ENQUEUE USED IN: DashboardController.java "Save Appointment" Button
    /* 
     * If there is a new ticket added to the line and empty palang yung line it will become both front and rear
     * If may mga ticket na nagwwait on the rear this new ticket will be linked to the previous rear and will become the new rear
     */
    public void enqueue(Appointment appointment) {
        Node newNode = new Node(appointment);
        
        // Base Case: If queue is empty, both front and rear point to the new node
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    // DEQUEUE USED IN: DashboardController.java "Process Next" Button
    // Removes and returns yung ticket na currently in front of the line
    // Automatically niya rin pinupull up 'yung Search Ticket tab to display the details
    public Appointment dequeue() {
        if (front == null) {
            return null; 
        }
        
        Appointment temp = front.data;
        front = front.next; // once na-click 'yung process next directly ng lilipat 'yung front node to the next node which is sorted by oldest date first in line
        // If empty na 'yung front then the queue/line is empty so empty na rin 'yung rear
        if (front == null) {
            rear = null;
        }
        size--;
        return temp;
    }
    // Standard Utitlity method can be used later
    // PEEK USED IN: TracePacketRunner.java
    // Chinecheck niya 'yung front element to see kung sino 'yung next in line without removing them from the queue
    public Appointment peek() {
        return (front != null) ? front.data : null;
    }
    // ISEMPTY USED IN: TestRunner
    // Just checking if may nasa front ba na waiting
    // Vinavalidate niya lang 'yung integrity nung structure (T01)
    public boolean isEmpty() {
        return front == null;
    }
    // SIZE USED IN: TestRunner
    // Cinocount exactly how many are in line
    // To validate the queue capacity (T02)
    public int size() {
        return size;
    }
}
