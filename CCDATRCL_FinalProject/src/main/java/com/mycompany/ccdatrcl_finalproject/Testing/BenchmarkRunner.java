package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;


public class BenchmarkRunner {

    public static void runBenchmarks() {
        System.out.println("Dataset Size   | Operation      | Algorithm         | Time (ns)      | Comparisons / Movements");
        System.out.println("------------------------------------------------------------------------------------------------");

        int[] sizes = {100, 500, 1000, 2500, 5000}; 
        
        // Benchmark Data Loading
        long loadStartTime = System.nanoTime();
        com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList allData = 
            com.mycompany.ccdatrcl_finalproject.utils.DatabaseConnection.fetchAllAppointments();
        long loadEndTime = System.nanoTime();
        long loadDurationNs = loadEndTime - loadStartTime;
        
        // Print data load benchmark
        System.out.printf("%-14d | %-14s | %-17s | %-14d | N/A\n", 
                          allData.getSize(), "Data Loading", "MySQL Fetch", loadDurationNs);

        // Convert linked list to an array for testing sorting algorithms
        Appointment[] masterArray = new Appointment[5000];
        com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList.Node current = allData.getHead();
        int count = 0;
        while (current != null && count < 5000) {
            masterArray[count++] = current.data;
            current = current.next;
        }

        java.util.Collections.shuffle(java.util.Arrays.asList(masterArray));
        
        for (int size : sizes) {
            Appointment[] subArrayBubble = java.util.Arrays.copyOf(masterArray, size);
            Appointment[] subArraySelection = java.util.Arrays.copyOf(masterArray, size);

            // Benchmark Bubble Sort
            long bubbleStartTime = System.nanoTime();
            com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms.SortMetrics bubbleMetrics = 
                com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms.bubbleSort(subArrayBubble);
            long bubbleEndTime = System.nanoTime();
            long bubbleDurationNs = bubbleEndTime - bubbleStartTime;
            
            System.out.printf("%-14d | %-14s | %-17s | %-14d | %d / %d\n", 
                              size, "Sorting", "Bubble Sort", bubbleDurationNs, bubbleMetrics.comparisons, bubbleMetrics.movements);

            // Benchmark Selection Sort
            long selectionStartTime = System.nanoTime();
            com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms.SortMetrics selectionMetrics = 
                com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms.selectionSortByName(subArraySelection);
            long selectionEndTime = System.nanoTime();
            long selectionDurationNs = selectionEndTime - selectionStartTime;

            System.out.printf("%-14d | %-14s | %-17s | %-14d | %d / %d\n", 
                              size, "Sorting", "Selection Sort", selectionDurationNs, selectionMetrics.comparisons, selectionMetrics.movements);
        }
        System.out.println("================================================================================================");
    }

    // This main method allows you to run this file all by itself!
    public static void main(String[] args) {
        runBenchmarks();
    }
}