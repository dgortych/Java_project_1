
//import pl.pja.s29611.PassengerWagon;
import pl.pja.s29611.Locomotive;
import pl.pja.s29611.TrainSet;
import pl.pja.s29611.graph.Connection;
import pl.pja.s29611.graph.Station;
import pl.pja.s29611.new_graph.TrainConnection;
import pl.pja.s29611.new_graph.TrainGraph;
import pl.pja.s29611.new_graph.TrainStation;
import pl.pja.s29611.threads.ChangeSpeedRunnable;
import pl.pja.s29611.threads.FileWriteRunnable;
import pl.pja.s29611.threads.TrainRunnable;
import pl.pja.s29611.wagon.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/*
public class Main {
    public static void main(String[] args) {
        ArrayList<Locomotive> locomotives = new ArrayList<Locomotive>();
        ArrayList<Wagon> wagons = new ArrayList<Wagon>();
        ArrayList<Station> stations = new ArrayList<Station>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Stwórz nową lokomotywę");
            System.out.println("2. Stwórz nowy wagon");
            System.out.println("3. Stwórz nową stację kolejową");
            System.out.println("4. Stwórz nowe połączenie między stacjami");
            System.out.println("5. Przypisz wagon do lokomotywy");
            System.out.println("6. Załaduj osoby/ładunek do wagonu");
            System.out.println("7. Usuń obiekt");
            System.out.println("8. Wyjdź");

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Podaj nazwe lokomotywy:");
                    String name = scanner.next();
                    System.out.println("Podaj stację macierzystą:");
                    String home_station = scanner.next();
                    Locomotive locomotive = new Locomotive(name, home_station);
                    locomotives.add(locomotive);
                    System.out.println("Dodano lokomotywę o nazwie " + name + " do stacji " + home_station + ".");
                    break;
                case 2:
                    System.out.println("Podaj numer wagonu:");
                    number = scanner.nextInt();
                    System.out.println("Podaj pojemność wagonu:");
                    int capacity = scanner.nextInt();
                    Wagon wagon = new Wagon(number, capacity);
                    wagons.add(wagon);
                    System.out.println("Dodano wagon o numerze " + number + " i pojemności " + capacity + ".");
                    break;
                case 3:
                    System.out.println("Podaj nazwę stacji:");
                    String name = scanner.next();
                    Station station = new Station(name);
                    stations.add(station);
                    System.out.println("Dodano stację " + name + ".");
                    break;
                case 4:
                    System.out.println("Wybierz stację początkową:");
                    for (int i = 0; i < stations.size(); i++) {
                        System.out.println((i + 1) + ". " + stations.get(i).getName());
                    }
                    int source = scanner.nextInt() - 1;
                    System.out.println("Wybierz stację końcową:");
                    for (int i = 0; i < stations.size(); i++) {
                        System.out.println((i + 1) + ". " + stations.get(i).getName());
                    }
                    int destination = scanner.nextInt() - 1;
                    System.out.println("Podaj czas podróży:");
                    int travelTime = scanner.nextInt();
                    stations.get(source).addConnection(stations.get(destination), travelTime);
                    System.out.println("Dodano połączenie między stacjami " + stations.get(source).getName() + " i " + stations.get(destination).getName() + " o czasie podróży " + travelTime + ".");
                    break;}

            public void createWagon(){
                int choice = scanner.nextInt();
                scanner.nextLine(); // konsumujemy znak nowej linii

                switch (choice) {
                    case 1:
                        System.out.println("Podaj liczbę miejsc siedzących:");
                        int seats = scanner.nextInt();
                        PassengerWagon passengerWagon = new PassengerWagon();
                        passengerWagon.setSeats(seats);
                        break;
                    case 2:
                        PostalWagon postalWagon = new PostalWagon();
                        break;
                    case 3:
                        BaggagePostalWagon baggagePostalWagon = new BaggagePostalWagon();
                        break;
                    case 4:
                        DiningWagon diningWagon = new DiningWagon();
                        break;
                    case 5:
                        BasicTruckWagon basicTruckWagon = new BasicTruckWagon();
                        break;
                    case 6:
                        LiquidMatterWagon liquidMatterWagon = new LiquidMatterWagon();
                        break;
                    case 7:
                        GasMatterWagon gasMatterWagon = new GasMatterWagon();
                        break;
                    case 8:
                        RefrigeratedWagon refrigeratedWagon = new RefrigeratedWagon();
                        break;
                    case 9:
                        ExplosivesWagon explosivesWagon = new ExplosivesWagon();
                        break;
                    case 10:
                        ToxicMatterWagon toxicMatterWagon = new ToxicMatterWagon();
                        break;
                    default:
                        System.out.println("Niepoprawny wybór.");
                }
            }
            }

 */

class Main
{
    public static void main (String[] args){
        TrainGraph tr = TrainGraph.generateTrainNetwork(200,15,0.3);

        Map<TrainStation, List<TrainStation>> stationConnections = new HashMap<>();

        for (TrainConnection connection : tr.getConnections()) {
            TrainStation from = connection.getSource();
            TrainStation to = connection.getDestination();
            List<TrainStation> toList = stationConnections.getOrDefault(from, new ArrayList<>());
            toList.add(to);
            stationConnections.put(from, toList);
        }
        tr.neighbors = stationConnections;

        Wagon w = new PassengerWagon("xd");
        Wagon w1 = new PassengerWagon("xd",45,3,32);
        Wagon w2 = new PassengerWagon("xd",67,2,45,5000,45,45);

        System.out.println(w);
        System.out.println(w1);
        System.out.println(w2);


       try {
        w.load(45);
        w1.load(56);
        w2.load(99);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

        System.out.println(w);
        System.out.println(w1);
        System.out.println(w2);




        /* 
        Locomotive l0 = new Locomotive("0",tr.getStations().get(5),tr.getStations().get(5),tr.getStations().get(89));
        Locomotive l1 = new Locomotive("1",tr.getStations().get(78),tr.getStations().get(56),tr.getStations().get(178));
        Locomotive l2 = new Locomotive("2",tr.getStations().get(5),tr.getStations().get(5),tr.getStations().get(89));


        TrainSet s0 = new TrainSet(l0);
        TrainSet s1 = new TrainSet(l1);
        TrainSet s2 = new TrainSet(l2);

        List<TrainSet> train_sets = new ArrayList<>();
        train_sets.add(s0);
        train_sets.add(s1);
        train_sets.add(s2);


        Thread t0 = new Thread(new TrainRunnable(l0,tr));
        Thread t1 = new Thread (new TrainRunnable(l1,tr));
        Thread t2 = new Thread (new TrainRunnable(l2,tr));

        Thread c0 = new Thread (new ChangeSpeedRunnable(l0));
        Thread c1 = new Thread (new ChangeSpeedRunnable(l1));
        Thread c2 = new Thread (new ChangeSpeedRunnable(l2));

        Thread f0 = new Thread (new FileWriteRunnable(train_sets,tr));

        t0.start();
        t1.start();
        t2.start();

        c0.start();
        c1.start();
        c2.start();

        f0.start();


        }





        /* 
        // Input: List of edges in a weighted digraph (as per the above diagram)
        // tuple `(x, y, w)` represents an edge from `x` to `y` having weight `w`

        List<Edge> edges_list = new ArrayList<>();
        Random rand = new Random();

        int cities_amount = 100;
        for(int i = 0; i < cities_amount; i++){
            for(int j = 0; j < rand.nextInt(cities_amount / 8); j++){
                edges_list.add(new Edge(i,rand.nextInt(cities_amount),rand.nextInt(50)));
            }
        }


        // construct a graph from the given list of edges
        Graph graph = new Graph(edges_list);

        // print adjacency list representation of the graph
        Graph.printGraph(graph);

        for(int i = 1; i < 20; i++){
            System.out.println(graph.findPath(rand.nextInt(100), rand.nextInt(100)));
        }
        */
    }
}

