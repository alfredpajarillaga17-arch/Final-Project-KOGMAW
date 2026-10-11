package com.mycompany.ccdatrcl_finalproject.models;

public class Appointment {
    // encapsulated fields para sa pag store ng mga specific details about sa repair ticket
    private String id;
    private String customerName;
    private String contactNumber;
    private String deviceType;
    private String brand;
    private String model;
    private String reportedIssue;
    private String appointmentDate;
    private String assessmentPhase; // New field

    // constructor para mainitialize yung mga fields ng appointment
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

    // getter tsaka setter method para magkaron ng access sa mga encapsulated fields ng appointment
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

    /*  
     *  Inooverride niya yung default output like @7b23ec81 sa console and ginagawa niyang mas readable yung output para sa mga appointment details
     *  sa system na 'to it is used for customBST.java during the inOrder, preorder, and postorder traversal whenever sys.out.print(node.data) is executed
     */
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + customerName + " | Contact Number: " + contactNumber + " | Phase: " + assessmentPhase + " | Date: " + appointmentDate;
    }
}
