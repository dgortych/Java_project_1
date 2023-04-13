package pl.pja.s29611.wagon;

import java.util.concurrent.ThreadLocalRandom;

public class LiquidToxicMatterWagon extends HeavyTruckWagon implements Liquid{

    private final int year_of_production;
    private final boolean up_to_date_service;

    public LiquidToxicMatterWagon(String sender) {
        super(sender);
        this.year_of_production = ThreadLocalRandom.current().nextInt(1970, 2023);
        this.up_to_date_service = Math.random() < 0.9;
    }
    public LiquidToxicMatterWagon(String sender,int year_of_production,boolean up_to_date_service) {
        super(sender);
        this.year_of_production = year_of_production;
        this.up_to_date_service = up_to_date_service;
    }
    public LiquidToxicMatterWagon(String sender,int year_of_production,boolean up_to_date_service,int protections,boolean reinforced_body,double net_weight,double cargo,double max_cargo) {
        super(sender,protections,reinforced_body,net_weight,cargo,max_cargo);
        this.year_of_production = year_of_production;
        this.up_to_date_service = up_to_date_service;
    }

    /*
    public void load(double amount) throws Exception{
        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit osób (" + this.getMaxCargo() + ')');
        }
        this.cargo += amount;
        this.gross_weight += amount;
    }
    */

    @Override
    public void pour() {

    }

    @Override
    public void printWarning() {
        System.out.println("Uwaga! Wagon zawiera materiał toksyczny!!");
    }

    @Override
    public String toString() {
        return "Wagon Na Ciekłe Materiały Toksyczne - " + super.toStringBasic() +
                ", Rok_produkcji= " + this.year_of_production +
                ", Aktualny_serwis= " + this.up_to_date_service +
                " }\n";
    }


}
