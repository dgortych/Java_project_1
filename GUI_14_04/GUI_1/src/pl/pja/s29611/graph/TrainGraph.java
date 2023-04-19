package pl.pja.s29611.graph;
import java.util.*;

public class TrainGraph {

    private final List<TrainStation> stations;
    private final List<TrainConnection> connections;
    public Map<TrainStation, List<TrainStation>> neighbours;

    public TrainGraph() {
        this.stations = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.neighbours = new HashMap<>();
    }

    public List<TrainStation> getStations() {
        return this.stations;
    }

    public void addStation(TrainStation station) {
        this.stations.add(station);
        this.neighbours.put(station, new ArrayList<>());
    }

    public List<TrainConnection> getConnections() {
        return this.connections;
    }

    public void addConnection(TrainStation source, TrainStation destination, double distance) {
        TrainConnection connection = new TrainConnection(source, destination, distance);
        this.connections.add(connection);
        this.neighbours.get(source).add(destination);
    }

    public List<TrainStation> getNeighbours(TrainStation station) {
        return this.neighbours.get(station);
    }

    public TrainStation getSource(TrainConnection connection) {
        return connection.getSource();
    }

    public void setDestination(TrainConnection connection, TrainStation destination) {
        connection.setDestination(destination);
        this.neighbours.get(connection.getSource()).add(destination);
    }

    public TrainConnection getConnection(TrainStation current, TrainStation neighbour){
        for (TrainConnection connection : getConnections()) {
            if (connection.getSource().equals(current) && connection.getDestination().equals(neighbour)) {
                return connection;
            }
        }
        return null;
    }
    public double getDistance(TrainStation current, TrainStation neighbour) {
        return this.getConnection(current,neighbour).getDistance();
    }

    public List<TrainStation> findPath(TrainStation start, TrainStation end) {
        Map<TrainStation, TrainStation> came_from = new HashMap<>();
        Map<TrainStation, Integer> cost_so_far = new HashMap<>();
        PriorityQueue<TrainStation> frontier = new PriorityQueue<>(Comparator.comparingInt(cost_so_far::get));
        Set<TrainStation> visited = new HashSet<>();

        frontier.offer(start);
        cost_so_far.put(start, 0);

        while (!frontier.isEmpty()) {
            TrainStation current = frontier.poll();

            if (visited.contains(current)) {
                continue;
            }
            if (current.equals(end)) {
                break;
            }

            visited.add(current);

            for (TrainStation neighbor : getNeighbours(current)) {
                int new_cost = cost_so_far.get(current) + (int)getDistance(current, neighbor);

                if (!cost_so_far.containsKey(neighbor) || new_cost < cost_so_far.get(neighbor)) {
                    cost_so_far.put(neighbor, new_cost);
                    came_from.put(neighbor, current);
                    frontier.offer(neighbor);
                }
            }
        }

        if (!came_from.containsKey(end)) {
            return null;
        }

        List<TrainStation> path = new ArrayList<>();
        TrainStation current = end;
        while (!current.equals(start)) {
            path.add(0, current);
            current = came_from.get(current);
        }
        path.add(0, start);

        return path;
    }


    public static TrainGraph generateTrainNetwork(int stations_amount, int nearest_neighbours_amount, int extra_connections_amount) {

        TrainGraph graph = new TrainGraph();
        for (int i = 0; i < stations_amount; i++) {
            graph.addStation(new TrainStation("Stacja " + i));
        }

        for (int i = 0; i < stations_amount; i++) {
            for (int j = 1; j <= nearest_neighbours_amount; j++) {
                int neighbour_index = (i + j) % stations_amount;
                TrainStation neighbour = graph.getStations().get(neighbour_index);
                graph.addConnection(graph.getStations().get(i), neighbour, 1000 + (int)( Math.random() * 2000 ));
            }

            for(int k = 0; k < extra_connections_amount; k++) {
                int neighbour_index;
                do {
                    neighbour_index = (int)(Math.random() * stations_amount);
                } while (graph.getNeighbours(graph.getStations().get(i)).contains(graph.getStations().get(neighbour_index)));
                graph.addConnection(graph.getStations().get(i), graph.getStations().get(neighbour_index), 1000 + (int)( Math.random() * 2000 ));
            }

        }
        return graph;
    }

}
