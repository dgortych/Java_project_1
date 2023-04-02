package pl.pja.s29611.wagon;

public class LiquidMatterWagon extends BasicTruckWagon{

    private double density;
    private boolean inflammability;

    public LiquidMatterWagon(String sender,String transported_material,int length,double density,boolean inflammability) {
        super(sender,transported_material,length);
        this.density = density;
        this.inflammability = inflammability;
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
                "density=" + density +
                "inflammability=" + inflammability;
    }

}
