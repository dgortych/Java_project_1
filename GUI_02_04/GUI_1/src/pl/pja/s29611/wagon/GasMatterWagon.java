package pl.pja.s29611.wagon;

public class GasMatterWagon extends BasicTruckWagon{

    private double pressure;
    private boolean is_noble;

    public GasMatterWagon(String sender,String transported_material,int length,double pressure,boolean is_noble) {
        super(sender,transported_material,length);
        this.pressure = pressure;
        this.is_noble = is_noble;
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
                "pressure=" + pressure +
                "is_noble=" + is_noble ;
    }

}
