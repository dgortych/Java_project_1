package pl.pja.s29611;

import java.util.ArrayList;
import java.util.List;

import pl.pja.s29611.new_graph.TrainGraph;
import pl.pja.s29611.wagon.Wagon;

public class TrainSet {

    private Locomotive locomotive;
    private List<Wagon> wagons;
    private int wagons_amount;
    private int electricity_wagons_amount;
    private int cargo_weight;

    public TrainSet(Locomotive locomotive){
        this.locomotive = locomotive;
        this.wagons = new ArrayList<>();
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

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public double getRemainingRouteLength() {
        return this.locomotive.getRemainingRouteLength();
    }

    public int getPercentageRouteMade(){
        return (int)(this.locomotive.getRouteLengthMade() / this.locomotive.getRouteLength() * 100);
    }

    public int getPercentageNearestRouteMade(TrainGraph graph){
        return (int)(this.locomotive.getCurrentConection(graph).getDistanceCompleted() / this.locomotive.getCurrentConection(graph).getDistance() * 100);
    }

    public String makeReport(TrainGraph graph){
        return "Skład numer: " + this.locomotive.getId() + "\n" +
                "Trasa: " + this.locomotive.getStartStation() + " --> " + this.locomotive.getFinalStation() + "\n" +
                "Procent ukończonej całkowiteh trasy: " + this.getPercentageRouteMade() +" % " + "\n" +
                "Obecna Stacja: " + this.locomotive.getCurrentStation() + "\n" +
                "Procent ukończonej trasy pomiędzy najbliższymi stacjami: " + this.getPercentageNearestRouteMade(graph) + " %" + "\n" +
                "Prędkość: " + this.locomotive.getSpeed() + "\n" +
                "Liczba wagonów :" + this.wagons_amount + "\n" +
                "Informacja o wagonach: " + this.wagons.toString() + "\n";

    }

}
