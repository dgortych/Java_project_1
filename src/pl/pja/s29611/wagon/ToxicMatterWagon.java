package pl.pja.s29611.wagon;

public class ToxicMatterWagon extends HeavyTruckWagon{

    private int sensors_amount;
    private boolean health_dangerous;

    public ToxicMatterWagon(String sender,String protections,boolean reinforced_body,int sensors_amount,boolean health_dangerous) {
        super(sender,protections,reinforced_body);
        this.sensors_amount = sensors_amount;
        this.health_dangerous = health_dangerous;
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
                "sensors_amount=" + sensors_amount +
                "health_dangerous=" + health_dangerous ;
    }

}
