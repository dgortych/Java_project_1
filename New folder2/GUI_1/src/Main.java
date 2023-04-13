
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
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {

        int stations_amount = 100;
        int avg_connections_per_station = 10;
        double rewiring_probability = 0.3;
        TrainGraph tr = TrainGraph.generateTrainNetwork(stations_amount, avg_connections_per_station, rewiring_probability);

        Map<TrainStation, List<TrainStation>> stationConnections = new HashMap<>();

        for (TrainConnection connection : tr.getConnections()) {
            TrainStation from = connection.getSource();
            TrainStation to = connection.getDestination();
            List<TrainStation> toList = stationConnections.getOrDefault(from, new ArrayList<>());
            toList.add(to);
            stationConnections.put(from, toList);
        }
        tr.neighbors = stationConnections;

        List<TrainSet> train_sets = new ArrayList<>();

        for(int i = 0; i < 25; i++){
            Locomotive l = new Locomotive(Integer.toString(i),tr.getStations().get( ThreadLocalRandom.current().nextInt(0, stations_amount)),
                    tr.getStations().get(ThreadLocalRandom.current().nextInt(0, stations_amount)),
                    tr.getStations().get(ThreadLocalRandom.current().nextInt(0, stations_amount)));

            TrainSet ts = new TrainSet(l);
            
            for(int j = 0; j < ThreadLocalRandom.current().nextInt(5,11);j++) {
                try {
                    ts.addWagon(new PassengerWagon("Użytkownik"));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            train_sets.add(ts);

            Thread t = new Thread(new TrainRunnable(l,tr));
            Thread c = new Thread (new ChangeSpeedRunnable(ts,tr));
            t.start();
            c.start();
        }

        Thread f = new Thread (new FileWriteRunnable(train_sets,tr));
        f.start();
        
        ArrayList<Locomotive> locomotives = new ArrayList<Locomotive>();
      
        ArrayList<Wagon> wagons = new ArrayList<Wagon>();
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
                    int home_station = scanner.nextInt();
                    System.out.println("Podaj stację startową:");
                    int start_station = scanner.nextInt();
                    System.out.println("Podaj stację docelową:");
                    int final_station = scanner.nextInt();
                    Locomotive locomotive = new Locomotive(name,tr.getStations().get(home_station),
                        tr.getStations().get(start_station),
                        tr.getStations().get(final_station));
                    locomotives.add(locomotive);
                    System.out.println("Dodano lokomotywę o nazwie " + name + " do stacji " + home_station + ".");
                    break;
                case 2:
                    Wagon wagon = createWagon();
                    wagons.add(wagon);              
                    System.out.println("Dodano wagon o numerze " + wagon.getId() + " i pojemności" + wagon.getCargo() + "."); // ogarnac
                    break;
                case 3:
                    System.out.println("Podaj nazwę stacji:");
                    String station_name = scanner.next();
                    TrainStation train_station = new TrainStation(station_name);
                    tr.addStation(train_station);
                    System.out.println("Dodano stację " + station_name + ".");
                    break;
                case 4: break;
                case 5: 
                    System.out.println("Wybierz wagon:");
                    for(Wagon w: wagons)
                        System.out.println(w + "/n");


                case 6: break;
                default: break;
            }
        }while(true);
    }


    public static Wagon createWagon(){
        System.out.println("Wybierz typ wagonu:");
        System.out.println("1. Wagon pasażerski");
        System.out.println("2. Wagon pocztowy");
        System.out.println("3. Wagon bagożowo-pocztowy");
        System.out.println("4. Wagon restauracyjny");
        System.out.println("5. Wagon towarowy podstawowy");
        System.out.println("6. Wagon towarowy ciężki");
        System.out.println("7. Wagon chłodniczy");
        System.out.println("8. Wagon na materiały ciekłe");
        System.out.println("9. Wagon na materiały gazowe");
        System.out.println("10. Wagon na materiały wybuchowe");
        System.out.println("11. Wagon na materiały toksyczne");
        System.out.println("12. Wagon na ciekłe materiały toksyczne");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                System.out.println("Podaj liczbę miejsc siedzących z zakresu 10-150:");
                int seats = scanner.nextInt();
                System.out.println("Podaj liczbę pasażerów:");
                int passengers_amount = scanner.nextInt();
                System.out.println("Podaj klase wagonu z zakresu 1-4:");
                int wagon_class = scanner.nextInt();
                return new PassengerWagon("Użytkownik",seats,wagon_class,passengers_amount);
            case 2:
                System.out.println("Podaj rozmiar: ( mały, średni, duży ):");
                String size = scanner.next();
                System.out.println("Czy wagon ma być priorytetowy:");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int choice_loc2 = scanner.nextInt();
                boolean is_priority = choice_loc2 == 1;
                return new PostalWagon("Użytkownik",size,is_priority);
            case 3:
                System.out.println("Podaj materiał wykonania ( np. stal ):");
                String material = scanner.next();
                System.out.println("Podaj liczbę drzwi z zakresu:");
                int door_number = scanner.nextInt();
                return new BaggagePostalWagon("Użytkownik",door_number,material);
            case 4:
                System.out.println("Podaj liczbę obsługi z zakresu 1-15:");
                int service_amount = scanner.nextInt();
                System.out.println("Podaj ilość miejsc do jedzenia z zakresu 10-35:");
                int places_to_eat = scanner.nextInt();
                return new DiningWagon("Użytkownik",service_amount,places_to_eat);
            case 5:
                System.out.println("Podaj transportowany materiał:");
                String transported_material = scanner.next();
                System.out.println("Podaj długość wagonu:");
                double length = scanner.nextDouble();
                return new BasicTruckWagon("Użytkownik",transported_material,length);
            case 6:
                System.out.println("Podaj liczbę zabezpieczeń:");
                int protections = scanner.nextInt();
                System.out.println("Czy wagon ma mieć wzmocnione nadwozie?:");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int choice_loc6 = scanner.nextInt();
                boolean is_reinforced = choice_loc6 == 1;
                return new HeavyTruckWagon("Użytkownik",protections,is_reinforced);
            case 7:
                System.out.println("Podaj temperature chłodzenia z zakresu:");
                double temperature = scanner.nextDouble();
                System.out.println("Podaj moc chłodnicy z zakresu 1000-5000:");
                double power = scanner.nextDouble();
                return new RefrigeratedWagon("Użytkownik",temperature,power);
            case 8:
                System.out.println("Podaj gęstość transportowanej cieczy:");
                double density = scanner.nextDouble();
                System.out.println("Czy transportowana ciecz może być łatwopalna?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int choice_loc8 = scanner.nextInt();
                boolean inflammability = choice_loc8 == 1;
                return new LiquidMatterWagon("Użytkownik",density,inflammability);
            case 9:
                System.out.println("Podaj ciśnienie transportowanego gazu:");
                double pressure = scanner.nextDouble();
                System.out.println("Czy transportowany gaz jest szlachetny?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int choice_loc9 = scanner.nextInt();
                boolean is_noble = choice_loc9 == 1;
                return new GasMatterWagon("Użytkownik",pressure,is_noble);
            case 10:
                System.out.println("Podaj poziom zagrożenia z zakresu 1-5:");
                int danger_level = scanner.nextInt();
                System.out.println("Podaj moc wybuchu przewożonego materiału z zakresu 10-5000:");
                int blast_power = scanner.nextInt();
                return new ExplosivesWagon("Użytkownik",danger_level,blast_power);
            case 11:
                System.out.println("Podaj liczbę sensorów:");
                int sensors_amount = scanner.nextInt();
                System.out.println("Czy transportowany materiał jest groźny dla zdrowia?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int choice_loc11 = scanner.nextInt();
                boolean health_dangerous = choice_loc11 == 1;
                return new ToxicMatterWagon("Użytkownik",sensors_amount,health_dangerous);
            case 12:
                System.out.println("Podaj rok produkcji:");
                int year_of_production = scanner.nextInt();
                System.out.println("Czy wagon ma aktualny serwis?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int choice_loc12 = scanner.nextInt();
                boolean up_to_date_service = choice_loc12 == 1;
                return new LiquidToxicMatterWagon("Użytkownik",year_of_production,up_to_date_service);
            default:
                System.out.println("Niepoprawny wybór.");
                return null;
        }
    }
    


}   



/*
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


/*
class Main
{
    public static void main (String[] args) throws InterruptedException {
        int stations_amount = 100;
        int avg_connections_per_station = 10;
        double rewiring_probability = 0.3;
        TrainGraph tr = TrainGraph.generateTrainNetwork(stations_amount, avg_connections_per_station, rewiring_probability);

        Map<TrainStation, List<TrainStation>> stationConnections = new HashMap<>();

        for (TrainConnection connection : tr.getConnections()) {
            TrainStation from = connection.getSource();
            TrainStation to = connection.getDestination();
            List<TrainStation> toList = stationConnections.getOrDefault(from, new ArrayList<>());
            toList.add(to);
            stationConnections.put(from, toList);
        }
        tr.neighbors = stationConnections;


        List<TrainSet> train_sets = new ArrayList<>();

        for(int i = 0; i < 25; i++){
            Locomotive l = new Locomotive(Integer.toString(i),tr.getStations().get( ThreadLocalRandom.current().nextInt(0, stations_amount)),
                    tr.getStations().get(ThreadLocalRandom.current().nextInt(0, stations_amount)),
                    tr.getStations().get(ThreadLocalRandom.current().nextInt(0, stations_amount)));

            train_sets.add(new TrainSet(l));


            for(int j = 0; j < ThreadLocalRandom.current().nextInt(5,11);j++) {
                try {
                    switch (ThreadLocalRandom.current().nextInt(0, 12)) {
                        case 0 -> train_sets.get(i).addWagon(new PassengerWagon("Użytkownik"));
                        case 1 -> train_sets.get(i).addWagon(new PostalWagon("Użytkownik"));
                        case 2 -> train_sets.get(i).addWagon(new BaggagePostalWagon("Użytkownik"));
                        case 3 -> train_sets.get(i).addWagon(new DiningWagon("Użytkownik"));
                        case 4 -> train_sets.get(i).addWagon(new BasicTruckWagon("Użytkownik"));
                        case 5 -> train_sets.get(i).addWagon(new HeavyTruckWagon("Użytkownik"));
                        case 6 -> train_sets.get(i).addWagon(new RefrigeratedWagon("Użytkownik"));
                        case 7 -> train_sets.get(i).addWagon(new LiquidMatterWagon("Użytkownik"));
                        case 8 -> train_sets.get(i).addWagon(new GasMatterWagon("Użytkownik"));
                        case 9 -> train_sets.get(i).addWagon(new ExplosivesWagon("Użytkownik"));
                        case 10 -> train_sets.get(i).addWagon(new ToxicMatterWagon("Użytkownik"));
                        case 11 -> train_sets.get(i).addWagon(new LiquidToxicMatterWagon("Użytkownik"));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            Thread t = new Thread(new TrainRunnable(l,tr));
            Thread c = new Thread (new ChangeSpeedRunnable(l));
            t.start();
            c.start();
        }

        Thread f = new Thread (new FileWriteRunnable(train_sets,tr));
        f.start();
        }

    }
 */

