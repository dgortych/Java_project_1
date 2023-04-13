package pl.pja.s29611.old_graph;

import pl.pja.s29611.Locomotive;

public class Edge {

    private int source;
    private int destination;
    private int weight;

    private boolean is_train;
    private Locomotive locomotive;

    private double distance_completed;

    public Edge(int src, int dest, int weight)
    {
        this.source = src;
        this.destination = dest;
        this.weight = weight;
        this.distance_completed = 0;
        this.is_train = false;
    }

    private void updateDistancePercent(double excess){
        distance_completed += excess;
        distance_completed += locomotive.getSpeed();   // ogarna czas i predkosc

        if(distance_completed >= weight) {
            double current_excess = distance_completed - weight;
            //locomotive.nextStation(current_excess,locomotive);
            this.is_train = false;
        }
    }

    public void assignLocomotive(Locomotive locomotive){
        this.locomotive = locomotive;
    }

    public int getSource(){
        return this.source;
    }

    public int getDestination(){
        return this.destination;
    }

    public int getWeight(){
        return this.weight;
    }



}
