package pl.pja.s29611.new_graph;
import java.util.*;

public class TrainGraph {

    private final List<TrainStation> stations;
    private final List<TrainConnection> connections;
    public Map<TrainStation, List<TrainStation>> neighbors;

    public TrainGraph() {
        this.stations = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.neighbors = new HashMap<>();
    }

    public List<TrainStation> getStations() {
        return this.stations;
    }

    public void addStation(TrainStation station) {
        this.stations.add(station);
        this.neighbors.put(station, new ArrayList<>());
    }

    public List<TrainConnection> getConnections() {
        return this.connections;
    }

    public void addConnection(TrainStation source, TrainStation destination, int distance) {
        TrainConnection connection = new TrainConnection(source, destination, distance);
        this.connections.add(connection);
        this.neighbors.get(source).add(destination);
    }

    public List<TrainStation> getNeighbours(TrainStation station) {
        return this.neighbors.get(station);
    }

    public TrainStation getSource(TrainConnection connection) {
        return connection.getSource();
    }

    public void setDestination(TrainConnection connection, TrainStation destination) {
        connection.setDestination(destination);
        this.neighbors.get(connection.getSource()).add(destination);
    }

    public double getDistance(TrainStation current, TrainStation neighbour) {
        for( TrainConnection connection : getConnections() ) {
            if( connection.getSource().equals(current) && connection.getDestination().equals(neighbour) ) {
                return connection.getDistance();
            }
        }
        // poprawic na blad
        return Integer.MAX_VALUE;
    }

    public TrainConnection getConnection(TrainStation current, TrainStation neighbour){
        for (TrainConnection connection : getConnections()) {
            if (connection.getSource().equals(current) && connection.getDestination().equals(neighbour)) {
                return connection;
            }
        }
            return null;
    }

    public List<TrainStation> findPath(TrainStation start, TrainStation end) {
        Map<TrainStation, TrainStation> cameFrom = new HashMap<>();
        Map<TrainStation, Integer> costSoFar = new HashMap<>();
        PriorityQueue<TrainStation> frontier = new PriorityQueue<>(Comparator.comparingInt(costSoFar::get));
        Set<TrainStation> visited = new HashSet<>();

        // Initialize start node
        frontier.offer(start);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            TrainStation current = frontier.poll();

            // Skip visited nodes
            if (visited.contains(current)) {
                continue;
            }

            // Stop search when end node is reached
            if (current.equals(end)) {
                break;
            }

            visited.add(current);

            // Visit all neighbors of current node
            for (TrainStation neighbor : getNeighbours(current)) {
                int newCost = costSoFar.get(current) + (int)getDistance(current, neighbor);

                // If neighbor has not been visited or has a lower cost, update it
                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    cameFrom.put(neighbor, current);
                    frontier.offer(neighbor);
                }
            }
        }

        // If end node was not reached, return null
        if (!cameFrom.containsKey(end)) {
            return null;
        }

        // Construct path from start to end node
        List<TrainStation> path = new ArrayList<>();
        TrainStation current = end;
        while (!current.equals(start)) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        path.add(0, start);

        return path;
    }


    public static TrainGraph generateTrainNetwork(int numStations, int avgConnectionsPerStation, double rewiringProbability) {
            // Create graph with numStations nodes
            TrainGraph graph = new TrainGraph();
            for (int i = 0; i < numStations; i++) {
                graph.addStation(new TrainStation("Station " + i));
            }

            // Create regular ring lattice with avgConnectionsPerStation neighbors
            int numConnections = numStations * avgConnectionsPerStation / 2;
            int numNeighbors = avgConnectionsPerStation / 2;
            for (int i = 0; i < numStations; i++) {
                for (int j = 1; j <= numNeighbors; j++) {
                    int neighborIndex = (i + j) % numStations;
                    TrainStation neighbor = graph.getStations().get(neighborIndex);
                    graph.addConnection(graph.getStations().get(i), neighbor, 1000 + (int) (Math.random() * 2000));
                }
            }

            // Rewire edges with probability p
            for (TrainConnection connection : graph.getConnections()) {
                if (Math.random() < rewiringProbability) {
                    TrainStation newDestination;
                    do {
                        newDestination = graph.getStations().get((int) (Math.random() * numStations));
                    } while (newDestination == connection.getDestination() || graph.getNeighbours(connection.getSource()).contains(newDestination));
                    connection.setDestination(newDestination);
                }
            }

            return graph;
        }

}
