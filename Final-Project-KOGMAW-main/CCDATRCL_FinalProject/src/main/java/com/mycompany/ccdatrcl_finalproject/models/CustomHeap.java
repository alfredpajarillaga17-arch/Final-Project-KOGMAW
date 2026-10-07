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

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    // Computes priority based on TWO attributes: Date and Phase
    private int comparePriority(Appointment a, Appointment b) {
        int dateComparison = a.getAppointmentDate().compareTo(b.getAppointmentDate());
        if (dateComparison != 0) {
            return dateComparison; // Earlier dates get priority (-1 means a is smaller/earlier)
        }
        // If dates are identical, prioritize by repair phase weight (Higher weight = higher priority)
        return getPhaseWeight(b.getAssessmentPhase()) - getPhaseWeight(a.getAssessmentPhase());
    }

    private int getPhaseWeight(String phase) {
        if (phase == null) return 0;
        switch(phase) {
            case "Initial Diagnostic": return 10;
            case "Disassembly": return 9;
            case "Component Swap": return 8;
            case "Part Ordering": return 7;
            case "Firmware Flash": return 6;
            case "Cleaning": return 5;
            case "Reassembly": return 4;
            case "Stress Test": return 3;
            case "Final QA": return 2;
            case "Ready for Pickup": return 1;
            default: return 0;
        }
    }

    public void insert(Appointment appointment) {
        if (size == capacity) {
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }
        heap[size] = appointment;
        int current = size;
        size++;

        while (current > 0 && comparePriority(heap[current], heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public Appointment extractUrgent() {
        if (size <= 0) return null;
        if (size == 1) return heap[--size];

        Appointment root = heap[0];
        heap[0] = heap[size - 1]; 
        size--;
        heapifyDown(0);           

        return root;
    }

    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && comparePriority(heap[left], heap[smallest]) < 0) smallest = left;
        if (right < size && comparePriority(heap[right], heap[smallest]) < 0) smallest = right;
        
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest); 
        }
    }

    /**
     * Finds and removes a specific appointment by ID from the heap.
     */
    public boolean removeById(String id) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap[i] != null && heap[i].getId().equals(id)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) return false; // Not found

        heap[index] = heap[size - 1]; // Swap with the last element
        heap[size - 1] = null;
        size--;

        if (index < size) {
            // Restore heap property 
            heapifyDown(index);
            int current = index;
            while (current > 0 && comparePriority(heap[current], heap[parent(current)]) < 0) {
                swap(current, parent(current));
                current = parent(current);
            }
        }
        return true;
    }

    private void swap(int i, int j) {
        Appointment temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public Appointment peekUrgent() { return (size > 0) ? heap[0] : null; }
    public boolean isEmpty() { return size == 0; }
    public int getSize() { return size; }
    public Appointment[] getHeapForDisplay() { return Arrays.copyOf(heap, size); }
}