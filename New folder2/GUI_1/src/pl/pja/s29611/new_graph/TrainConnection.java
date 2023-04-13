package pl.pja.s29611.new_graph;
import pl.pja.s29611.Locomotive;

public class TrainConnection {

    private TrainStation source;
    private TrainStation destination;
    private Locomotive locomotive;

    private boolean is_train;

    private double distance;
    private double distance_completed;

    public TrainConnection(TrainStation source, TrainStation destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.distance_completed = 0;
        this.is_train = false;
    }

    public void drive(){
        this.distance_completed += this.locomotive.getSpeed();

        if(this.distance_completed <= this.distance)
            this.locomotive.addRouteLengthMade(this.locomotive.getSpeed());
        else
            this.locomotive.addRouteLengthMade(this.locomotive.getSpeed() - this.distance_completed + this.distance);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // System.out.println("Jade w "+ this.source); // do usuniecia

        if(this.distance_completed >= this.distance) {
            this.is_train = false;
            this.locomotive = null;
        }
    }

    public void setIsTrain(){
        this.is_train = true;
    }

    public void setDistanceCompleted(double distance){
        this.distance_completed = distance;
    }

    public TrainStation getSource() {
        return this.source;
    }

    public void setSource(TrainStation source) {
        this.source = source;
    }

    public TrainStation getDestination() {
        return this.destination;
    }

    public void setDestination(TrainStation destination) {
        this.destination = destination;
    }

    public void setLocomotive(Locomotive locomotive){
        this.locomotive = locomotive;
        this.is_train = true;
    }

    public double getDistance() {return this.distance;}

    public double getDistanceCompleted() {
        return Math.min(this.distance_completed, this.distance);
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isTrain(){
        return this.is_train;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s (%f km)", this.source, this.destination, this.distance);
    }
}
