package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.Testing.DataCreation;

public class Test5000Runner {
    public static void main(String[] args) {
        System.out.println("Starting the 5,000 record generation...");
        // This calls the seeder class you created earlier
        DataCreation.populate5000Records(); 
        System.out.println("Database population complete!");
    }
}