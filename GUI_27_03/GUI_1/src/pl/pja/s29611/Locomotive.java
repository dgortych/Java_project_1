package pl.pja.s29611;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Locomotive {

    private static int nextId = 1;
    private int id;
    private String name;

    private Station home_station;
    private Station start_station;
    private Station final_station;

    private int max_train_cars;
    private int max_thrust;
    private int max_electricity_tc;


    private double speed;
    private List<Station> train_route;


    public Locomotive(String name,String home_station){
        this.name = name;
        this.speed = ThreadLocalRandom.current().nextInt(50, 150);
        this.id = nextId++;
    }

    public void changeSpeed(){
        this.speed = Math.random() < 0.5 ? speed * 1.03 : speed * 0.97;
    }

    public double getSpeed(){
        return this.speed;
    }






}
