package pl.pja.s29611.wagon;

public class BaggagePostalWagon extends Wagon{

    private int door_number;

    private String material;


    
    public void load(double amount) throws Exception{

        if( this.cargo + amount > max_cargo ){
            throw new Exception("Przekroczono limit osób (" + this.max_cargo + ')'); 
        }
        this.cargo += amount;
        this.gross_weight += amount * 60;
    }


}
