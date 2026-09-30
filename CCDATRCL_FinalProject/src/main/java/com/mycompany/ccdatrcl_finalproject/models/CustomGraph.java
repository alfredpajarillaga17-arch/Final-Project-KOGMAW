package com.mycompany.ccdatrcl_finalproject.models;

import java.util.*;

/**
 * Custom Graph structure using an Adjacency List.
 * Represents delivery locations or repair dependency networks between service hubs/devices.
 */
public class CustomGraph {

    private static class Edge {
        String destination;
        int weight; // Distance in kilometers or cost factor

        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return destination + " (" + weight + " km)";
        }
    }

    private Map<String, List<Edge>> adjacencyList;

    public CustomGraph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a location/hub vertex to the graph.
     */
    public void addVertex(String location) {
        adjacencyList.putIfAbsent(location, new ArrayList<>());
    }

    /**
     * Adds a bi-directional (undirected) road/connection between two locations with a weight.
     */
    public void addEdge(String source, String destination, int weight) {
        addVertex(source);
        addVertex(destination);
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight)); // Undirected
    }

    /**
     * Performs a Breadth-First Search (BFS) to explore network reachability starting from a root location.
     */
    public void bfs(String startLocation) {
        if (!adjacencyList.containsKey(startLocation)) {
            System.out.println("Location " + startLocation + " does not exist in the graph.");
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

    /**
     * Displays the complete adjacency list representation of the graph.
     */
    public void displayGraph() {
        System.out.println("\n--- Service Delivery & Location Graph ---");
        for (String node : adjacencyList.keySet()) {
            System.out.println(node + " connects to: " + adjacencyList.get(node));
        }
    }

    // Main method for independent testing
    public static void main(String[] args) {
        CustomGraph graph = new CustomGraph();

        // Build a sample delivery location network (e.g., Clark / Pampanga hubs)
        graph.addEdge("Main Workshop (Clark)", "Hub A (Angeles)", 10);
        graph.addEdge("Main Workshop (Clark)", "Hub B (Mabalacat)", 7);
        graph.addEdge("Hub A (Angeles)", "Hub C (San Fernando)", 15);
        graph.addEdge("Hub B (Mabalacat)", "Hub D (Bamban)", 12);
        graph.addEdge("Hub C (San Fernando)", "Hub D (Bamban)", 25);

        // Display Network
        graph.displayGraph();

        // Test BFS Traversal
        graph.bfs("Main Workshop (Clark)");
    }
}