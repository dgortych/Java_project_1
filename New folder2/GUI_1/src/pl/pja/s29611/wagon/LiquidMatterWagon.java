package pl.pja.s29611.wagon;

import java.util.concurrent.ThreadLocalRandom;

public class LiquidMatterWagon extends BasicTruckWagon implements Liquid{

    private final double density;
    private final boolean inflammability;

    public LiquidMatterWagon(String sender) {
        super(sender,"Ciecz",ThreadLocalRandom.current().nextDouble(15, 30));
        this.density = ThreadLocalRandom.current().nextInt(700, 10000);
        this.inflammability = Math.random() < 0.5;
    }
    public LiquidMatterWagon(String sender,double density,boolean inflammability) {
        super(sender,"Ciecz",ThreadLocalRandom.current().nextDouble(15, 30));
        this.density = density;
        this.inflammability = inflammability;
    }
    public LiquidMatterWagon(String sender,double density,boolean inflammability,int length,String transported_material,double net_weight,double cargo,double max_cargo) {
        super(sender,transported_material,length,net_weight,cargo,max_cargo);
        this.density = density;
        this.inflammability = inflammability;
    }

    public void load(double amount) throws Exception{
        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit ładowności wynoszący: " + this.getMaxCargo());
        }
        this.cargo += amount;
        this.gross_weight += amount;
    }

    @Override
    public void printWarning() {
        if(this.inflammability)
            System.out.println("Uwaga! Wagon może zawierać materiał łatwopalny!!");
    }

    @Override
    public void pour() {

    }

    @Override
    public String toString() {
        return "Wagon Na Materiały Ciekłe - " + super.toStringBasic() +
                ", gęstość=  " + this.density +
                ", łatwopalność= " + this.inflammability +
                " }\n";
    }
}
