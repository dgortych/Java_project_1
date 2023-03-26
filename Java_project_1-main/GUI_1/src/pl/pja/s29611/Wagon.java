
public abstract class Wagon {

    private static int nextId  = 1;
    protected String sender;
    protected double net_weight;
    protected double gross_weight;
    protected int seats;
    protected int id;

    public Wagon(){
        this.id = nextId++;
    }
    public int getId(){
        return this.id;
    }

}

class PassengerWagon extends Wagon{

}


class PostalWagon extends Wagon{
    

}

class  BaggagePostalWagon extends Wagon{
    

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

