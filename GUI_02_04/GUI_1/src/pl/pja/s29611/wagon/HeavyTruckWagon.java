package pl.pja.s29611.wagon;

public class HeavyTruckWagon extends Wagon{

    private boolean reinforced_body;
    private String protections;

    public HeavyTruckWagon(String sender,String protections,boolean reinforced_body) {
        super(sender);
        this.protections = protections;
        this.reinforced_body = reinforced_body;
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
                "reinforced_body=" + reinforced_body +
                "protections=" + protections ;
    }

}


