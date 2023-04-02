package pl.pja.s29611.wagon;

public class DiningWagon extends Wagon{

    private final int service_amount;
    private final int places_to_eat;

    public DiningWagon(String sender,int service_amount,int places_to_eat) {
        super(sender);
        this.service_amount = service_amount;
        this.places_to_eat = places_to_eat;
    }

    public void load(double amount) throws Exception{

        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit osób (" + this.getMaxCargo() + ')');
        }
        this.cargo += amount;
        this.gross_weight += amount * 60;
    }

    @Override
    public boolean needElectricity(){
        return true;
    }

    @Override
    public String toString() {
        return super.toString() +
                "service_amount=" + service_amount +
                "places_to_eat=" + places_to_eat ;
    }

}

