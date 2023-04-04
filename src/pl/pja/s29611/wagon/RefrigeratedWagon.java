package pl.pja.s29611.wagon;


public class RefrigeratedWagon extends BasicTruckWagon{

    private final double temperature;
    private final double power;

    public RefrigeratedWagon(String sender,String transported_material,int length,double temperature,double power) {
        super(sender,transported_material,length);
        this.power = power;
        this.temperature = temperature;
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
                "temperature=" + temperature +
                "power=" + power ;
    }



}
