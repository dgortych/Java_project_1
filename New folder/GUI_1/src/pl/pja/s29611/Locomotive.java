package pl.pja.s29611;
import pl.pja.s29611.graph.Station;
import pl.pja.s29611.new_graph.TrainConnection;
import pl.pja.s29611.new_graph.TrainGraph;
import pl.pja.s29611.new_graph.TrainStation;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Locomotive{

    private static int nextId = 0;
    private int id;
    private String name;

    private TrainStation home_station;

    private TrainStation start_station;
    private TrainStation final_station;

    private int max_train_cars;
    private int max_thrust;
    private int max_electricity_tc;

    private double speed;
    private List<TrainStation> train_route;
    private double route_length;
    private double route_length_made;
    private int current_station_id;

    private double remaining_route;

    private int train_route_station_id;

    public Locomotive(String name,TrainStation home_station,TrainStation start_station,TrainStation final_station){
        this.name = name;
        this.home_station = home_station;
        this.speed = ThreadLocalRandom.current().nextInt(80, 180);
        this.id = nextId++;
        this.current_station_id = home_station.getId();
        this.max_electricity_tc = ThreadLocalRandom.current().nextInt(10, 12);  // ogarnac to
        this.max_train_cars = ThreadLocalRandom.current().nextInt(10, 25);
        this.max_thrust = ThreadLocalRandom.current().nextInt(500000, 3500000);
        this.start_station = start_station;
        this.final_station = final_station;
    }

    public Locomotive(String name,TrainStation home_station,int max_train_cars,int max_electricity_tc,int max_thrust,TrainStation start_station,TrainStation final_station){
        this.name = name;
        this.home_station = home_station;
        this.speed = ThreadLocalRandom.current().nextInt(2, 20);
        this.id = nextId++;
        this.current_station_id = home_station.getId();
        this.start_station = start_station;
        this.final_station = final_station;
    }
    public Locomotive(Locomotive locomotive){
        this.name = "XD";
        this.home_station =locomotive.home_station;
        this.speed = locomotive.speed;
        this.id = locomotive.id;
        this.current_station_id = locomotive.current_station_id;
        this.start_station = locomotive.start_station;
        this.final_station = locomotive.final_station;
        this.max_electricity_tc = locomotive.max_electricity_tc;
        this.max_train_cars = locomotive.max_train_cars;
        this.max_thrust = locomotive.max_thrust;
        this.remaining_route = locomotive.remaining_route;
        this.route_length = locomotive.route_length;
        this.route_length_made = locomotive.route_length_made;
        this.train_route = locomotive.train_route;
        this.train_route_station_id = locomotive.train_route_station_id;
    }

    public void rideBetweenStations(TrainGraph tr){
        TrainConnection connection = tr.getConnection(this.getCurrentStation(), this.getNextStation());
        connection.setLocomotive(this);
        while( connection.isTrain() )
            connection.drive();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.nextStation(tr);
    }

    public TrainConnection getCurrentConection(TrainGraph tr){
        return tr.getConnection(this.getCurrentStation(), this.getNextStation());
    }

    public void nextStation(TrainGraph tr){
        this.getCurrentConection(tr).setDistanceCompleted(0);
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
            throw new RailroadHazard("Skład " + this.id + " osiągnął prędkość ponad 200 km/h.");
    }

    public double getSpeed(){
        return this.speed;
    }

    public TrainStation getCurrentStation(){
        return this.train_route.get(this.train_route_station_id);
    }

    public TrainStation getNextStation(){
        return this.train_route.get(this.train_route_station_id+1);
    }

    /*
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Speed: %f, Station: %d", id, name, speed,getCurrentStation().getId());
    }
    */

    @Override
    public String toString() {
        return "Locomotive{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", home_station=" + home_station +
                ", start_station=" + start_station +
                ", final_station=" + final_station +
                ", speed=" + speed +
                ", route_length=" + route_length +
                ", route_length_made=" + route_length_made +
                ", route_length_zostalo=" + remaining_route +   // do usuniecia
                ", current_station_id=" + current_station_id +
                '}';
    }

    public int getMax_train_cars() {
        return max_train_cars;
    }

    public int getMax_thrust() {
        return max_thrust;
    }

    public int getMax_electricity_tc() {
        return max_electricity_tc;
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

    public double getRemainingRouteLength(){
        this.remaining_route = this.route_length - this.route_length_made;
        return this.route_length - this.route_length_made;
    }
    public int getId(){
        return this.id;
    }

}


class RailroadHazard extends Exception{
    public RailroadHazard(String message) {
        super(message);
    }

}
