package pl.pja.s29611.wagon;
import java.util.concurrent.ThreadLocalRandom;


public class DiningWagon extends Wagon{

    private int service_amount;
    private final int places_to_eat;

    public DiningWagon(String sender){
        super(sender);
        this.service_amount = ThreadLocalRandom.current().nextInt(5, 15);
        this.places_to_eat = ThreadLocalRandom.current().nextInt(10, 35);
     }

    public DiningWagon(String sender,int service_amount,int places_to_eat){
       super(sender);
       this.service_amount = service_amount;
       this.places_to_eat = places_to_eat;
    }

    public DiningWagon(String sender,int service_amount,int places_to_eat,double net_weight,double cargo,double max_cargo){
        super(sender,net_weight,cargo,max_cargo);
        this.service_amount = service_amount;
        this.places_to_eat = places_to_eat;
    }

    public void load(double amount) throws Exception{
        if( this.service_amount + amount > this.places_to_eat / 2){
            throw new Exception("Przekroczono limit serwisu, który wynosi: " + this.service_amount);
        }
        this.cargo += amount * 60;
        this.service_amount += amount;
        this.gross_weight += amount * 60;
    }
    @Override
    public boolean needElectricity(){
        return true;
    }

    @Override
    public String toString() {
        return "Wagon Restauracyjny - " + super.toString() +
                ", ilość_obsługi= " + service_amount +
                ", miejsca_do_jedzenia= " + places_to_eat + 
                 " }\n";
    }

}

