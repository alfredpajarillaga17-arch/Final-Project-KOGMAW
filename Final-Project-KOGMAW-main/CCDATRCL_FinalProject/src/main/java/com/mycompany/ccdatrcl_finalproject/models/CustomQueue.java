package com.mycompany.ccdatrcl_finalproject.models;

public class CustomQueue {

    // Represents individual queue elements
    private static class Node {
        Appointment data;
        Node next;

        Node(Appointment data) {
            this.data = data;
            this.next = null;
        }
    }

    // Track the front (for dequeue) and rear (for enqueue) of the queue
    private Node front;
    private Node rear;
    private int size;

    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // Adds a new element to the back of the queue ($O(1)$ time complexity)
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

    // Removes and returns the front element (FIFO service logic, $O(1)$ time complexity)
    public Appointment dequeue() {
        if (front == null) {
            return null; 
        }
        
        Appointment temp = front.data;
        front = front.next; 
        
        // If front becomes null, the queue is now empty, so reset rear as well
        if (front == null) {
            rear = null;
        }
        size--;
        return temp;
    }

    // Inspects the next element to be processed without removing it 
    public Appointment peek() {
        return (front != null) ? front.data : null;
    }

    // Checks if the queue contains any elements
    public boolean isEmpty() {
        return front == null;
    }

    // Returns the current total count of items in the queue
    public int size() {
        return size;
    }
}