package pl.pja.s29611.wagon;
import java.util.concurrent.ThreadLocalRandom;

public class BasicTruckWagon extends Wagon{

    private final String transported_material;
    private final double length;

    public BasicTruckWagon(String sender) {
        super(sender);
        this.length = ThreadLocalRandom.current().nextDouble(15, 30);
        String[] materials = {"węgiel", "złoto", "drewno"};
        this.transported_material = materials[ThreadLocalRandom.current().nextInt(0, 3)];
    }
    public BasicTruckWagon(String sender,String transported_material,double length) {
        super(sender);
        this.transported_material = transported_material;
        this.length = length;
    }
    public BasicTruckWagon(String sender,String transported_material,double length,double net_weight,double cargo,double max_cargo) {
        super(sender,net_weight,cargo,max_cargo);
        this.transported_material = transported_material;
        this.length = length;
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
        return "Wagon Towarowy Podstawowy - " + super.toString() +
                ", transportowany_materiał= " + this.transported_material +
                ", długość= " + Math.round(this.length * 100.0) / 100.0 +
                " }\n";
    }

    public String toStringBasic() {
        return super.toString() + 
                ", transportowany_materiał= " + this.transported_material +
                ", długość= " + Math.round(this.length * 100.0) / 100.0;
                
    }

}



