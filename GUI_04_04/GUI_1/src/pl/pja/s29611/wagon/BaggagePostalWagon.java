package pl.pja.s29611.wagon;

public class BaggagePostalWagon extends Wagon{

    private final int door_number;
    private final String material;

    public BaggagePostalWagon(String sender,int door_number,String material) {
        super(sender);
        this.door_number = door_number;
        this.material = material;
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
        return super.toString() +
        "door_number=" + door_number +
        "material=" + material ;
    }
}
