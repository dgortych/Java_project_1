package pl.pja.s29611.wagons;
import java.util.concurrent.ThreadLocalRandom;

public class HeavyTruckWagon extends Wagon{

    private final boolean reinforced_body;
    private final int protections_amount;

    public HeavyTruckWagon(String sender) {
        super(sender);
        this.reinforced_body = Math.random() < 0.5;
        this.protections_amount = ThreadLocalRandom.current().nextInt(1, 10);
    }

    public HeavyTruckWagon(String sender,int protections,boolean reinforced_body) {
        super(sender);
        this.protections_amount = protections;
        this.reinforced_body = reinforced_body;
    }

    public HeavyTruckWagon(String sender,int protections,boolean reinforced_body,double net_weight,double cargo,double max_cargo) {
        super(sender,net_weight,cargo,max_cargo);
        this.protections_amount = protections;
        this.reinforced_body = reinforced_body;
    }

    public void load(double amount) throws Exception{
        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit ładowności wynoszący: " + this.getMaxCargo());
        }
        this.cargo += amount;
        this.gross_weight += amount;
    }

    @Override
    public String toString() {
        return "Wagon Towarowy Cięzki - " + super.toString() +
                ", wzmocnione_nadwozie= " + this.reinforced_body +
                ", liczba_zabezpieczeń= " + this.protections_amount+
                " }";
    }

    public String toStringBasic() {
        return super.toString() + 
                ", wzmocnione_nadwozie= " + this.reinforced_body +
                ", liczba_zabezpieczeń= " + this.protections_amount;
                
    }
}


