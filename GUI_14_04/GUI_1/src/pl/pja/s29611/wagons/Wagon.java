package pl.pja.s29611.wagons;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Wagon {

    private static int nextId  = 0;
    private final int id;
    private final String sender;

    private final double net_weight;
    protected double gross_weight;

    protected double cargo;
    private final double max_cargo;

    public Wagon(String sender){
        this.id = nextId++;
        this.sender = sender;
        this.net_weight = ThreadLocalRandom.current().nextDouble(10000, 25000);
        this.cargo = ThreadLocalRandom.current().nextDouble(10000, 25000);
        this.max_cargo = this.cargo + ThreadLocalRandom.current().nextDouble(10000, 25000);
        this.gross_weight = this.net_weight + this.cargo;
    }

    public Wagon(String sender,double net_weight,double cargo,double max_cargo){
        this.id = nextId++;
        this.sender = sender;
        this.cargo = cargo;
        this.max_cargo = max_cargo;
        this.net_weight = net_weight;
        this.gross_weight = this.net_weight + this.cargo;
    }

    public int getId(){
        return this.id;
    }
    public boolean needElectricity(){
        return false;
    }
    
    abstract public void load(double amount) throws Exception;

    public String getSender() {
        return sender;
    }

    public double getNetWeight() {
        return net_weight;
    }

    public double getMaxCargo() {
        return max_cargo;
    }

    public double getGrossWeight() {
        return gross_weight;
    }

    public double getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return  "{ id = " + this.id +
                ", nadawca= " +  this.sender +
                ", waga_netto= " +  Math.round(this.net_weight * 100.0) / 100.0 +
                ", waga_brutto= " + Math.round(this.gross_weight * 100.0) / 100.0  +
                ", waga_ładunku= " + Math.round(this.cargo * 100.0) / 100.0  +
                ", maksymalna_waga_ładunku= " +  Math.round(this.max_cargo * 100.0) / 100.0;
    }
}


