package pl.pja.s29611.graph;


import pl.pja.s29611.Locomotive;

public class Connection {

    private Station destination;
    private double distance;

    private boolean is_train;
    private Locomotive locomotive;

    private double distance_completed;

    public Connection(Station destination, double distance) {
        this.destination = destination;
        this.distance = distance;
        this.distance_completed = 0;
        this.is_train = false;
    }

    private void updateDistancePercent(double excess){
        distance_completed += excess;
        distance_completed += locomotive.getSpeed();   // ogarna czas i predkosc

        if(distance_completed >= distance) {
            double current_excess = distance_completed - distance;
            //locomotive.nextStation(current_excess,locomotive);
            this.is_train = false;
        }
    }

    public Station getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

}
/*
    public List<TrainStation> findShortestPath(TrainStation source, TrainStation destination) {
        Map<TrainStation, Double> distances = new HashMap<>();
        Map<TrainStation, TrainStation> previousStations = new HashMap<>();
        PriorityQueue<TrainStation> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        List<TrainStation> path = new ArrayList<>();

        for (TrainStation station : stations) {
            if (station == source) {
                distances.put(station, 0.0);
            } else {
                distances.put(station, Double.MAX_VALUE);
            }
            queue.offer(station);
        }

        while (!queue.isEmpty()) {
            TrainStation currentStation = queue.poll();
            if (currentStation == destination) {
                while (previousStations.containsKey(currentStation)) {
                    path.add(currentStation);
                    currentStation = previousStations.get(currentStation);
                }
                path.add(source);
                Collections.reverse(path);
                return path;
            }

            for (Connection connection : currentStation.getConnections()) {
                Station nextStation = connection.getDestination();
                double distance = distances.get(currentStation) + connection.getDistance();
                if (distance < distances.get(nextStation)) {
                    distances.put(nextStation, distance);
                    previousStations.put(nextStation, currentStation);
                    queue.remove(nextStation);
                    queue.offer(nextStation);
                }
            }
        }
    }

    public static List<TrainConnection> findPath(TrainStation source, TrainStation destination, TrainGraph graph) {
        Map<TrainStation, Integer> distances = new HashMap<>();
        Map<TrainStation, TrainConnection> previousConnections = new HashMap<>();
        Set<TrainStation> visited = new HashSet<>();

        PriorityQueue<TrainStation> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            TrainStation currentStation = queue.poll();

            if (currentStation.equals(destination)) {
                break;
            }

            visited.add(currentStation);

            for (TrainConnection connection : graph.getNeighbours(currentStation)) {
                TrainStation neighbourStation = connection.getDestination();
                int newDistance = distances.get(currentStation) + connection.getDistance();

                if (!visited.contains(neighbourStation) && newDistance < distances.getOrDefault(neighbourStation, Integer.MAX_VALUE)) {
                    distances.put(neighbourStation, newDistance);
                    previousConnections.put(neighbourStation, connection);
                    queue.add(neighbourStation);
                }
            }
        }

        if (!previousConnections.containsKey(destination)) {
            return null;
        }

        List<TrainConnection> path = new ArrayList<>();
        TrainStation currentStation = destination;

        while (!currentStation.equals(source)) {
            TrainConnection previousConnection = previousConnections.get(currentStation);
            path.add(previousConnection);
            currentStation = previousConnection.getSource();
        }

        Collections.reverse(path);
        return path;
    }


public static List<TrainConnection> findPath(TrainStation source, TrainStation destination, TrainGraph graph) {
    Map<TrainStation, Integer> distances = new HashMap<>();
    Map<TrainStation, TrainConnection> previousConnections = new HashMap<>();
    Set<TrainStation> visited = new HashSet<>();

    PriorityQueue<TrainStation> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

    distances.put(source, 0);
    queue.add(source);

    while (!queue.isEmpty()) {
        TrainStation currentStation = queue.poll();

        if (currentStation.equals(destination)) {
            break;
        }

        visited.add(currentStation);

        for (TrainStation neighbourStation : graph.getNeighbours(currentStation)) {
            TrainConnection connection = graph.getConnection(currentStation, neighbourStation);
            int newDistance = distances.get(currentStation) + connection.getDistance();

            if (!visited.contains(neighbourStation) && newDistance < distances.getOrDefault(neighbourStation, Integer.MAX_VALUE)) {
                distances.put(neighbourStation, newDistance);
                previousConnections.put(neighbourStation, connection);
                queue.add(neighbourStation);
            }
        }
    }

    if (!previousConnections.containsKey(destination)) {
        return null;
    }

    List<TrainConnection> path = new ArrayList<>();
    TrainStation currentStation = destination;

    while (!currentStation.equals(source)) {
        TrainConnection previousConnection = previousConnections.get(currentStation);
        path.add(previousConnection);
        currentStation = previousConnection.getSource();
    }

    Collections.reverse(path);
    return path;
}



    public  List<TrainStation> findPath(TrainStation start, TrainStation end) {
        List<TrainStation> path = new ArrayList<>();
        Set<TrainStation> visited = new HashSet<>();
        dfs(start, end, visited, path);
        return path;
    }

    private void dfs(TrainStation current, TrainStation end, Set<TrainStation> visited, List<TrainStation> path) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            // We have reached the end station, so stop searching
            return;
        }

        for (TrainStation neighbor : neighbors.get(current)) {
            if (!visited.contains(neighbor)) {
                // Recursively search the neighbor
                dfs(neighbor, end, visited, path);

                // If the end station was found, stop searching
                if (path.get(path.size() - 1).equals(end)) {
                    return;
                }

                // If the neighbor did not lead to the end station, backtrack
                path.remove(path.size() - 1);
            }
        }
    }



public List<TrainStation> findShortestPath(TrainStation start, TrainStation end) {
    Map<TrainStation, Integer> distances = new HashMap<>();
    Map<TrainStation, TrainStation> previous = new HashMap<>();
    PriorityQueue<TrainStation> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
    Set<TrainStation> visited = new HashSet<>();

    // Initialize distances to infinity for all stations except the start station
    for (TrainStation station : stations) {
        if (station.equals(start)) {
            distances.put(station, 0);
        } else {
            distances.put(station, Integer.MAX_VALUE);
        }
    }

    pq.offer(start);

    while (!pq.isEmpty()) {
        TrainStation current = pq.poll();

        if (current.equals(end)) {
            break;
        }

        if (visited.contains(current)) {
            continue;
        }

        visited.add(current);

        for (TrainStation neighbor : neighbors.get(current)) {
            int distanceToNeighbor = distances.get(current) + getConnectionDistance(current, neighbor);

            if (distanceToNeighbor < distances.get(neighbor)) {
                distances.put(neighbor, distanceToNeighbor);
                previous.put(neighbor, current);
                pq.offer(neighbor);
            }
        }
    }

    // Reconstruct the path from start to end
    List<TrainStation> path = new ArrayList<>();
    TrainStation current = end;

    while (previous.containsKey(current)) {
        path.add(0, current);
        current = previous.get(current);
    }

    if (path.isEmpty()) {
        // There is no path from start to end
        return null;
    } else {
        path.add(0, start);
        return path;
    }
}

    private int getConnectionDistance(TrainStation source, TrainStation destination) {
        for (TrainConnection connection : connections) {
            if (connection.getSource().equals(source) && connection.getDestination().equals(destination)) {
                return connection.getDistance();
            }
        }

        throw new IllegalArgumentException("There is no connection between " + source + " and " + destination);
    }

*/