package pl.pja.s29611.new_graph;


import pl.pja.s29611.Locomotive;

public class TrainConnection {

    private TrainStation source;
    private TrainStation destination;
    private int distance;

    private boolean is_train;
    private Locomotive locomotive;


    private int distance_completed;

    public TrainConnection(TrainStation source, TrainStation destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.distance_completed = 0;
        this.is_train = false;
    }

    public void drive(){

        distance_completed += locomotive.getSpeed();   // ogarna czas i predkosc

        if(distance_completed <= distance)
            locomotive.addRouteLengthMade(locomotive.getSpeed());
        else
            locomotive.addRouteLengthMade(locomotive.getSpeed() - distance_completed + distance);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Jade w "+ this.source);

        if(distance_completed >= distance) {
            this.is_train = false;
            this.locomotive = null;
        }
    }

    public void setIs_train(){
        this.is_train = true;
    }

    public void setDistance_completed(int distance){
        this.distance_completed= distance;
    }


    public TrainStation getSource() {
        return source;
    }

    public void setSource(TrainStation source) {
        this.source = source;
    }

    public TrainStation getDestination() {
        return destination;
    }

    public void setDestination(TrainStation destination) {
        this.destination = destination;
    }

    public void setLocomotive(Locomotive locomotive){
        this.locomotive = locomotive;
        this.is_train = true;
    }

    public int getDistance() {
        return distance;
    }

    public int getDistanceCompleted() {
        return distance_completed;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isTrain(){
        return this.is_train;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s (%d km)", source, destination, distance);
    }
}
