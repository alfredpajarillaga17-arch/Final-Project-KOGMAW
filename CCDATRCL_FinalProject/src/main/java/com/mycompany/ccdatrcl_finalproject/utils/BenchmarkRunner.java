package com.mycompany.ccdatrcl_finalproject.utils;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import java.util.Arrays;

public class BenchmarkRunner {
    public static void runBenchmark() {
        int[] sampleSizes = {100, 500, 1000, 2500, 5000};

        System.out.println("=================================================");
        System.out.println("       SORTING ALGORITHM BENCHMARK RESULTS       ");
        System.out.println("=================================================");
        System.out.printf("%-12s | %-15s | %-15s%n", "Record Count", "Bubble Sort (ms)", "Selection Sort (ms)");
        System.out.println("-------------------------------------------------");

        for (int size : sampleSizes) {
            Appointment[] data = DataLoader.generateSyntheticData(size);
            Appointment[] bubbleData = Arrays.copyOf(data, data.length);
            long startTime = System.nanoTime();
            SortingAlgorithms.bubbleSort(bubbleData);
            long bubbleTime = System.nanoTime() - startTime;

            Appointment[] selectData = Arrays.copyOf(data, data.length);
            startTime = System.nanoTime();
            SortingAlgorithms.selectionSortByName(selectData);
            long selectTime = System.nanoTime() - startTime;

            double bubbleMs = bubbleTime / 1_000_000.0;
            double selectMs = selectTime / 1_000_000.0;

            System.out.printf("%-12d | %-15.3f | %-15.3f%n", size, bubbleMs, selectMs); 
        }
        System.out.println("=================================================");
    }
    public static void main(String[] args) {
        runBenchmark();
    }
}