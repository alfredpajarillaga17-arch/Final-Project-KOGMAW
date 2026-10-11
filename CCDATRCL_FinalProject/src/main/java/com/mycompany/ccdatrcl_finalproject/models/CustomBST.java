package com.mycompany.ccdatrcl_finalproject.models;

public class CustomBST {
    private BSTNode root; // root ng tree with left and right and appointment data

    public CustomBST() {
        this.root = null; // Walang laman na binary tree, no nodes no nothing, nada, wala na. Ito yung binary tree mismo
    }

    // ==========================================================
    // INSERT
    // ==========================================================

    // public method na cina-call sa main
    public void insert(Appointment appointment) {
        root = insert(root, appointment);
        //calls the private insert method, and it basically turns the first node into root
        // and then when a new node is inserted that new node's value gets compared
        //to the current node which is the root, if its small it goes to the left and if its bigger it goes to the right
    }

    /*
     * WHERE THE MAGIC HAPPENS
     * 
     */
    private BSTNode insert(BSTNode node, Appointment appointment) {
        // BASE CASE NG TREE AWIOFJHSAIOFIOAF
        if (node == null)
            return new BSTNode(appointment); //if node is null it returns the appointment data so it can become the root

        // Gets the appointment ID of the new node and compares it to the current node's ID
        // and using the if statements below it gets assigned to left or right depending on the value
        int apt = appointment.getId().compareTo(node.data.getId());

        // IF APPOINTMENT ID IS LESS THAN 0 PUPUNTA SA LEFT IF MORE THAN 0 PUPUNTA SA RIGHT
        // tapos yung mga new appointment na nailagay magiging root nila is yung values ng left or right depende sa new id
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

    // ==========================================================
    // PRE-ORDER TRAVERSAL
    // ==========================================================
    public void preOrder() {
        System.out.println("\n--- BST Pre-Order Traversal ---");
        preOrder(root);
    }

    private void preOrder(BSTNode node) {
        if (node == null)
            return;
        System.out.println(node.data);
        preOrder(node.left);
        preOrder(node.right);
    }

    // ==========================================================
    // POST-ORDER TRAVERSAL
    // ==========================================================
    public void postOrder() {
        System.out.println("\n--- BST Post-Order Traversal ---");
        postOrder(root);
    }

    private void postOrder(BSTNode node) {
        if (node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.data);
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

    // ----------SEARCH FUNCTION (By: Darg)----------//

    // Basically, this function is a recursive search function that traverses the
    // binary search tree (BST) to find an appointment with a specific ID. It starts
    // from the root node and compares the given ID with the ID of the current
    // node's data. If it finds a match, it returns the corresponding appointment.
    // If the ID is less than the current node's ID, it continues searching in the
    // left subtree; if it's greater, it searches in the right subtree. If it
    // reaches a null node, it means the appointment with the specified ID does not
    // exist in the tree, and it returns null.

    private Appointment search(BSTNode node, String id) {
        /*
         * Ito yung stopper (base case), meaning pag wala ng laman yung node
         * (like walang laman yung both left and right subtrees),
         * Edi mag rereturn siya ng null.
         * KASI NGA WALA NGANING LAMAN YUNG NODE
         */
        if (node == null) {
            return null;
        }

        /*
         * Ito naman pag may nahanap siyang node na same value sa id na hinahanap mo,
         * Edi rereturn niya yung nahanap niya.
         * KASI SAME NGANI YUNG VALUE NG ID NA HINAHANAP MO SA NODE DATA
         * 
         * (ps. yung node.data.getId() is yung nakastore sa current node.
         * yung id naman is yung hinahanap mo.)
         */

        if (id.equals(node.data.getId())) {
            return node.data;
        }

        /*
         * Dito naman pag wala hindi siya same value, pag ko-compare niya yung hinahanap
         * mo sa current node.
         * 
         * Pag mas maliit yung node, magiging value niya is -1
         * Pag mag malaki, edi 1
         * Pag same, edi 0 (pero pag 0 na, edi nahanap na)
         * 
         * Take for example, KGW-T600 yung hinahanap mo,
         * Current node is Y450,
         * ignore the letter, is Y450 < T600? Yes, edi -1, punta sa left, repeat
         * 
         * (ps. magiging string comparison yung id.compareTo(node.data.getId()) kasi
         * string yung id,
         * so basically lexagraphical comparison not numerical)
         * 
         */

        int comparison = id.compareTo(node.data.getId());

        if (comparison < 0) {
            return search(node.left, id);
        } else {
            return search(node.right, id);
        }
    }

    // ----------DELETE FUNCTION (By: Darg)----------//

    public void delete(String id) {
        root = delete(root, id);
    }

    private BSTNode delete(BSTNode node, String id) {
        if (node == null) {
            return null;
        }

        int comparison = id.compareTo(node.data.getId());

        if (comparison < 0) {
            node.left = delete(node.left, id);
        } else if (comparison > 0) {
            node.right = delete(node.right, id);
        } else {

            if (node.left == null) {
                return node.right;

            } else if (node.right == null) {
                return node.left;
            }

            BSTNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }

            node.data = successor.data;

            node.right = delete(
                    node.right,
                    successor.data.getId());
        }

        return node;
    }

}