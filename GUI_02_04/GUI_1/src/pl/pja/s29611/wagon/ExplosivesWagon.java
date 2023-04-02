package pl.pja.s29611.wagon;

public class ExplosivesWagon extends HeavyTruckWagon{

    private int danger_level;
    private int blast_power;

    public ExplosivesWagon(String sender,String protections,boolean reinforced_body,int danger_level,int blast_power) {
        super(sender,protections,reinforced_body);
        this.danger_level = danger_level;
        this.blast_power = blast_power;
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
                "danger_level=" + danger_level +
                "blast_power=" + blast_power ;
    }

}


