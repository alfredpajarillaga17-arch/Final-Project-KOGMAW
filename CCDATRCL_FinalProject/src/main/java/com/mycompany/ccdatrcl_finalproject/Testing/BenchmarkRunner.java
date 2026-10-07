package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms;
import com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms.SortMetrics;

import java.util.Arrays;

public class BenchmarkRunner {
    public static void runBenchmark() {
        int[] sampleSizes = {100, 500, 1000, 2500, 5000};

        System.out.println("==========================================================================================");
        System.out.println("                              TEMPLATE F - BENCHMARK RESULTS                              ");
        System.out.println("==========================================================================================");
        // Aligned with Template F columns
        System.out.printf("%-15s | %-12s | %-20s | %-15s | %-25s%n", 
                "Dataset Size", "Operation", "Algorithm", "Time (ns)", "Comparisons / Movements");
        System.out.println("------------------------------------------------------------------------------------------");

        for (int size : sampleSizes) {
            Appointment[] data = DataLoader.generateSyntheticData(size);
            
            // --- Bubble Sort Benchmark ---
            Appointment[] bubbleData = Arrays.copyOf(data, data.length);
            long startTime = System.nanoTime();
            SortingAlgorithms.SortMetrics bubbleMetrics = SortingAlgorithms.bubbleSort(bubbleData);
            long bubbleTime = System.nanoTime() - startTime; 
            
            String bubbleCompMov = bubbleMetrics.comparisons + " / " + bubbleMetrics.movements;
            System.out.printf("%-15d | %-12s | %-20s | %-15d | %-25s%n", 
                    size, "Sorting", "Bubble Sort", bubbleTime, bubbleCompMov); 

            // --- Selection Sort Benchmark ---
            Appointment[] selectData = Arrays.copyOf(data, data.length);
            startTime = System.nanoTime();
            SortingAlgorithms.SortMetrics selectMetrics = SortingAlgorithms.selectionSortByName(selectData);
            long selectTime = System.nanoTime() - startTime;
            
            String selectCompMov = selectMetrics.comparisons + " / " + selectMetrics.movements;
            System.out.printf("%-15d | %-12s | %-20s | %-15d | %-25s%n", 
                    size, "Sorting", "Selection Sort", selectTime, selectCompMov); 
        }
        System.out.println("==========================================================================================");
    }
    
    public static void main(String[] args) {
        runBenchmark();
    }
}