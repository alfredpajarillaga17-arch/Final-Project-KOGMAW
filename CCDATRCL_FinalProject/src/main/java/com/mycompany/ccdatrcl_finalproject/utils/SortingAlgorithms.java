package com.mycompany.ccdatrcl_finalproject.utils;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;

public class SortingAlgorithms {
    
    // Helper class to store benchmark metrics
    public static class SortMetrics {
        public long comparisons = 0;
        public long movements = 0;
    }

    public static SortMetrics bubbleSort(Appointment[] appointments) {
        SortMetrics metrics = new SortMetrics();
        int n = appointments.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if(appointments[j] != null && appointments[j + 1] != null) {
                    metrics.comparisons++; // Track comparison
                    
                    if(appointments[j].getAppointmentDate().compareTo(appointments[j + 1].getAppointmentDate()) > 0) {
                        // Track movement (swap)
                        Appointment temp = appointments[j];
                        appointments[j] = appointments[j + 1];
                        appointments[j + 1] = temp;
                        metrics.movements++; 
                    }
                }
            }
        }
        return metrics;
    }

    public static SortMetrics selectionSortByName(Appointment[] appointments) {
        SortMetrics metrics = new SortMetrics();
        int n = appointments.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for(int j = i + 1; j < n; j++) {
                if(appointments[j] != null && appointments[minIdx] != null) {
                    metrics.comparisons++; // Track comparison
                    
                    if(appointments[j].getCustomerName().compareToIgnoreCase(appointments[minIdx].getCustomerName()) < 0) {
                        minIdx = j;
                    }
                }
            }

            if (minIdx != i) {
                Appointment temp = appointments[minIdx];
                appointments[minIdx] = appointments[i];
                appointments[i] = temp;  
                metrics.movements++; // Track movement (swap)
            }
        }
        return metrics;
    } 
}