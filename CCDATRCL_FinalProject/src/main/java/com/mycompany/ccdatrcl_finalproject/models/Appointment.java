package com.mycompany.ccdatrcl_finalproject.models;

import java.time.LocalDate;

public class Appointment {;
    private String id;
    private String customerName;
    private String deviceType;
    private String brand;
    private String model;
    private String reportedIssue;
    private String appointmentDate;

    public Appointment(String id, String customerName, String deviceType, String brand, String model, String reportedIssue, String appointmentDate) {
        this.id = id;
        this.customerName = customerName; // Make sure this line exists!
        this.deviceType = deviceType;
        this.brand = brand;
        this.model = model;
        this.reportedIssue = reportedIssue;
        this.appointmentDate = appointmentDate;
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getReportedIssue() {
        return reportedIssue;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + customerName + " | Device: " + deviceType 
               + " (" + brand + ") | Date: " + appointmentDate;
    }
}
