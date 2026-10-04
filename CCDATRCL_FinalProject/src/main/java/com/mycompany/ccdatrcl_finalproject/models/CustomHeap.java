package com.mycompany.ccdatrcl_finalproject.models;

import java.util.Arrays;

public class CustomHeap {
    private Appointment[] heap;
    private int size;
    private int capacity;

    public CustomHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Appointment[capacity];
    }

    // Helper methods for array-based tree navigation
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    /**
     * Inserts a new appointment and heapifies up to maintain Min-Heap property.
     * Easy to trigger from an AppointmentController 'Save' button.
     */
    public void insert(Appointment appointment) {
        if (size == capacity) {
            // Dynamically double array size if capacity is reached
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }
        
        heap[size] = appointment;
        int current = size;
        size++;

        // Heapify Up: Prioritize earlier dates (Min-Heap based on Appointment Date)
        while (current > 0 && 
               heap[current].getAppointmentDate().compareTo(heap[parent(current)].getAppointmentDate()) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    /**
     * Removes and returns the most urgent ticket (root of the Min-Heap).
     * Useful for a 'Process Next Ticket' action in the DashboardController.
     */
    public Appointment extractUrgent() {
        if (size <= 0) return null;
        if (size == 1) {
            size--;
            return heap[0];
        }

        Appointment root = heap[0];
        heap[0] = heap[size - 1]; // Move last element to root
        size--;
        heapifyDown(0);           // Restore heap property

        return root;
    }

    /**
     * Restores the Min-Heap structure from the top down.
     */
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && 
            heap[left].getAppointmentDate().compareTo(heap[smallest].getAppointmentDate()) < 0) {
            smallest = left;
        }
        if (right < size && 
            heap[right].getAppointmentDate().compareTo(heap[smallest].getAppointmentDate()) < 0) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest); // Recursively push the larger date down
        }
    }

    private void swap(int i, int j) {
        Appointment temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Inspects the most urgent ticket without removing it
    public Appointment peekUrgent() {
        if (size <= 0) return null;
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
    public int getSize() {
        return size;
    }

    /**
     * Returns a truncated array of current heap items for GUI table rendering.
     * Note: A heap array is partially sorted, not fully sequentially sorted.
     */
    public Appointment[] getHeapForDisplay() {
        return Arrays.copyOf(heap, size);
    }
}