import pl.pja.s29611.Locomotive;
import pl.pja.s29611.TrainSet;
import pl.pja.s29611.graph.TrainGraph;
import pl.pja.s29611.graph.TrainStation;
import pl.pja.s29611.threads.ChangeSpeedRunnable;
import pl.pja.s29611.threads.FileWriteRunnable;
import pl.pja.s29611.threads.TrainRunnable;
import pl.pja.s29611.wagons.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Presentation {
    public static void main(String[] args){

        int stations_amount = 25;  // liczba stacji
        int nearest_neighbours_amount = 2; // liczba połączeń każdej stacji z najbliższymi stacjami
        int extra_connections_amount = 1; // liczba dodatkowych połączeń z innymi stacjami

        // Utworzenie grafu połączeń kolejowych z własnymi parametrami
        TrainGraph graph = TrainGraph.generateTrainNetwork(stations_amount, nearest_neighbours_amount, extra_connections_amount);

        // Dodanie własnych stacji
        graph.addStation(new TrainStation("Własna_Stacja_1"));
        graph.addStation(new TrainStation("Własna_Stacja_2"));
        graph.addStation(new TrainStation("Własna_Stacja_3"));
        System.out.println("Wyświetlenie jednej z dodanych stacji:");
        System.out.println(graph.getStations().get(26) + "\n");

        // Dodanie własnych połączeń między stacjami
        graph.addConnection(graph.getStations().get(25), graph.getStations().get(26), 2500);
        graph.addConnection(graph.getStations().get(26), graph.getStations().get(27), 2432);
        graph.addConnection(graph.getStations().get(27), graph.getStations().get(0), 1345);
        graph.addConnection(graph.getStations().get(4), graph.getStations().get(17), 5567);
        System.out.println("Wyświetlenie jednego z dodanych połączeń:");
        System.out.println(graph.getConnection(graph.getStations().get(25),graph.getStations().get(26))+ "\n");

        //Znalezienie najkrótszej drogi między stacjami
        List<TrainStation> path = graph.findPath(graph.getStations().get(1), graph.getStations().get(23));
        System.out.println("Wyświetlenie najkrótszej drogi między stacjami:");
        System.out.println(path + "\n");

        //Utworzenie lokomotyw
        ArrayList<Locomotive> locomotives = new ArrayList<>();
        locomotives.add(new Locomotive("Nowa_lokomotywa_losowa", graph.getStations().get(5)));
        locomotives.add(new Locomotive("Nowa_lokomotywa_losowa2", graph.getStations().get(5)));
        locomotives.add(new Locomotive("Nowa_lokomotywa_wlasna", graph.getStations().get(7),34,15,1500000));
        locomotives.add(new Locomotive("Nowa_lokomotywa_wlasna2", graph.getStations().get(13),22,7,2000000));
        System.out.println("Wyświetlenie losowo utworzonej lokomotywy:");
        System.out.println(locomotives.get(1) + "\n");
        System.out.println("Wyświetlenie własnej utworzonej lokomotywy:");
        System.out.println(locomotives.get(3) + "\n");

        //Utworzenie wagonów
        ArrayList<Wagon> wagons = new ArrayList<>();
        wagons.add(new PassengerWagon("Widz_prezentacji", 130, 3, 70)); // wagon pasażerski
        wagons.add(new PostalWagon("Użytkownik", "Średni", true)); // wagon pocztowy
        wagons.add(new BaggagePostalWagon("Użytkownik", 8, "Stal")); // wagon bagażowo-pocztowy
        wagons.add(new DiningWagon("Użytkownik", 8, 35)); // wagon restauracyjny
        wagons.add(new BasicTruckWagon("Użytkownik", "Złoto", 20.4)); // wagon towarowy podstawowy
        wagons.add(new HeavyTruckWagon("Użytkownik", 12, false)); // wagon towarowy ciężki
        wagons.add(new RefrigeratedWagon("Użytkownik", -25.3, 2500)); // wagon chłodniczy
        wagons.add(new LiquidMatterWagon("Użytkownik", 3500, true)); // wagon na materiały ciekłe
        wagons.add(new GasMatterWagon("Użytkownik", 2000, false)); // wagon na materiały gazowe
        wagons.add(new ExplosivesWagon("Użytkownik", 4, 1459)); // wagon na materiały wybuchowe
        wagons.add(new ToxicMatterWagon("Użytkownik", 8, true)); // wagon na materiały toksyczne
        wagons.add(new LiquidToxicMatterWagon("Użytkownik", 1987, true)); // wagon na ciekłe materiały toksyczne
        //Utworzenie wagonów z większą ilośćią własnych parametrów
        wagons.add(new LiquidToxicMatterWagon("Użytkownik", 1987, true,10,false,22000,12000,30000));
        wagons.add(new BasicTruckWagon("Użytkownik", "Złoto", 20.4,18000,15000,23000));
        System.out.println("Wyświetlenie utworzonych wagonów:");
        for (int i = 0; i < wagons.size(); i++) {
            System.out.println(i + 1 + ". " + wagons.get(i));
        }

        //Załadowanie ładunku/osób do wagonów
        try {
            wagons.get(0).load(25);    //wagon pasażerski
            wagons.get(1).load(2500);  //wagon pocztowy
            wagons.get(2).load(2500);  //wagon bagażowo-pocztowy
            wagons.get(3).load(3);    //wagon restauracyjny
            wagons.get(4).load(2500);  //wagon towarowy podstawowy
            wagons.get(5).load(2500);  //wagon towarowy ciężki
            wagons.get(6).load(2500);  //wagon chłodniczy
            wagons.get(7).load(2500);  //wagon na materiały ciekłe
            wagons.get(8).load(2500);  //wagon na materiały gazowe
            wagons.get(9).load(2500);  //wagon na materiały wybuchowe
            wagons.get(10).load(2500); //wagon na materiały toksyczne
            wagons.get(11).load(2500); //wagon na ciekłe materiały toksyczne
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nWyświetlenie wagonów po załadunku:");
        for (int i = 0; i < wagons.size(); i++) {
            System.out.println(i + 1 + ". " + wagons.get(i));
        }

        System.out.println("\nWyświetlenie informacji o przeładowaniu wagonu pasażerskiego:");
        try {
            wagons.get(0).load(100);    //wagon pasażerski
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Wyświetlenie informacji o przeładowaniu wagonu towarowego podstawowego:");
        try {
            wagons.get(4).load(1000000);    //wagon towarowy podstawowy
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Przyłączenie wagonów do lokomotyw
        List<TrainSet> train_sets = new ArrayList<>();
        train_sets.add(new TrainSet( locomotives.get(0)));
        train_sets.add(new TrainSet( locomotives.get(1)));
        train_sets.add(new TrainSet( locomotives.get(2)));
        train_sets.add(new TrainSet( locomotives.get(3)));

        try {
            train_sets.get(0).addWagon(wagons.get(0));
            train_sets.get(0).addWagon(wagons.get(1));
            train_sets.get(0).addWagon(wagons.get(2));
            train_sets.get(1).addWagon(wagons.get(3));
            train_sets.get(1).addWagon(wagons.get(4));
            train_sets.get(2).addWagon(wagons.get(5));
            train_sets.get(3).addWagon(wagons.get(6));
            train_sets.get(3).addWagon(wagons.get(7));
            train_sets.get(3).addWagon(wagons.get(8));
            train_sets.get(3).addWagon(wagons.get(9));
            train_sets.get(3).addWagon(wagons.get(10));
            train_sets.get(2).addWagon(wagons.get(11));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Wyświetlenie jednego z utworzonych składów");
        System.out.println(train_sets.get(3)+ "\n");


        //Nadanie trasy składom
        for (TrainSet trainSet : train_sets) {
            trainSet.getLocomotive().setStartStation(graph.getStations().get(1));
            trainSet.getLocomotive().setFinalStation(graph.getStations().get(23));
            Thread t = new Thread(new TrainRunnable(trainSet.getLocomotive(), graph));
            Thread c = new Thread(new ChangeSpeedRunnable(trainSet.getLocomotive()));
            t.start();
            c.start();
        }

        Thread f = new Thread(new FileWriteRunnable(train_sets, graph));
        f.start();
    }

}
