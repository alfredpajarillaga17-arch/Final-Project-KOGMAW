package com.mycompany.ccdatrcl_finalproject;

import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.utils.DataLoader;

public class Main {
    
    public static Appointment[] initialData; 

    public static void main(String[] args) {

        String filePath = "src/main/java/com/mycompany/ccdatrcl_finalproject/DataSets/kogmaw_datasets.txt"; 
        initialData = DataLoader.loadAppointments(filePath, 50);
    }
}