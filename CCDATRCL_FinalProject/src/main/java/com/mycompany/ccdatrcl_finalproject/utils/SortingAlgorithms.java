 package com.mycompany.ccdatrcl_finalproject.utils;

    import com.mycompany.ccdatrcl_finalproject.models.Appointment;

    public class SortingAlgorithms {
        public static void bubbleSort(Appointment[] appointments) {
            int n = appointments.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if(appointments[j] != null && appointments[j + 1] != null) {
                        if(appointments[j].getAppointmentDate().compareTo(appointments[j + 1].getAppointmentDate()) > 0) {
                            Appointment temp = appointments[j];
                            appointments[j] = appointments[j + 1];
                            appointments[j + 1] = temp;
                        }
                    }
                }
            }
        }

        public static void selectionSortByName(Appointment[] appointments) {
            int n = appointments.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for(int j = i + 1; j < n; j++) {
                    if(appointments[j] != null && appointments[minIdx] != null) {
                        if(appointments[j].getCustomerName().compareToIgnoreCase(appointments[minIdx].getCustomerName()) < 0) {
                            minIdx = j;
                        }
                    }
                }
                Appointment temp = appointments[minIdx];
                appointments[minIdx] = appointments[i];
                appointments[i] = temp;  
            }
        }
        public static void insertionSortById(Appointment[] appointments) {
            int n = appointments.length;
            for (int i = 1; i < n; i++) {
                Appointment key = appointments[i];
                int j = i - 1;
                while (j >= 0 && appointments[j] != null && key != null &&
                          appointments[j].getId().compareTo(key.getId()) > 0) {
                            appointments[j + 1] = appointments[j];
                            j--;
                          }
                          appointments[j + 1] = key;
            }
        }
    }