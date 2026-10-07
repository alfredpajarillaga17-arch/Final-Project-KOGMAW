package com.mycompany.ccdatrcl_finalproject.models;

import java.util.*;

public class CustomGraph {

    // Inner class representing a weighted edge between workflow stages
    private static class Edge {
        String destination;
        int weight; // Duration/time in minutes or steps

        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return destination + " (" + weight + " mins)";
        }
    }

    private Map<String, List<Edge>> adjacencyList;

    public CustomGraph() {
        this.adjacencyList = new HashMap<>();
    }

    // Add new stage/vertex to the graph
    public void addVertex(String location) {
        adjacencyList.putIfAbsent(location, new ArrayList<>());
    }

    // Add undirected weighted edge between two workflow stages
    public void addEdge(String source, String destination, int weight) {
        addVertex(source);
        addVertex(destination);
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight));
    }

    // Execute Breadth-First Search (BFS) traversal
    public void bfs(String startLocation) {
        if (!adjacencyList.containsKey(startLocation)) {
            System.out.println("Stage " + startLocation + " does not exist in the graph.");
            return;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(startLocation);
        queue.add(startLocation);

        System.out.println("\n--- BFS Traversal starting from: " + startLocation + " ---");
        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.print(current + " -> ");

            for (Edge edge : adjacencyList.get(current)) {
                if (!visited.contains(edge.destination)) {
                    visited.add(edge.destination);
                    queue.add(edge.destination);
                }
            }
        }
        System.out.println("END");
    }

    // Execute Depth-First Search (DFS) traversal for T08
    public void dfs(String startLocation) {
        if (!adjacencyList.containsKey(startLocation)) {
            System.out.println("Stage " + startLocation + " does not exist in the graph.");
            return;
        }

        Set<String> visited = new HashSet<>();
        
        System.out.println("\n--- DFS Traversal starting from: " + startLocation + " ---");
        dfsHelper(startLocation, visited);
        System.out.println("END");
    }

    // Recursive helper method for DFS
    private void dfsHelper(String current, Set<String> visited) {
        // Mark the current stage as visited and print it
        visited.add(current);
        System.out.print(current + " -> ");

        // Recursively visit all unvisited connected stages
        for (Edge edge : adjacencyList.get(current)) {
            if (!visited.contains(edge.destination)) {
                dfsHelper(edge.destination, visited);
            }
        }
    }
    // Display complete graph adjacency structure
    public void displayGraph() {
        System.out.println("\n--- Technical Repair Workflow Graph ---");
        for (String node : adjacencyList.keySet()) {
            System.out.println(node + " connects to: " + adjacencyList.get(node));
        }
    }
}
