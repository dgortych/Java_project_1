package pl.pja.s29611;
import pl.pja.s29611.graph.TrainConnection;
import pl.pja.s29611.graph.TrainGraph;
import pl.pja.s29611.graph.TrainStation;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Locomotive{

    private static int nextId = 0;
    private final int id;
    private final String name;

    private final TrainStation home_station;
    private TrainStation start_station;
    private TrainStation final_station;

    private final int max_wagons;
    private final int max_thrust;
    private final int max_electricity_wagons;

    private double speed;
    private List<TrainStation> train_route;
    private double route_length;
    private double route_length_made;
    private int train_route_station_id;

    public Locomotive(String name,TrainStation home_station){
        this.name = name;
        this.home_station = home_station;
        this.speed = 0.0;
        this.id = nextId++;
        this.max_wagons = ThreadLocalRandom.current().nextInt(10, 25);
        this.max_electricity_wagons =  this.max_wagons / 3 + ThreadLocalRandom.current().nextInt(0, 3);
        this.max_thrust = ThreadLocalRandom.current().nextInt(500000, 3500000);
    }

    public Locomotive(String name,TrainStation home_station,TrainStation start_station,TrainStation final_station){
        this.name = name;
        this.home_station = home_station;
        this.speed = 0.0;
        this.id = nextId++;
        this.max_wagons = ThreadLocalRandom.current().nextInt(10, 25);
        this.max_electricity_wagons =  this.max_wagons / 3 + ThreadLocalRandom.current().nextInt(0, 3);
        this.max_thrust = ThreadLocalRandom.current().nextInt(500000, 3500000);
        this.start_station = start_station;
        this.final_station = final_station;
    }

    public Locomotive(String name,TrainStation home_station,int max_wagons,int max_electricity_wagons,int max_thrust){
        this.name = name;
        this.home_station = home_station;
        this.speed = 0.0;
        this.id = nextId++;
        this.max_wagons = max_wagons;
        this.max_thrust = max_thrust;
        this.max_electricity_wagons = max_electricity_wagons;
    }

    public void rideBetweenStations(TrainGraph graph){
        TrainConnection connection = graph.getConnection(this.getCurrentStation(), this.getNextStation());
        connection.setLocomotive(this);
        while( connection.isTrain() )
            connection.drive();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.nextStation(graph);
    }

    public TrainConnection getCurrentConection(TrainGraph graph){
        return graph.getConnection(this.getCurrentStation(), this.getNextStation());
    }

    public void nextStation(TrainGraph graph){
        this.getCurrentConection(graph).setDistanceCompleted(0);
        this.train_route_station_id++;

    }

    public void assignRoute(List<TrainStation> route,double route_length){
        this.train_route = route;
        this.route_length = route_length;
        this.train_route_station_id = 0;
        this.route_length_made = 0;
    }

    public void changeSpeed() throws Exception {
        this.speed = Math.random() < 0.5 ? speed * 1.03 : speed * 0.97;
        if(this.speed > 200.00)
            throw new RailroadHazard("Skład " + this.id + " osiągnął prędkość ponad 200 km/h!!!\n" +
                    "Informacje:\n"+
                    "Trasa: " + this.start_station + " --> " + this.final_station + "\n" +
                    "Długość trasy: " + Math.round(this.getRouteLength() * 100.0) / 100.0 + " km\n" +
                    "Prędkość: " + Math.round(this.speed * 100.0) / 100.0 + " km/h\n"
            );
    }

    public double getSpeed(){
        return this.speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public TrainStation getCurrentStation(){
        return this.train_route.get(this.train_route_station_id);
    }

    public TrainStation getNextStation(){
        return this.train_route.get(this.train_route_station_id+1);
    }

    @Override
    public String toString() {
        return "Lokomotywa: " +
                "id=" + id +
                ", nazwa='" + name + '\'' +
                ", stacja_macierzysta=" + home_station +
                ", stacja_startowa=" + start_station +
                ", stacja_docelowa=" + final_station +
                ", prędkość=" + speed +
                ", długość_trasy=" + route_length +
                ", długość_pokonanej_trasy=" + route_length_made +
                '}';
    }

    public void setTrainRouteStationId(int train_route_station_id){
        this.train_route_station_id = train_route_station_id;
    }

    public int getMaxWagons() {
        return max_wagons;
    }

    public int getMaxThrust() {
        return max_thrust;
    }

    public int getMaxElectricityWagons() {
        return max_electricity_wagons;
    }

    public void setStartStation(TrainStation start_station) {
        this.start_station = start_station;
    }
    public TrainStation getStartStation() {
        return start_station;
    }

    public void setFinalStation(TrainStation final_station) {
        this.final_station = final_station;
    }
    public TrainStation getFinalStation() {
        return this.final_station;
    }

    public double getRouteLength() {
        return route_length;
    }
    public void addRouteLengthMade(double distance) {
        this.route_length_made += distance;
        if(this.route_length_made >= this.route_length)
            this.route_length_made = this.route_length;
    }

    public double getRouteLengthMade() {
        return route_length_made;
    }

    public boolean isInRoute() {
        return this.train_route != null;
    }

    public int getId(){
        return this.id;
    }

    public double getRemainingRouteLength() {
        return this.route_length - this.route_length_made;
    }
}

class RailroadHazard extends Exception{

    public RailroadHazard(String message) {
        super(message);
    }

}