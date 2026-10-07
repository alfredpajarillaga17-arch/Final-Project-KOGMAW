package com.mycompany.ccdatrcl_finalproject.utils;

import com.mycompany.ccdatrcl_finalproject.models.*;

public class DataManager {
    // The single instance of this class
    private static DataManager instance;

    // Core Data Structures
    private final CustomHeap priorityQueue;
    private final CustomBST searchTree;
    private final CustomHashTable instantCache;
    private final CustomQueue intakeQueue;
    private final CustomStack undoStack;
    private final CustomGraph workflowGraph;
    private final CustomLinkedList historyLog;

    // Private constructor prevents instantiation from other classes
    private DataManager() {
        this.priorityQueue = new CustomHeap(100); 
        this.searchTree = new CustomBST();
        this.instantCache = new CustomHashTable(128); 
        this.intakeQueue = new CustomQueue();
        this.undoStack = new CustomStack();
        this.workflowGraph = new CustomGraph();
        this.historyLog = new CustomLinkedList();

        // Initialize the graph topology defined in workflow_graph.txt
        setupWorkflowGraph();
    }

    // Global access point
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void setupWorkflowGraph() {
        workflowGraph.addEdge("Initial Diagnostic", "Disassembly", 15);
        workflowGraph.addEdge("Initial Diagnostic", "Part Ordering", 10);
        workflowGraph.addEdge("Disassembly", "Part Ordering", 15);
        workflowGraph.addEdge("Disassembly", "Cleaning", 10);
        workflowGraph.addEdge("Disassembly", "Component Swap", 5);
        workflowGraph.addEdge("Part Ordering", "Component Swap", 30);
        workflowGraph.addEdge("Cleaning", "Component Swap", 10);
        workflowGraph.addEdge("Cleaning", "Reassembly", 15);
        workflowGraph.addEdge("Component Swap", "Reassembly", 25);
        workflowGraph.addEdge("Component Swap", "Firmware Flash", 5);
        workflowGraph.addEdge("Reassembly", "Firmware Flash", 10);
        workflowGraph.addEdge("Reassembly", "Stress Test", 5);
        workflowGraph.addEdge("Firmware Flash", "Stress Test", 45);
        workflowGraph.addEdge("Stress Test", "Final QA", 20);
        workflowGraph.addEdge("Final QA", "Ready for Pickup", 5);
    }

    // Getters for controllers to access the structures
    public CustomHeap getPriorityQueue() { 
        return priorityQueue; 
    }
    public CustomBST getSearchTree() { 
        return searchTree; 
    }
    public CustomHashTable getInstantCache() { 
        return instantCache; 
    }
    public CustomQueue getIntakeQueue() { 
        return intakeQueue; 
    }
    public CustomStack getUndoStack() { 
        return undoStack; 
    }
    public CustomGraph getWorkflowGraph() { 
        return workflowGraph; 
    }
    public CustomLinkedList getHistoryLog() { 
        return historyLog; 
    }
}
