package pl.pja.s29611.wagon;

public abstract class Wagon {

    private static int nextId  = 1;
    protected int id;

    protected String sender;

    protected double net_weight;
    protected double gross_weight;

    protected double cargo;
    protected double max_cargo;

    public Wagon(){
        this.id = nextId++;
    }

    public int getId(){
        return this.id;
    }

    public int needElectricity(){
        return 0;
    }

    abstract public void load(double amount) throws Exception;

}

class  DiningWagon extends Wagon{


}

class  BasicTruckWagon extends Wagon{


}

class  HeavyTruckWagon extends Wagon{


}

class RefrigeratedWagon extends BasicTruckWagon{


}

class LiquidMatterWagon extends BasicTruckWagon{


}

class GasMatterWagon extends BasicTruckWagon{


}

class ExplosivesWagon extends HeavyTruckWagon{


}

class ToxicMatterWagon extends HeavyTruckWagon{


}