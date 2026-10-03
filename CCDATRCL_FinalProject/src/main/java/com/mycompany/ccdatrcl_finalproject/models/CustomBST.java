package com.mycompany.ccdatrcl_finalproject.models;

public class CustomBST {
    private BSTNode root; //root ng tree



    public CustomBST() {
        this.root = null; //Walang laman na binary tree, no nodes no nothing, nada, wala na.
    }

    // ==========================================================
    // INSERT
    // ==========================================================

    //PUBLIC METHOD PARA MA CALL SA MAIN AND MADADALA RIN DITO YUNG INSERTED DATA.
    public void insert(Appointment appointment) {
        root = insert(root, appointment);
    }

    /*WHERE THE MAGIC HAPPENS

    */
    private BSTNode insert(BSTNode node, Appointment appointment) {
        //BASE CASE NG TREE AWIOFJHSAIOFIOAF

        if (node == null) return new BSTNode(appointment); //

        //Gets the appointment ID tapos compares it to the current appoint ID in the node.
        int apt = appointment.getId().compareTo(node.data.getId());

        //IF APPOINTMENT ID IS LESS THAN 0 PUPUNTA SA LEFT IF MORE THAN 0 PUPUNTA SA RIGHT
        if (apt < 0) {
            node.left = insert(node.left, appointment);
        }else if (apt > 0) {
            node.right = insert(node.right, appointment);
        }


        return node;
    }

    // ==========================================================
    // IN-ORDER TRAVERSAL (prints everything sorted by ID)
    // ==========================================================


    public void inOrder() {
        System.out.println("\n==================================================");
        System.out.println("===               BST SORTED OUTPUT           ===");
        System.out.println("==================================================");
        inOrder(root);


    }

    private void inOrder(BSTNode node) {
        if (node == null) return;

        inOrder(node.left);
        System.out.println(node.data);
        inOrder(node.right);

    }


    // ==========================================================
    // SEARCCCCCCCCCCCHHHHHHH TODO WALA PANG SEARCH
    // ==========================================================

    // ==========================================================
    // DELEEETE TODO WALA PANG DELETE
    // ==========================================================


}