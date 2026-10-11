package com.mycompany.ccdatrcl_finalproject.models;

//DI KO ALAM PERO ITO YUNG STRUCTURE NG TREE

public class BSTNode {
    public Appointment data; //STORES THE ID, NAME, DEVICE, EVERY DATA INSIDE Appointment class
    public BSTNode left, right; //creates left and right branches where left has lower numbers and right has bigger numbers

    public BSTNode(Appointment data) {
        this.data = data; //Appointment data is equal to the inputted parameter like the ID, NAME, DEVICE
        this.left = null; //left is null at the start
        this.right = null; //right is null too
    }
}
    