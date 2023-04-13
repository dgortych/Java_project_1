package pl.pja.s29611;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import pl.pja.s29611.new_graph.TrainGraph;
import pl.pja.s29611.wagon.Wagon;
import java.text.DecimalFormat;

public class TrainSet{

    private final Locomotive locomotive;
    private final List<Wagon> wagons;
    private int wagons_amount;
    private int electricity_wagons_amount;
    private int cargo_weight;

    public TrainSet(Locomotive locomotive){
        this.locomotive = locomotive;
        this.wagons = new ArrayList<>();
        this.wagons_amount = 0;
        this.electricity_wagons_amount = 0;
        this.cargo_weight = 0;
    }

    public TrainSet(TrainSet ts){
        this.locomotive = new Locomotive(ts.locomotive);
        this.wagons = ts.wagons;
        this.wagons_amount = ts.wagons_amount;
        this.cargo_weight = ts.cargo_weight;
        this.electricity_wagons_amount = ts.electricity_wagons_amount;
    }


    public void addWagon(Wagon wagon) throws Exception {
        if ( this.cargo_weight + wagon.getGrossWeight()  >= this.locomotive.getMax_thrust()) {
            throw new Exception("Skład osiągnął maksymalny uciąg, nie można dodać kolejnego wagonu");
        }
        if ( this.wagons_amount + 1 >= this.locomotive.getMax_train_cars() ){
            throw new Exception("Skład osiągnął maksymalną liczbę wagonów, nie można dodać kolejnego");
        }
        if ( this.electricity_wagons_amount + 1 >= this.locomotive.getMax_electricity_tc() ){
            throw new Exception("Skład osiągnął maksymalną liczbę wagonów wymagających podłączenia do sieci elektrycznej" +
                                  ", nie można dodać kolejnego wagonu z takim wymaganiem");
        }
        this.wagons.add(wagon);
        this.cargo_weight += wagon.getCargo();
        this.wagons_amount++;
        if(wagon.needElectricity())
            this.electricity_wagons_amount++;
    }

    @Override
    public String toString() {
        return "TrainSet{" +
                "locomotive=" + locomotive.toString() +
                ", wagons=" + wagons.toString() +
                ", wagons_amount=" + wagons_amount +
                ", electricity_wagons_amount=" + electricity_wagons_amount +
                ", cargo_weight=" + cargo_weight +
                '}';
    }

    public String getWagonsListAsString() {
        StringBuilder sb = new StringBuilder();
        for (Wagon wagon : wagons) {
            sb.append(wagon.toString());
        }
        return sb.toString();
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public double getRemainingRouteLength() {
        return this.locomotive.getRemainingRouteLength();
    }

    public double getPercentageRouteMade(){
        return Math.round((this.locomotive.getRouteLengthMade() / this.locomotive.getRouteLength() * 100) * 100.0) / 100.0;
    }

    public double getPercentageNearestRouteMade(TrainGraph graph){
        return Math.round((this.locomotive.getCurrentConection(graph).getDistanceCompleted() /
                this.locomotive.getCurrentConection(graph).getDistance() * 100.0) * 100.0) / 100.0;

    }

    public String makeReport(TrainGraph graph){
        this.wagons.sort(Comparator.comparing(Wagon::getGrossWeight));
        return "Skład numer: " + this.locomotive.getId() + "\n" +
                "Trasa: " + this.locomotive.getStartStation() + " --> " + this.locomotive.getFinalStation() + "\n" +
                "Procent ukończonej całkowitej trasy: " + this.getPercentageRouteMade() +" % " + "\n" +
                "Pozostała całkowita trasa: " + Math.round(this.getRemainingRouteLength() * 100.0) / 100.0 + "\n" +
                "Obecna Stacja: " + this.locomotive.getCurrentStation() + "\n" +
                "Procent ukończonej trasy pomiędzy najbliższymi stacjami: " + this.getPercentageNearestRouteMade(graph) + " %" + "\n" +
                "Prędkość: " + Math.round(this.locomotive.getSpeed() * 100.0) / 100.0 + "\n" +
                "Liczba wagonów: " + this.wagons_amount + "\n" +
                "Informacja o wagonach:\n" + this.getWagonsListAsString() + "\n";

    }
}
