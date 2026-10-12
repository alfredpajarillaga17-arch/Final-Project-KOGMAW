package com.mycompany.ccdatrcl_finalproject.models;

public class CustomGraph {
    private String[] vertices;                                              // Array to hold the names of the vertices (stages) in the graph
    private int[][] adjMatrix;                                              // Adjacency matrix to represent the edges (connections) between vertices, where the value at adjMatrix[i][j] represents the weight of the edge from vertex i to vertex j
    private int numVertices;                                                // Current number of vertices (stages) in the graph
    private static final int MAX_NODES = 20;                                // Maximum number of vertices (stages) allowed in the graph

    public CustomGraph() {
        vertices = new String[MAX_NODES];                                   // Initialize the array to hold vertex names with a maximum size of MAX_NODES
        adjMatrix = new int[MAX_NODES][MAX_NODES];                          // Initialize the adjacency matrix with a maximum size of MAX_NODES x MAX_NODES
        numVertices = 0;
    }

    private int getIndex(String vertex) {                                   // Returns the index of a vertex in the vertices array, or -1 if not found
        for (int i = 0; i < numVertices; i++) {                             // Loop through the vertices array to find the index of the specified vertex
            if (vertices[i].equals(vertex)) return i;                       // If the vertex is found, return its index
        }
        return -1;
    }

    public void addVertex(String location) {                                // Adds a new vertex (stage) to the graph if it doesn't already exist and if the maximum number of vertices hasn't been reached
        if (getIndex(location) == -1 && numVertices < MAX_NODES) {          // Check if the vertex already exists and if there's room for more vertices
            vertices[numVertices++] = location;                             // Add the new vertex to the vertices array and increment the count of vertices
        }
    }

    public void addEdge(String source, String destination, int weight) {    // Adds an edge (connection) between two vertices (stages) with a specified weight, creating the vertices if they don't already exist
        addVertex(source);                                                  // Ensure the source vertex exists in the graph, adding it if necessary
        addVertex(destination);                                             // Ensure the destination vertex exists in the graph, adding it if necessary
        int srcIdx = getIndex(source);                                      // Get the index of the source vertex in the vertices array
        int destIdx = getIndex(destination);                                // Get the index of the destination vertex in the vertices array
        if (srcIdx != -1 && destIdx != -1) {                                // If both vertices exist, add the edge to the adjacency matrix
            adjMatrix[srcIdx][destIdx] = weight;                            // Set the weight of the edge from source to destination in the adjacency matrix
            adjMatrix[destIdx][srcIdx] = weight;                            // Set the weight of the edge from destination to source in the adjacency matrix (for undirected graph)
        }
    }

    // Custom Queue to handle BFS without importing java.util.LinkedList
    private static class StringQueue {                                      // Custom queue implementation for BFS traversal
        String[] items = new String[50];                                    // Array to hold the items in the queue with a maximum size of 50
        int front = 0, rear = 0;                                            // Indices to track the front and rear of the queue
        void enqueue(String s) { items[rear++] = s; }                       // Adds an item to the rear of the queue
        String dequeue() { return items[front++]; }                         // Removes and returns the item at the front of the queue
        boolean isEmpty() { return front == rear; }                         // Checks if the queue is empty
    }

    public void bfs(String startLocation) {                                 // Performs a breadth-first search (BFS) traversal of the graph starting from the specified vertex (stage)
        int startIdx = getIndex(startLocation);                             // Get the index of the starting vertex in the vertices array
        if (startIdx == -1) {                                               // If the starting vertex doesn't exist in the graph, print an error message and return
            System.out.println("Stage " + startLocation + " does not exist."); // Print an error message indicating that the specified stage does not exist in the graph
            return;                                                         // Return from the method without performing BFS since the starting vertex is invalid
        }

        boolean[] visited = new boolean[numVertices];                       // Array to track which vertices have been visited during the BFS traversal
        StringQueue queue = new StringQueue();                              // Custom queue to manage the order of vertices to visit during BFS

        visited[startIdx] = true;                                           // Mark the starting vertex as visited to avoid revisiting it during the traversal
        queue.enqueue(startLocation);                                       // Add the starting vertex to the queue to begin the BFS traversal

        System.out.println("\n--- BFS Traversal starting from: " + startLocation + " ---"); // Print a message indicating the start of the BFS traversal from the specified starting vertex
        while (!queue.isEmpty()) {                                          // Continue the BFS traversal as long as there are vertices in the queue to visit
            String current = queue.dequeue();                               // Remove and retrieve the next vertex from the queue to process it
            System.out.print(current + " -> ");                             // Print the current vertex to indicate the order of traversal
            int currIdx = getIndex(current);                                // Get the index of the current vertex in the vertices array to access its neighbors in the adjacency matrix

            for (int i = 0; i < numVertices; i++) {                         // Loop through all vertices to find neighbors of the current vertex
                if (adjMatrix[currIdx][i] > 0 && !visited[i]) {             // If there is an edge to vertex i and it hasn't been visited yet, mark it as visited and enqueue it for future processing
                    visited[i] = true;                                      // Mark vertex i as visited to avoid revisiting it during the traversal
                    queue.enqueue(vertices[i]);                             // Add vertex i to the queue to be processed in subsequent iterations of the BFS traversal
                }
            }
        }
        System.out.println("END");
    }

    public void dfs(String startLocation) {                                 // Performs a depth-first search (DFS) traversal of the graph starting from the specified vertex (stage)
        int startIdx = getIndex(startLocation);                             // Get the index of the starting vertex in the vertices array
        if (startIdx == -1) return;                                         // If the starting vertex doesn't exist in the graph, return without performing DFS

        boolean[] visited = new boolean[numVertices];                       // Array to track which vertices have been visited during the DFS traversal
        System.out.println("\n--- DFS Traversal starting from: " + startLocation + " ---"); // Print a message indicating the start of the DFS traversal from the specified starting vertex
        dfsHelper(startIdx, visited);                                       // Call the recursive helper method to perform the DFS traversal starting from the specified vertex index
        System.out.println("END"); 
    }

    private void dfsHelper(int currIdx, boolean[] visited) {                // Recursive helper method for DFS traversal that visits vertices in depth-first order
        visited[currIdx] = true;                                            // Mark the current vertex as visited to avoid revisiting it during the traversal
        System.out.print(vertices[currIdx] + " -> ");                       // Print the current vertex to indicate the order of traversal

        for (int i = 0; i < numVertices; i++) {                             // Loop through all vertices to find neighbors of the current vertex
            if (adjMatrix[currIdx][i] > 0 && !visited[i]) {                 // If there is an edge to vertex i and it hasn't been visited yet, recursively call dfsHelper for vertex i
                dfsHelper(i, visited);                                      // Recursively visit vertex i in depth-first order
            }
        }
    }
    
    // Returns an array of valid next phases based on the workflow graph
    public String[] getAdjacentPhases(String phase) {                       // Returns an array of valid next phases based on the workflow graph
        int idx = getIndex(phase);                                          // Get the index of the specified phase in the vertices array
        if (idx == -1) return new String[0];                                // If the specified phase doesn't exist in the graph, return an empty array
        
        // Count neighbors to properly size the return array
        int count = 0;                                                      // Initialize a counter to keep track of the number of adjacent phases (neighbors) for the specified phase
        for (int i = 0; i < numVertices; i++) {                             // Loop through all vertices to count the number of neighbors (adjacent phases) for the specified phase
            if (adjMatrix[idx][i] > 0) count++;                             // If there is an edge from the specified phase to vertex i, increment the count of neighbors
        }
        
        String[] neighbors = new String[count];                             // Create an array to hold the names of the adjacent phases (neighbors) with the size equal to the count of neighbors
        int nIdx = 0;                                                       // Initialize an index to keep track of the position in the neighbors array
        for (int i = 0; i < numVertices; i++) {                             // Loop through all vertices again to populate the neighbors array with the names of the adjacent phases
            if (adjMatrix[idx][i] > 0) {                                    // If there is an edge from the specified phase to vertex i, add the name of vertex i to the neighbors array
                neighbors[nIdx++] = vertices[i];                            // Add the name of the adjacent phase (vertex i) to the neighbors array and increment the index for the next neighbor
            }
        }
        return neighbors;                                                   // Return the array of adjacent phases (neighbors) for the specified phase
    }
}
