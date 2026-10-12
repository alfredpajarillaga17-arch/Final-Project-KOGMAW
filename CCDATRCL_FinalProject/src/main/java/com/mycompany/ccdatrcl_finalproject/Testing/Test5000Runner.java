package com.mycompany.ccdatrcl_finalproject.Testing;

import com.mycompany.ccdatrcl_finalproject.Testing.DataCreation;
// Think of this class as the Big Red Power Button for your Automated Ticket Factory.
// It is a simple, standalone script used strictly by the developer to trigger the data generation process.
public class Test5000Runner {
    public static void main(String[] args) {
        System.out.println("Starting the 5,000 record generation...");
        // It reaches into your DataCreation class 
        // and tells the factory to start generating and inserting the tickets into MySQL
        DataCreation.populate5000Records(); 
        System.out.println("Database population complete!");
    }
}
