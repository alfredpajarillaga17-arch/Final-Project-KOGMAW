package com.mycompany.ccdatrcl_finalproject.models;

public class CustomGraph {
    private String[] nodes;
    private int[][] adjacencyMatrix;
    private int numNodes;
    private final int MAX_NODES = 15; 

    public CustomGraph() {
        nodes = new String[MAX_NODES];
        adjacencyMatrix = new int[MAX_NODES][MAX_NODES];
        numNodes = 0;
    }

    private int getOrAddNode(String nodeName) {
        for (int i = 0; i < numNodes; i++) {
            if (nodes[i].equals(nodeName)) {
                return i;
            }
        }

        if (numNodes < MAX_NODES) {
            nodes[numNodes] = nodeName;
            return numNodes++;
        }
        return -1; 
    }

    public void addEdge(String source, String destination, int timeInMinutes) {
        int srcIndex = getOrAddNode(source);
        int destIndex = getOrAddNode(destination);

        if (srcIndex != -1 && destIndex != -1) {
            adjacencyMatrix[srcIndex][destIndex] = timeInMinutes;
        }
    }

    public void printWorkflow() {
        System.out.println("\n--- Active Repair Workflow Dependencies ---");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjacencyMatrix[i][j] > 0) {
                    System.out.println(nodes[i] + " -> " + nodes[j] + " (" + adjacencyMatrix[i][j] + " mins)");
                }
            }
        }
    }
}