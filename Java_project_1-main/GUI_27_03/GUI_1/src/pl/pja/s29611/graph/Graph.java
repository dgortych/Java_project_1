package pl.pja.s29611.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    // A list of lists to represent an adjacency list
    private List<List<Node>> adjList = new ArrayList<>();

    // Constructor to construct a graph
    public Graph(List<Edge> edges)
    {
        // find the maximum numbered value
        int n = 0;
        for (Edge e: edges) {
            n = Integer.max(n, Integer.max(e.getSource(), e.getDestination()));
        }

        // allocate memory for the adjacency list
        for (int i = 0; i <= n; i++) {
            adjList.add(i, new ArrayList<>());
        }

        // add edges to the directed graph
        for (Edge e: edges)
        {
            // allocate new node in adjacency list from src to dest
            adjList.get(e.getSource()).add(new Node(e.getDestination(), e.getWeight()));

            // uncomment below line for undirected graph

            //allocate new node in adjacency list from dest to src
            adjList.get(e.getDestination()).add(new Node(e.getSource(), e.getWeight()));
        }
    }

    // Function to print adjacency list representation of a graph
    public static void printGraph(Graph graph)
    {
        int src = 0;
        int n = graph.adjList.size();

        while (src < n)
        {
            // print current value and all its neighboring vertices
            for (Node edge: graph.adjList.get(src)) {
                System.out.printf("%d ——> %s\t", src, edge);
            }

            System.out.println();
            src++;
        }
    }

    public List<Integer> findPath(int start, int end) {
        boolean[] visited = new boolean[adjList.size()];
        List<Integer> path = new ArrayList<>();
        dfs(start, end, visited, path);
        return path;
    }

    private boolean dfs(int current, int end, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);
        if (current == end) {
            return true;
        }
        for (Node neighbor : adjList.get(current)) {
            if (!visited[neighbor.value]) {
                if (dfs(neighbor.value, end, visited, path)) {
                    return true;
                }
            }
        }
        path.remove(path.size() - 1);
        return false;
    }
}

/*
class DijkstraAlgorithm {
    public static void dijkstra(Graph graph, int source, int destination) {
        Map<Integer, Integer> weights = new HashMap<>();
        Map<Integer, Integer> parents = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(source, 0));
        weights.put(source, 0);
        parents.put(source, null);
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            if (curr.value == destination) {
                break;
            }
            if (curr.weight > weights.get(curr.value)) {
                continue;
            }
            for (Node neighbor : graph.adjList.get(curr.value)) {
                int weight = curr.weight + neighbor.weight;
                if (!weights.containsKey(neighbor.value) || weight < weights.get(neighbor.value)) {
                    weights.put(neighbor.value, weight);
                    parents.put(neighbor.value, curr.value);
                    pq.offer(new Node(neighbor.value, weight));
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        if (!parents.containsKey(destination)) {
            System.out.println("There is no path from " + source + " to " + destination);
            return;
        }
        Integer value = destination;
        while (value != null) {
            path.add(value);
            value = parents.get(value);
        }
        Collections.reverse(path);

        System.out.println("The shortest path from " + source + " to " + destination + " is " + path);
    }
}
*/
/*
public List<Integer> findShortestPath(int source, int destination) {
    Map<Integer, Integer> weights = new HashMap<>();
    Map<Integer, Integer> parents = new HashMap<>();
    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.offer(new Node(source, 0));
    weights.put(source, 0);
    parents.put(source, null);
    while (!pq.isEmpty()) {
        Node curr = pq.poll();
        if (curr.value == destination) {
            break;
        }
        if (curr.weight > weights.get(curr.value)) {
            continue;
        }
        for (int neighbor : graph.get(curr.value).keySet()) {
            int weight = curr.weight + graph.get(curr.value).get(neighbor);
            if (!weights.containsKey(neighbor) || weight < weights.get(neighbor)) {
                weights.put(neighbor, weight);
                parents.put(neighbor, curr.value);
                pq.offer(new Node(neighbor, weight));
            }
        }
    }
    List<Integer> path = new ArrayList<>();
    if (!parents.containsKey(destination)) {
        return path;
    }
    int value = destination;
    while (value) {
        path.add(value);
        value = parents.get(value);
    }
    Collections.reverse(path);
    return path;
}
*/


