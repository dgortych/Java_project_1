package pl.pja.s29611.wagon;
import java.util.concurrent.ThreadLocalRandom;

public class BasicTruckWagon extends Wagon{

    private String transported_material;
    private double length;

    public BasicTruckWagon(String sender) {
        super(sender);
        this.length = ThreadLocalRandom.current().nextInt(1, 10);
        String[] materials = {"coal", "gold", "wood"};
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
                ", długość= " + this.length + 
                " }";
    }

    public String toStringBasic() {
        return super.toString() + 
                ", transportowany_materiał= " + this.transported_material +
                ", długość= " + this.length;
                
    }

}



