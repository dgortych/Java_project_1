package pl.pja.s29611.graph;

import pl.pja.s29611.Locomotive;

public class Edge {

    private int source;
    private int destination;
    private int weight;

    private int is_train;

    private Locomotive locomotive;

    private double distance_percent;


    public Edge(int src, int dest, int weight)
    {
        this.source = src;
        this.destination = dest;
        this.weight = weight;
        this.distance_percent = 0;
    }

    private double updateDistancePercent(double excess){
        distance_percent += excess;
        distance_percent += locomotive.getSpeed();   // ogarna czas i predkosc


        if(distance_percent >= 100.00)
            double current_excess =
            locomotive.nextStation();

            return 0;

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
