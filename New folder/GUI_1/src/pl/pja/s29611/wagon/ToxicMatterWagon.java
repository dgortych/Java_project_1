package pl.pja.s29611.wagon;

import java.util.concurrent.ThreadLocalRandom;

public class ToxicMatterWagon extends HeavyTruckWagon{

    private final int sensors_amount;
    private final boolean health_dangerous;

    public ToxicMatterWagon(String sender) {
        super(sender);
        this.sensors_amount = ThreadLocalRandom.current().nextInt(0, 15);
        this.health_dangerous = Math.random() < 0.5;;
    }
    public ToxicMatterWagon(String sender,int sensors_amount,boolean health_dangerous) {
        super(sender);
        this.sensors_amount = sensors_amount;
        this.health_dangerous = health_dangerous;
    }
    public ToxicMatterWagon(String sender,int sensors_amount,boolean health_dangerous,int protections,boolean reinforced_body,double net_weight,double cargo,double max_cargo) {
        super(sender,protections,reinforced_body,net_weight,cargo,max_cargo);
        this.sensors_amount = sensors_amount;
        this.health_dangerous = health_dangerous;
    }

    /*
    public void load(double amount) throws Exception{

        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit osób (" + this.getMaxCargo() + ')');
        }
        this.cargo += amount;
        this.gross_weight += amount * 60;
    }
*/

    @Override
    public String toString() {
        return "Wagon Na Materiały Toksyczne - " + super.toStringBasic() +
                ", liczba_czujników= " + this.sensors_amount +
                ", groźne_dla_zdrowia= " + this.health_dangerous +
                " }\n";
    }

}
