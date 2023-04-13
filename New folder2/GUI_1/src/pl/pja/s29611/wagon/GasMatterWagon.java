package pl.pja.s29611.wagon;

import java.util.concurrent.ThreadLocalRandom;

public class GasMatterWagon extends BasicTruckWagon{

    private final double pressure;
    private final boolean is_noble;

    public GasMatterWagon(String sender) {
        super(sender,"Gaz", ThreadLocalRandom.current().nextDouble(15, 30));
        this.pressure = ThreadLocalRandom.current().nextDouble(1000, 5000);
        this.is_noble = Math.random() < 0.5;
    }
    public GasMatterWagon(String sender,double pressure,boolean is_noble) {
        super(sender,"Gaz",ThreadLocalRandom.current().nextDouble(15, 30));
        this.pressure = pressure;
        this.is_noble = is_noble;
    }
    public GasMatterWagon(String sender,double pressure,boolean is_noble,int length,String transported_material,double net_weight,double cargo,double max_cargo) {
        super(sender,transported_material,length,net_weight,cargo,max_cargo);
        this.pressure = pressure;
        this.is_noble = is_noble;
    }

    public void load(double amount) throws Exception{

        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit osób (" + this.getMaxCargo() + ')');
        }
        this.cargo += amount;
        this.gross_weight += amount * 60;
    }

    @Override
    public String toString() {
        return "Wagon Na Materiały Gazowe - " + super.toStringBasic() +
                ", ciśnienie= " + Math.round(this.pressure * 100.0) / 100.0 +
                ", gaz_szlachetny= " + this.is_noble +
                " }\n";
    }

}
