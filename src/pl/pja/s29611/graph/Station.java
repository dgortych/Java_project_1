package pl.pja.s29611.graph;

import java.util.*;


public class Station {

    private String name;
    private List<Connection> connections;

    public Station(String name) {
        this.name = name;
        connections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }
}
