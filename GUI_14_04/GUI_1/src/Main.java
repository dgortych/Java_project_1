import pl.pja.s29611.Locomotive;
import pl.pja.s29611.TrainSet;
import pl.pja.s29611.graph.TrainGraph;
import pl.pja.s29611.graph.TrainStation;
import pl.pja.s29611.threads.ChangeSpeedRunnable;
import pl.pja.s29611.threads.FileWriteRunnable;
import pl.pja.s29611.threads.TrainRunnable;
import pl.pja.s29611.wagons.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args){

        int stations_amount = 125;
        int nearest_neighbours_amount = 8;
        int extra_connections_amount = 5;
        int train_sets_amount = 25;
        int min_wagons = 5;
        int max_wagons = 10;

        TrainGraph graph = TrainGraph.generateTrainNetwork(stations_amount, nearest_neighbours_amount, extra_connections_amount);

        List<TrainSet> train_sets = new ArrayList<>();
        ArrayList<Locomotive> locomotives = new ArrayList<>();
        ArrayList<Wagon> wagons = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < train_sets_amount; i++) {

             Locomotive l = new Locomotive(Integer.toString(i), graph.getStations().get(ThreadLocalRandom.current().nextInt(0, stations_amount)),
                    graph.getStations().get(ThreadLocalRandom.current().nextInt(0, stations_amount)),
                    graph.getStations().get(ThreadLocalRandom.current().nextInt(0, stations_amount)));
            TrainSet ts = new TrainSet(l);

            for (int j = 0; j < ThreadLocalRandom.current().nextInt(min_wagons, max_wagons); j++) {
                try {
                    switch (ThreadLocalRandom.current().nextInt(0, 12)) {
                        case 0 -> ts.addWagon(new PassengerWagon("Użytkownik"));
                        case 1 -> ts.addWagon(new PostalWagon("Użytkownik"));
                        case 2 -> ts.addWagon(new BaggagePostalWagon("Użytkownik"));
                        case 3 -> ts.addWagon(new DiningWagon("Użytkownik"));
                        case 4 -> ts.addWagon(new BasicTruckWagon("Użytkownik"));
                        case 5 -> ts.addWagon(new HeavyTruckWagon("Użytkownik"));
                        case 6 -> ts.addWagon(new RefrigeratedWagon("Użytkownik"));
                        case 7 -> ts.addWagon(new LiquidMatterWagon("Użytkownik"));
                        case 8 -> ts.addWagon(new GasMatterWagon("Użytkownik"));
                        case 9 -> ts.addWagon(new ExplosivesWagon("Użytkownik"));
                        case 10 -> ts.addWagon(new ToxicMatterWagon("Użytkownik"));
                        case 11 -> ts.addWagon(new LiquidToxicMatterWagon("Użytkownik"));
                    }
                } catch (Exception ignored) {
                }
            }

            if( ts.getElectricityWagonsAmount() > ts.getLocomotive().getMaxElectricityWagons() )
                ts.setElectricityWagonsAmount(ts.getLocomotive().getMaxElectricityWagons());

            train_sets.add(ts);

            Thread t = new Thread(new TrainRunnable(l, graph));
            Thread c = new Thread(new ChangeSpeedRunnable(l));
            t.start();
            c.start();
        }

        Thread f = new Thread(new FileWriteRunnable(train_sets, graph));
        f.start();

        boolean run = true;
        int choice;

        do {
            System.out.println("\u001B[32m"+"==============Menu============="+"\u001B[0m");
            System.out.println("1. Stwórz nową lokomotywę");
            System.out.println("2. Stwórz nowy wagons");
            System.out.println("3. Stwórz nową stację kolejową");
            System.out.println("4. Stwórz nowe połączenie między stacjami");
            System.out.println("5. Przypisz wagons do lokomotywy");
            System.out.println("6. Załaduj osoby/ładunek do wagonu");
            System.out.println("7. Nadaj trasę składowi lub lokomotywie");
            System.out.println("8. Wyświetl raport o wybranym składzie pociągu");
            System.out.println("9. Usuń wybrany obiekt");
            System.out.println("10. Przekieruj komunikaty wyjątków do osobnego pliku");
            System.out.println("11. Przywróć wyświetlanie wyjątków w konsoli");
            System.out.println("12. "+"\u001B[31m"+"Wyjdź"+"\u001B[0m");
            System.out.println("Wybierz numer opcji:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Podaj nazwę lokomotywy:");
                    String name = scanner.next();
                    System.out.println("Podaj numer stacji macierzystej:");
                    int home_station = scanner.nextInt();
                    System.out.println("Czy chcesz samodzielnie podać pozostałe parametry np. maksymalną wagę ładunku?");
                    System.out.println("1. Tak");
                    System.out.println("2. Nie");
                    int decision = scanner.nextInt();
                    if(decision == 1){
                        System.out.println("Podaj maksymalną liczbę wagonów:");
                        int wagons_amount = scanner.nextInt();
                        System.out.println("Podaj maksymalną liczbę wagonów wymagających podłączenia do sieci elektrycznej::");
                        int electric_wagons_amount = scanner.nextInt();
                        System.out.println("Podaj maksymalną wagę uciągu:");
                        int thrust = scanner.nextInt();
                        Locomotive locomotive = new Locomotive(name, graph.getStations().get(home_station),wagons_amount,electric_wagons_amount,thrust);
                        locomotives.add(locomotive);
                    }
                    else {
                        Locomotive locomotive = new Locomotive(name, graph.getStations().get(home_station));
                        locomotives.add(locomotive);
                    }
                    System.out.println("Dodano lokomotywę o nazwie " + name + " do stacji " + home_station + ".");
                }
                case 2 -> {
                    Wagon wagon = createWagon();
                    wagons.add(wagon);
                    System.out.println("Dodano " +  wagon);
                }
                case 3 -> {
                    System.out.println("Podaj nazwę stacji:");
                    String station_name = scanner.next();
                    TrainStation train_station = new TrainStation(station_name);
                    graph.addStation(train_station);
                    System.out.println("Dodano stację " + station_name + ".");
                }
                case 4 -> {
                    System.out.println("Podaj numer stacji startowej:");
                    int start = scanner.nextInt();
                    System.out.println("Podaj numer stacji docelowej:");
                    int end = scanner.nextInt();
                    System.out.println("Podaj długość trasy:");
                    double length = scanner.nextDouble();
                    graph.addConnection(graph.getStations().get(start), graph.getStations().get(end), length);
                    System.out.println("Dodano połączenie pomiędzy " + graph.getStations().get(start).toString() + " a " + graph.getStations().get(end).toString() + " o długości " + length + ".");
                }
                case 5 -> {
                    if(wagons.size() == 0){
                        System.out.println("Nie ma dostępnych wagonów");
                        break;
                    }
                    System.out.println("Wybierz wagons:");
                    for (int i = 0; i < wagons.size(); i++) {
                        System.out.println(i + 1 + ". " + wagons.get(i));
                    }
                    int wagon_choose = scanner.nextInt() - 1;
                    System.out.println("Wybierz kategorie lokomotywy:");
                    System.out.println("1.Wolne lokomotywy");
                    System.out.println("2.Wolne lokomotywy w trasie");
                    System.out.println("3.Lokomotywy w składzie z wagonami");
                    System.out.println("4.Lokomotywy w składzie z wagonami w trasie");
                    int locomotive_category_choose = scanner.nextInt();
                    switch (locomotive_category_choose) {
                        case 1 -> {
                            if( locomotives.size() == 0){
                                System.out.println("Nie ma dostępnych wolnych lokomotyw");
                                break;
                            }
                            int is_locomotive = 0;
                            for (int i = 0; i < locomotives.size(); i++) {
                                if (!locomotives.get(i).isInRoute()) {
                                    System.out.println(i + 1 + ". " + locomotives.get(i));
                                    is_locomotive = 1;
                                }
                            }
                            if(is_locomotive == 0){
                                System.out.println("Nie ma dostępnych wolnych lokomotyw");
                                break;
                            }
                            int locomotive_choose = scanner.nextInt() - 1;
                            TrainSet tr = new TrainSet(locomotives.get(locomotive_choose));
                            try {
                                tr.addWagon(wagons.get(wagon_choose));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            train_sets.add(tr);
                            System.out.println("Dodano " + wagons.get(wagon_choose) + " do " + locomotives.get(locomotive_choose));
                            locomotives.remove(locomotive_choose);
                            wagons.remove(wagon_choose);
                        }
                        case 2 -> {
                            if( locomotives.size() == 0 ){
                                System.out.println("Nie ma dostępnych wolnych lokomotyw");
                                break;
                            }
                            int is_locomotive = 0;
                            for (int i = 0; i < locomotives.size(); i++) {
                                if (locomotives.get(i).isInRoute()) {
                                    System.out.println(i + 1 + ". " + locomotives.get(i));
                                    is_locomotive = 1;
                                }
                            }
                            if(is_locomotive == 0){
                                System.out.println("Nie ma dostępnych lokomotyw na trasie");
                                break;
                            }
                            int locomotive_choose = scanner.nextInt() - 1;
                            TrainSet tr = new TrainSet(locomotives.get(locomotive_choose));
                            try {
                                tr.addWagon(wagons.get(wagon_choose));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            train_sets.add(tr);
                            System.out.println("Dodano " + wagons.get(wagon_choose) + " do " + locomotives.get(locomotive_choose));
                            locomotives.remove(locomotive_choose);
                            wagons.remove(wagon_choose);
                        }
                        case 3 -> {
                            if( train_sets.size() == 0 ){
                                System.out.println("Nie ma dostępnych składów pociagu");
                                break;
                            }
                            int is_locomotive = 0;
                            for (int i = 0; i < train_sets.size(); i++) {
                                if (train_sets.get(i).getLocomotive().isInRoute()) {
                                    System.out.println(i + 1 + ". " + train_sets.get(i).getLocomotive());
                                    is_locomotive = 1;
                                }
                            }
                            if(is_locomotive == 0){
                                System.out.println("Nie ma dostępnych lokomotyw w składzie z wagonami");
                                break;
                            }
                            int locomotive_choose = scanner.nextInt() - 1;
                            try {
                                train_sets.get(locomotive_choose).addWagon(wagons.get(wagon_choose));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            wagons.remove(wagon_choose);
                            System.out.println("Dodano " + wagons.get(wagon_choose) + " do " + train_sets.get(locomotive_choose).getLocomotive());
                        }
                        case 4 -> {
                            if( train_sets.size() == 0 ){
                                System.out.println("Nie ma dostępnych składów pociagu");
                                break;
                            }
                            for (int i = 0; i < train_sets.size(); i++) {
                                if (!train_sets.get(i).getLocomotive().isInRoute())
                                    System.out.println(i + 1 + ". " + train_sets.get(i).getLocomotive());
                            }
                            int locomotive_choose = scanner.nextInt() - 1;
                            try {
                                train_sets.get(locomotive_choose).addWagon(wagons.get(wagon_choose));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            wagons.remove(wagon_choose);
                            System.out.println("Dodano " + wagons.get(wagon_choose) + " do " + train_sets.get(locomotive_choose).getLocomotive());
                        }
                    }
                }
                case 6 -> {
                    System.out.println("Wybierz kategorie wagonu:");
                    System.out.println("1.Wolne wagony");
                    System.out.println("2.Wagony przypisane do składu pociągu");
                    int wagon_category_choose = scanner.nextInt();
                    switch (wagon_category_choose) {
                        case 1 -> {
                            if( wagons.size() == 0 ){
                                System.out.println("Nie ma dostępnych wolnych wagonów");
                                break;
                            }
                            System.out.println("Wybierz numer wagonu:");
                            for (int i = 0; i < wagons.size(); i++) {
                                System.out.println(i + 1 + ". " + wagons.get(i));
                            }
                            int wagon_choose1 = scanner.nextInt() - 1;
                            System.out.println("Podaj ilość ładunku/osób:");
                            double load = scanner.nextDouble();
                            try {
                                wagons.get(wagon_choose1).load(load);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            System.out.println("Dodano " + load + " ładunku/osób" + " do wagonu o id = " + wagons.get(wagon_choose1).getId());
                        }
                        case 2 -> {
                            if( train_sets.size() == 0 ){
                                System.out.println("Nie ma dostępnych składów pociagu");
                                break;
                            }
                            System.out.println("Wybierz skład pociagu:");
                            for (int i = 0; i < train_sets.size(); i++) {
                                System.out.println(i + 1 + ". " + train_sets.get(i));
                            }
                            int train_set_choose = scanner.nextInt() - 1;
                            System.out.println("Wybierz wagons:");
                            System.out.println(train_sets.get(train_set_choose).getWagonsListAsString());
                            int wagon_choose1 = scanner.nextInt() - 1;
                            System.out.println("Podaj ilość ładunku/osób:");
                            double load = scanner.nextDouble();
                            try {
                                train_sets.get(train_set_choose).getWagons().get(wagon_choose1).load(load);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            System.out.println("Dodano " + load + " ładunku/osób " + " do wagonu o id = " + train_sets.get(train_set_choose).getWagons().get(wagon_choose1).getId());
                        }
                    }
                }
                case 7 -> {
                    System.out.println("Wybierz lokomotywę lub skład:");
                    int j = 0;
                    for (Locomotive l : locomotives) {
                        System.out.println(j + 1 + ". " + l + "/n");
                        j++;
                    }
                    for (TrainSet ts : train_sets) {
                        System.out.println(j + 1 + ". " + ts + "/n");
                        j++;
                    }
                    int choose = scanner.nextInt() - 1;
                    System.out.println("Podaj numer stacji startowej:");
                    int start_station = scanner.nextInt();
                    System.out.println("Podaj numer stacji docelowej:");
                    int final_station = scanner.nextInt();

                    if (choose <= locomotives.size() - 1) {
                        locomotives.get(choose).setStartStation(graph.getStations().get(start_station));
                        locomotives.get(choose).setFinalStation(graph.getStations().get(final_station));
                        Thread t = new Thread(new TrainRunnable(locomotives.get(choose), graph));
                        Thread c = new Thread(new ChangeSpeedRunnable(locomotives.get(choose)));
                        t.start();
                        c.start();
                        System.out.println("Nadano trasę " + graph.getStations().get(start_station) + " --> " + graph.getStations().get(final_station) +
                                " lokomotywie " + locomotives.get(choose));
                    } else {
                        train_sets.get(choose - locomotives.size() - 1).getLocomotive().setStartStation(graph.getStations().get(start_station));
                        train_sets.get(choose - locomotives.size() - 1).getLocomotive().setFinalStation(graph.getStations().get(final_station));
                        Thread t = new Thread(new TrainRunnable(train_sets.get(choose - locomotives.size() - 1).getLocomotive(), graph));
                        Thread c = new Thread(new ChangeSpeedRunnable(train_sets.get(choose - locomotives.size() - 1).getLocomotive()));
                        t.start();
                        c.start();
                        System.out.println("Nadano trasę " + graph.getStations().get(start_station) + " --> " + graph.getStations().get(final_station) +
                                " składowi " + train_sets.get(choose));
                    }
                }
                case 8 -> {
                    if( train_sets.size() == 0 ){
                        System.out.println("Nie ma dostępnych składów pociagu");
                        break;
                    }
                    System.out.println("Wybierz skład pociagu:");
                    for (int i = 0; i < train_sets.size(); i++) {
                        System.out.println(i + 1 + ". " + train_sets.get(i) + "/n");
                    }
                    int report_choose = scanner.nextInt() - 1;
                    System.out.println(train_sets.get(report_choose).makeReport(graph));
                }
                case 9 -> {
                    System.out.println("Wybierz typ obiektu:");
                    System.out.println("1.Składy pociągów");
                    System.out.println("2.Wolne lokomotywy");
                    System.out.println("3.Wolne wagony");
                    int choose = scanner.nextInt();
                    switch(choose) {
                        case 1 -> {
                            if( train_sets.size() == 0 ){
                                System.out.println("Nie ma dostępnych składów pociagu");
                                break;
                            }
                            for (int i = 0; i < train_sets.size(); i++) {
                                System.out.println(i + 1 + ". " + train_sets.get(i) + "/n");
                            }
                            int train_set_choose = scanner.nextInt() - 1;
                            System.out.println("Wybierz działanie:");
                            System.out.println("1.Usuń cały skład");
                            System.out.println("2.Usuń wagon ze składu");
                            int action_choose = scanner.nextInt();
                            if(action_choose == 1){
                                System.out.println("Usunięto " + train_sets.get(train_set_choose));
                                train_sets.remove(train_set_choose);
                            }
                            else{
                                if( train_sets.get(train_set_choose).getWagons().size() == 0 ){
                                    System.out.println("Wybrany skład nie posiada wagonów");
                                    break;
                                }
                                System.out.println("Wybierz numer wagonu:");
                                for (int i = 0; i < train_sets.get(train_set_choose).getWagons().size(); i++) {
                                    System.out.println(i + 1 + ". " + train_sets.get(train_set_choose).getWagons().get(i));
                                }
                                int wagon_choose = scanner.nextInt() - 1;
                                train_sets.get(train_set_choose).removeWagon(wagon_choose);
                                System.out.println("Usunięto wagon");
                            }
                        }
                        case 2 -> {
                            if( locomotives.size() == 0 ){
                                System.out.println("Nie ma dostępnych wolnych lokomotyw");
                                break;
                            }
                            System.out.println("Wybierz lokomotywę do usunięcia:");
                            for (int i = 0; i < locomotives.size(); i++) {
                                System.out.println(i + 1 + ". " + locomotives.get(i) + "/n");
                            }
                            int locomotive_choose = scanner.nextInt() - 1;
                            locomotives.remove(locomotive_choose);
                            System.out.println("Usunięto lokomotywę");
                        }
                        case 3 -> {
                            if( wagons.size() == 0 ){
                                System.out.println("Nie ma dostępnych wolnych wagonów");
                                break;
                            }
                            System.out.println("Wybierz wagon do usunięcia:");
                            for (int i = 0; i < wagons.size(); i++) {
                                System.out.println(i + 1 + ". " + wagons.get(i) + "/n");
                            }
                            int wagon_choose = scanner.nextInt() - 1;
                            wagons.remove(wagon_choose);
                            System.out.println("Usunięto wagon");
                        }
                    }
                }
                case 10 ->{
                    System.out.println("Podaj nazwę pliku:");
                    String file_name = scanner.next();
                    File file = new File(file_name);
                    PrintStream fileStream = null;
                    try {
                        fileStream = new PrintStream(new FileOutputStream(file));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.setErr(fileStream);
                    System.out.println("Przekierowano komunikaty wyjątków do pliku: " + file_name);
                }
                case 11 ->{
                    System.setErr(System.err);
                    System.out.println("Przywrócono wyświetlanie komunikatów o wyjątkach");
                }
                case 12 -> run = false;
                default -> System.out.println("Wybrano niepoprawną opcję");
            }
        }while(run) ;

        System.exit(1);

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
        switch (choice) {
            case 1 -> {
                System.out.println("Podaj liczbę miejsc siedzących z zakresu 25-150:");
                int seats = scanner.nextInt();
                System.out.println("Podaj liczbę pasażerów:");
                int passengers_amount = scanner.nextInt();
                System.out.println("Podaj klase wagonu z zakresu 1-4:");
                int wagon_class = scanner.nextInt();
                return new PassengerWagon("Użytkownik", seats, wagon_class, passengers_amount);
            }
            case 2 -> {
                System.out.println("Wprowadź rozmiar: ( mały, średni, duży ):");
                String size = scanner.next();
                System.out.println("Czy wagons ma być priorytetowy:");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int decision = scanner.nextInt();
                boolean is_priority = decision == 1;
                return new PostalWagon("Użytkownik", size, is_priority);
            }
            case 3 -> {
                System.out.println("Podaj materiał wykonania ( np. stal ):");
                String material = scanner.next();
                System.out.println("Podaj liczbę drzwi:");
                int door_number = scanner.nextInt();
                return new BaggagePostalWagon("Użytkownik", door_number, material);
            }
            case 4 -> {
                System.out.println("Podaj liczbę obsługi z zakresu 5-15:");
                int service_amount = scanner.nextInt();
                System.out.println("Podaj ilość miejsc do jedzenia z zakresu 10-35:");
                int places_to_eat = scanner.nextInt();
                return new DiningWagon("Użytkownik", service_amount, places_to_eat);
            }
            case 5 -> {
                System.out.println("Podaj transportowany materiał:");
                String transported_material = scanner.next();
                System.out.println("Podaj długość wagonu:");
                double length = scanner.nextDouble();
                return new BasicTruckWagon("Użytkownik", transported_material, length);
            }
            case 6 -> {
                System.out.println("Podaj liczbę zabezpieczeń:");
                int protections = scanner.nextInt();
                System.out.println("Czy wagons ma mieć wzmocnione nadwozie?:");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int decision = scanner.nextInt();
                boolean is_reinforced = decision == 1;
                return new HeavyTruckWagon("Użytkownik", protections, is_reinforced);
            }
            case 7 -> {
                System.out.println("Podaj temperature chłodzenia:");
                double temperature = scanner.nextDouble();
                System.out.println("Podaj moc chłodnicy z zakresu 1000-5000:");
                double power = scanner.nextDouble();
                return new RefrigeratedWagon("Użytkownik", temperature, power);
            }
            case 8 -> {
                System.out.println("Podaj gęstość transportowanej cieczy:");
                double density = scanner.nextDouble();
                System.out.println("Czy transportowana ciecz może być łatwopalna?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int decision = scanner.nextInt();
                boolean inflammability = decision == 1;
                return new LiquidMatterWagon("Użytkownik", density, inflammability);
            }
            case 9 -> {
                System.out.println("Podaj ciśnienie transportowanego gazu:");
                double pressure = scanner.nextDouble();
                System.out.println("Czy transportowany gaz jest szlachetny?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int decision = scanner.nextInt();
                boolean is_noble = decision == 1;
                return new GasMatterWagon("Użytkownik", pressure, is_noble);
            }
            case 10 -> {
                System.out.println("Podaj poziom zagrożenia z zakresu 1-5:");
                int danger_level = scanner.nextInt();
                System.out.println("Podaj moc wybuchu przewożonego materiału z zakresu 10-5000:");
                int blast_power = scanner.nextInt();
                return new ExplosivesWagon("Użytkownik", danger_level, blast_power);
            }
            case 11 -> {
                System.out.println("Podaj liczbę sensorów:");
                int sensors_amount = scanner.nextInt();
                System.out.println("Czy transportowany materiał jest groźny dla zdrowia?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int decision = scanner.nextInt();
                boolean health_dangerous = decision == 1;
                return new ToxicMatterWagon("Użytkownik", sensors_amount, health_dangerous);
            }
            case 12 -> {
                System.out.println("Podaj rok produkcji:");
                int year_of_production = scanner.nextInt();
                System.out.println("Czy wagons ma aktualny serwis?");
                System.out.println("1. Tak");
                System.out.println("2. Nie");
                int decision = scanner.nextInt();
                boolean up_to_date_service = decision == 1;
                return new LiquidToxicMatterWagon("Użytkownik", year_of_production, up_to_date_service);
            }
            default -> {
                System.out.println("Niepoprawny wybór.");
                return null;
            }
        }
    }

}   

