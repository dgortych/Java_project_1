
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Locomotive {

    private static int nextId = 1;
    private int id;
    private String name;

    private String home_station;
    private String start_station;
    private String final_station;

    private double speed;

    private int max_train_cars;
    private int max_thrust;
    private int max_electricity_tc;


    public Locomotive(String name,String home_station,String start_station,String final_station){
        this.name = name;
        this.home_station = home_station;
        this.start_station = start_station;
        this.final_station = final_station;
        this.speed = ThreadLocalRandom.current().nextInt(50, 150);
        this.id = nextId++;
    }

    public void changeSpeed(){
        this.speed = Math.random() < 0.5 ? speed * 1.03 : speed * 0.97;
    }





}
