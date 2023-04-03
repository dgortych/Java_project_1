package pl.pja.s29611.wagon;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Wagon {

    private static int nextId  = 1;
    private final int id;

    private final String sender;

    private final double net_weight;
    protected double gross_weight;

    protected double cargo;
    private final double max_cargo;

    public Wagon(String sender){
        this.id = nextId++;
        this.sender = sender;
        this.net_weight = ThreadLocalRandom.current().nextInt(10000, 25000);
        this.cargo = ThreadLocalRandom.current().nextInt(10000, 25000);
        this.max_cargo = this.cargo + ThreadLocalRandom.current().nextInt(10000, 25000);
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
        return "Wagon{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", net_weight=" + net_weight +
                ", gross_weight=" + gross_weight +
                ", cargo=" + cargo +
                ", max_cargo=" + max_cargo +
                '}';
    }
}


