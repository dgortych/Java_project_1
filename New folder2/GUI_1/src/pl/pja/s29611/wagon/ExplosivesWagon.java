package pl.pja.s29611.wagon;

import java.util.concurrent.ThreadLocalRandom;

public class ExplosivesWagon extends HeavyTruckWagon{

    private final int danger_level;
    private final int blast_power;

    public ExplosivesWagon(String sender) {
        super(sender);
        this.danger_level = ThreadLocalRandom.current().nextInt(0, 6);
        this.blast_power = ThreadLocalRandom.current().nextInt(10, 5000);
    }
    public ExplosivesWagon(String sender,int danger_level,int blast_power) {
        super(sender);
        this.danger_level = danger_level;
        this.blast_power = blast_power;
    }
    public ExplosivesWagon(String sender,int danger_level,int blast_power,int protections,boolean reinforced_body,double net_weight,double cargo,double max_cargo) {
        super(sender,protections,reinforced_body,net_weight,cargo,max_cargo);
        this.danger_level = danger_level;
        this.blast_power = blast_power;
    }

    /*
    public void load(double amount) throws Exception{
        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit osób (" + this.getMaxCargo() + ')');
        }
        this.cargo += amount;
        this.gross_weight += amount;
    }
    */

    @Override
    public String toString() {
        return "Wagon Na Materiały Wybuchowe - " + super.toStringBasic() +
                ", stopień_zagrożenia= " + this.danger_level +
                ", siła_wybuchu= " + this.blast_power +
                " }\n";
    }

}


