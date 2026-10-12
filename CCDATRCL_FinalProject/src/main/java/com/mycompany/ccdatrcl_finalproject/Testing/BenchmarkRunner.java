package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;

// Think of this class as a racetrack with a stopwatch for your algorithms.
// It is a behind-the-scenes developer tool used to measure exactly how fast your system
// can process and sort large amounts of data.
public class BenchmarkRunner {

    public static void runBenchmarks() {
        System.out.println("Dataset Size   | Operation      | Algorithm         | Time (ns)      | Comparisons / Movements");
        System.out.println("------------------------------------------------------------------------------------------------");
        // We will test our sorting algorithms against different pile sizes to see how they handle scaling up.
        int[] sizes = {100, 500, 1000, 2500, 5000}; 
        
       
        // Start the stopwatch (in nanoseconds)
        long loadStartTime = System.nanoTime();
        // Fetch every single appointment from the MySQL database and load it into our CustomLinkedList
        com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList allData = 
            com.mycompany.ccdatrcl_finalproject.utils.DatabaseConnection.fetchAllAppointments();
        // Stop the stopwatch and calculate the total trip time
        long loadEndTime = System.nanoTime();
        long loadDurationNs = loadEndTime - loadStartTime;
        
        // Print the exact time it took to load the data from the database
        System.out.printf("%-14d | %-14s | %-17s | %-14d | N/A\n", 
                          allData.getSize(), "Data Loading", "MySQL Fetch", loadDurationNs);

      
        // Convert the linked list into a standard array (max 5000 items) so we can run our sorting tests.
        Appointment[] masterArray = new Appointment[5000];
        com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList.Node current = allData.getHead();
        int count = 0;
        // Transfer tickets one by one
        while (current != null && count < 5000) {
            masterArray[count++] = current.data;
            current = current.next;
        }
        // Shuffle the array completely randomly. 
        // We do this to give our sorting algorithms a genuinely messy pile of data to organize!
        java.util.Collections.shuffle(java.util.Arrays.asList(masterArray));
        // Loop through our different pile sizes (100, 500, 1000, etc.)
        for (int size : sizes) {
            // Create exact, identical copies of the messy array for both algorithms so it is a fair race.
            Appointment[] subArrayBubble = java.util.Arrays.copyOf(masterArray, size);
            Appointment[] subArraySelection = java.util.Arrays.copyOf(masterArray, size);

            // Benchmark Bubble Sort
            long bubbleStartTime = System.nanoTime();
            // Run Bubble Sort and capture the metrics (how many times it compared and moved items)
            com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms.SortMetrics bubbleMetrics = 
                com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms.bubbleSort(subArrayBubble);
            long bubbleEndTime = System.nanoTime();
            long bubbleDurationNs = bubbleEndTime - bubbleStartTime;
            
            System.out.printf("%-14d | %-14s | %-17s | %-14d | %d / %d\n", 
                              size, "Sorting", "Bubble Sort", bubbleDurationNs, bubbleMetrics.comparisons, bubbleMetrics.movements);

            // Benchmark Selection Sort
            long selectionStartTime = System.nanoTime();
            // Run Selection Sort and capture the metrics
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
