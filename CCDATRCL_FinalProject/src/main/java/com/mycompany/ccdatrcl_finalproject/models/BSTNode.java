package com.mycompany.ccdatrcl_finalproject.models;

//DI KO ALAM PERO ITO YUNG STRUCTURE NG TREE

public class BSTNode {
    public Appointment data;
    public BSTNode left, right;

    public BSTNode(Appointment data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
