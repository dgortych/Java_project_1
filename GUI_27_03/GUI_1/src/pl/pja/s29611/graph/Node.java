package pl.pja.s29611.graph;

public class Node {

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
