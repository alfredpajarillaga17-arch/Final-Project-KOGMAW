package com.mycompany.ccdatrcl_finalproject.models;

public class Appointment {
    private String id;
    private String customerName;
    private String contactNumber;
    private String deviceType;
    private String brand;
    private String model;
    private String reportedIssue;
    private String appointmentDate;
    private String assessmentPhase; // New field

    public Appointment(String id, String customerName, String contactNumber, String deviceType, String brand, String model, String reportedIssue, String appointmentDate, String assessmentPhase) {
        this.id = id;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.deviceType = deviceType;
        this.brand = brand;
        this.model = model;
        this.reportedIssue = reportedIssue;
        this.appointmentDate = appointmentDate;
        this.assessmentPhase = assessmentPhase;
    }

    // Existing Getters
    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getDeviceType() { return deviceType; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getReportedIssue() { return reportedIssue; }
    public String getAppointmentDate() { return appointmentDate; }
    
    // New Getter and Setter for Phase
    public String getAssessmentPhase() { return assessmentPhase; }
    public void setAssessmentPhase(String assessmentPhase) { this.assessmentPhase = assessmentPhase; }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + customerName + " | Contact Number: " + contactNumber + " | Phase: " + assessmentPhase + " | Date: " + appointmentDate;
    }
}