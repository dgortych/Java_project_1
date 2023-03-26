import java.util.*;

class Graph {
    private int V; // liczba wierzchołków
    private LinkedList<Integer> adjList[]; // lista sąsiedztwa

    // konstruktor
    public Graph(int v) {
        V = v;
        adjList = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    // dodaj krawędź
    public void addEdge(int v, int w) {
        adjList[v].add(w);
        adjList[w].add(v); // graf nieskierowany
    }

    // funkcja znajdująca drogę między dwoma wierzchołkami
    public List<Integer> findPath(int start, int end) {
        boolean visited[] = new boolean[V];
        int parent[] = new int[V];

        // inicjalizacja tablic
        for (int i = 0; i < V; i++) {
            visited[i] = false;
            parent[i] = -1;
        }

        // tworzenie kolejki i dodanie startowego wierzchołka
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        // bfs
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            // przeglądanie sąsiadów
            Iterator<Integer> it = adjList[curr].listIterator();
            while (it.hasNext()) {
                int next = it.next();
                if (!visited[next]) {
                    visited[next] = true;
                    parent[next] = curr;
                    queue.add(next);
                }
            }
        }

        // tworzenie ścieżki
        List<Integer> path = new ArrayList<>();
        int curr = end;
        while (curr != -1) {
            path.add(curr);
            curr = parent[curr];
        }
        Collections.reverse(path);
        return path;
    }
}

class Main
{
    public static void main (String[] args)
    {
       
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        
        List<Integer> path = g.findPath(0, 5);

    }
}

