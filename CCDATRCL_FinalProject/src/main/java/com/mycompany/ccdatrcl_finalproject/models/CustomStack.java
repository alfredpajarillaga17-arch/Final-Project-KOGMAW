package com.mycompany.ccdatrcl_finalproject.models;

public class CustomStack {

    // Represents individual stack elements and memory links
    private static class Node {
        Appointment data;
        Node next;

        Node(Appointment data) {
            this.data = data;
            this.next = null;
        }
    }

    // Tracks the top of the stack
    private Node top;
    private int size;

    public CustomStack() {
        this.top = null;
        this.size = 0;
    }

    // Adds a new item to the top of the stack
    public void push(Appointment appointment) {
        Node newNode = new Node(appointment);
        newNode.next = top; // Point new node to the current top
        top = newNode;      // Update top pointer to the new node
        size++;
    }

    // Removes and returns the most recent item from the top
    public Appointment pop() {
        if (top == null) {
            return null;
        }
        Appointment temp = top.data;
        top = top.next; 
        size--;
        return temp;
    }

    // Inspects the most recent item at the top without removing it
    public Appointment peek() {
        return (top != null) ? top.data : null;
    }

    // Checks if the stack is currently empty
    public boolean isEmpty() {
        return top == null;
    }

    // Returns the total count of items in the stack
    public int size() {
        return size;
    }
}