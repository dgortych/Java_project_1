package pl.pja.s29611.wagon;
import java.util.concurrent.ThreadLocalRandom;

public class BaggagePostalWagon extends Wagon{

    private final int door_number;
    private final String material;

    public BaggagePostalWagon(String sender){
        super(sender);
        this.door_number = ThreadLocalRandom.current().nextInt(1, 10);
        String[] materials = {"drewno", "stal", "żelazo"};
        this.material = materials[ThreadLocalRandom.current().nextInt(0, 3)];
     }

    public BaggagePostalWagon(String sender,int door_number,String material){
        super(sender);
        this.door_number = door_number;
        this.material = material;
    }

    public BaggagePostalWagon(String sender,int door_number,String material,double net_weight,double cargo,double max_cargo){
        super(sender,net_weight,cargo,max_cargo);
        this.door_number = door_number;
        this.material = material;
    }

    public void load(double amount) throws Exception{
        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit ładowności pocztowo-bagażowej wynoszący: " + this.getMaxCargo());
        }
        this.cargo += amount;
        this.gross_weight += amount;
    }

    @Override
    public String toString() {
        return "Wagon Bagażowo-Pocztowy - " + super.toString() +
                ", liczba_drzwi= " + this.door_number +
                ", materiał_wykonania= " + this.material + 
                " }\n" ;
    }
}
