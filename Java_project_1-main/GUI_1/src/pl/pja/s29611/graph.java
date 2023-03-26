import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

 
// A class to store a graph edge
class Edge
{
    int src, dest, weight;
 
    Edge(int src, int dest, int weight)
    {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}
 
// A class to store adjacency list nodes
class Node
{
    int value, weight;
 
    Node(int value, int weight)
    {
        this.value = value;
        this.weight = weight;
    }
 
    @Override
    public String toString() {
        return this.value + " (" + this.weight + ")";
    }
}
 
// A class to represent a graph object
class Graph
{
    // A list of lists to represent an adjacency list
    List<List<Node>> adjList = new ArrayList<>();
 
    // Constructor to construct a graph
    public Graph(List<Edge> edges)
    {
        // find the maximum numbered value
        int n = 0;
        for (Edge e: edges) {
            n = Integer.max(n, Integer.max(e.src, e.dest));
        }
 
        // allocate memory for the adjacency list
        for (int i = 0; i <= n; i++) {
            adjList.add(i, new ArrayList<>());
        }
 
        // add edges to the directed graph
        for (Edge e: edges)
        {
            // allocate new node in adjacency list from src to dest
            adjList.get(e.src).add(new Node(e.dest, e.weight));
 
            // uncomment below line for undirected graph
 
            //allocate new node in adjacency list from dest to src
            adjList.get(e.dest).add(new Node(e.src, e.weight));
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
class Main
{
    public static void main (String[] args)
    {
        // Input: List of edges in a weighted digraph (as per the above diagram)
        // tuple `(x, y, w)` represents an edge from `x` to `y` having weight `w`

        List<Edge> edges_list = new ArrayList<>();
        Random rand = new Random();

        int cities_amount = 100;
        for(int i = 0; i < cities_amount; i++){
            for(int j = 0; j < rand.nextInt(cities_amount / 8); j++){
                edges_list.add(new Edge(i,rand.nextInt(cities_amount),rand.nextInt(50)));
            }
        }

             
        // construct a graph from the given list of edges
        Graph graph = new Graph(edges_list);
 
        // print adjacency list representation of the graph
        Graph.printGraph(graph);

        for(int i = 1; i < 20; i++){
            System.out.println(graph.findPath(rand.nextInt(100), rand.nextInt(100)));  
        }
        
        

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