package pl.pja.s29611.old_graph;

import pl.pja.s29611.graph.Station;

public class Node {

    int value, weight;

    Station station;

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
