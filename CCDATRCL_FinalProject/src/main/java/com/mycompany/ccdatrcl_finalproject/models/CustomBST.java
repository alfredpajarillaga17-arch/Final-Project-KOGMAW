package com.mycompany.ccdatrcl_finalproject.models;

public class CustomBST {
    private BSTNode root; // root ng tree

    public CustomBST() {
        this.root = null; // Walang laman na binary tree, no nodes no nothing, nada, wala na.
    }

    // ==========================================================
    // INSERT
    // ==========================================================

    // PUBLIC METHOD PARA MA CALL SA MAIN AND MADADALA RIN DITO YUNG INSERTED DATA.
    public void insert(Appointment appointment) {
        root = insert(root, appointment);
    }

    /*
     * WHERE THE MAGIC HAPPENS
     * 
     */
    private BSTNode insert(BSTNode node, Appointment appointment) {
        // BASE CASE NG TREE AWIOFJHSAIOFIOAF

        if (node == null)
            return new BSTNode(appointment); //

        // Gets the appointment ID tapos compares it to the current appoint ID in the
        // node.
        int apt = appointment.getId().compareTo(node.data.getId());

        // IF APPOINTMENT ID IS LESS THAN 0 PUPUNTA SA LEFT IF MORE THAN 0 PUPUNTA SA
        // RIGHT
        if (apt < 0) {
            node.left = insert(node.left, appointment);
        } else if (apt > 0) {
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
        if (node == null)
            return;

        inOrder(node.left);
        System.out.println(node.data);
        inOrder(node.right);

    }

    public Appointment search(String id) {
        return search(root, id);
    }

   

    private Appointment search(BSTNode node, String id) {
        if (node == null) {
            return null; // mao ni ang base case kung dili maka pangita sa tree, null ang i-return ani niya
        }

        if (id.equals(node.data.getId())) {
            return node.data; // nya diri kung makit-an niya ang appointment ID sa node, i-return niya ang data sa appointment na naay matching ID
        }

        int comparison = id.compareTo(node.data.getId());

        if (comparison < 0) {
            return search(node.left, id); // Search in the left subtree
        } else {
            return search(node.right, id); // Search in the right subtree
        }
    }

    public void delete(String id) {
        root = delete(root, id);
    }

    private BSTNode delete(BSTNode node, String id) {
        if (node == null) {
            return null; // Base case: If the node is null, return null
        }

        int comparison = id.compareTo(node.data.getId());

        if (comparison < 0) {
            node.left = delete(node.left, id); // Search in the left subtree
        } else if (comparison > 0) {
            node.right = delete(node.right, id); // Search in the right subtree
        } else {
            // Node to be deleted found

            // Case 2: Node has one child
            if (node.left == null) {
                return node.right; // Replace with the right child

            } else if (node.right == null) {
                return node.left; // Replace with the left child
            }

            // Case 3: Node has two children
        BSTNode successor = node.right; // Find the minimum value in the right subtree
            while (successor.left != null) {
            successor = successor.left;
        }

        node.data = successor.data;

        node.right = delete(
                node.right,
                successor.data.getId()
        );
    }

        return node;
    }

}