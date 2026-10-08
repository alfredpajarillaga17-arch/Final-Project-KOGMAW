package com.mycompany.ccdatrcl_finalproject.models;

public class CustomGraph {
    private String[] vertices;
    private int[][] adjMatrix;
    private int numVertices;
    private static final int MAX_NODES = 20;

    public CustomGraph() {
        vertices = new String[MAX_NODES];
        adjMatrix = new int[MAX_NODES][MAX_NODES];
        numVertices = 0;
    }

    private int getIndex(String vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) return i;
        }
        return -1;
    }

    public void addVertex(String location) {
        if (getIndex(location) == -1 && numVertices < MAX_NODES) {
            vertices[numVertices++] = location;
        }
    }

    public void addEdge(String source, String destination, int weight) {
        addVertex(source);
        addVertex(destination);
        int srcIdx = getIndex(source);
        int destIdx = getIndex(destination);
        if (srcIdx != -1 && destIdx != -1) {
            adjMatrix[srcIdx][destIdx] = weight;
            adjMatrix[destIdx][srcIdx] = weight;
        }
    }

    // Custom Queue to handle BFS without importing java.util.LinkedList
    private static class StringQueue {
        String[] items = new String[50];
        int front = 0, rear = 0;
        void enqueue(String s) { items[rear++] = s; }
        String dequeue() { return items[front++]; }
        boolean isEmpty() { return front == rear; }
    }

    public void bfs(String startLocation) {
        int startIdx = getIndex(startLocation);
        if (startIdx == -1) {
            System.out.println("Stage " + startLocation + " does not exist.");
            return;
        }

        boolean[] visited = new boolean[numVertices];
        StringQueue queue = new StringQueue();

        visited[startIdx] = true;
        queue.enqueue(startLocation);

        System.out.println("\n--- BFS Traversal starting from: " + startLocation + " ---");
        while (!queue.isEmpty()) {
            String current = queue.dequeue();
            System.out.print(current + " -> ");
            int currIdx = getIndex(current);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[currIdx][i] > 0 && !visited[i]) {
                    visited[i] = true;
                    queue.enqueue(vertices[i]);
                }
            }
        }
        System.out.println("END");
    }

    public void dfs(String startLocation) {
        int startIdx = getIndex(startLocation);
        if (startIdx == -1) return;

        boolean[] visited = new boolean[numVertices];
        System.out.println("\n--- DFS Traversal starting from: " + startLocation + " ---");
        dfsHelper(startIdx, visited);
        System.out.println("END");
    }

    private void dfsHelper(int currIdx, boolean[] visited) {
        visited[currIdx] = true;
        System.out.print(vertices[currIdx] + " -> ");

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[currIdx][i] > 0 && !visited[i]) {
                dfsHelper(i, visited);
            }
        }
    }
    
    // Returns an array of valid next phases based on the workflow graph
    public String[] getAdjacentPhases(String phase) {
        int idx = getIndex(phase);
        if (idx == -1) return new String[0];
        
        // Count neighbors to properly size the return array
        int count = 0;
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[idx][i] > 0) count++;
        }
        
        String[] neighbors = new String[count];
        int nIdx = 0;
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[idx][i] > 0) {
                neighbors[nIdx++] = vertices[i];
            }
        }
        return neighbors;
    }
}