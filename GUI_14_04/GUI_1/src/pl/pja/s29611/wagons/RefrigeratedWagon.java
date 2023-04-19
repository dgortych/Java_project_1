package pl.pja.s29611.wagons;
import java.util.concurrent.ThreadLocalRandom;

public class RefrigeratedWagon extends BasicTruckWagon{

    private final double temperature;
    private final double power;

    public RefrigeratedWagon(String sender) {
        super(sender,"Mrożonki",ThreadLocalRandom.current().nextDouble(15, 30));
        this.temperature = ThreadLocalRandom.current().nextInt(-30, 0);
        this.power = ThreadLocalRandom.current().nextInt(1000, 5000);
    }
    public RefrigeratedWagon(String sender,double temperature,double power) {
        super(sender,"Mrożonki",ThreadLocalRandom.current().nextDouble(15, 30));
        this.temperature = temperature;
        this.power = power;
    }
    public RefrigeratedWagon(String sender,String transported_material,int length,double temperature,double power,double net_weight,double cargo,double max_cargo) {
        super(sender,transported_material,length,net_weight,cargo,max_cargo);
        this.temperature = temperature;
        this.power = power;
    }

    @Override
    public String toString() {
        return "Wagon Chłodniczy - " + super.toStringBasic() +
                ", temperatura= " + Math.round(this.temperature * 100.0) / 100.0  +
                ", moc= " + Math.round(this.power * 100.0) / 100.0 +
                " }";
    }

}
