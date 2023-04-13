package pl.pja.s29611.wagon;
import java.util.concurrent.ThreadLocalRandom;

public class PostalWagon extends Wagon{

    private final String size;
    private final boolean is_priority;

     public PostalWagon(String sender){
        super(sender);
        String[] sizes = {"small", "medium", "large"};
        this.size = sizes[ThreadLocalRandom.current().nextInt(0, 3)];
        this.is_priority = Math.random() < 0.5;
     }

    public PostalWagon(String sender,String size,boolean is_priority){
       super(sender);
       this.size = size;
       this.is_priority = is_priority;
    }

    public PostalWagon(String sender,String size,boolean is_priority,double net_weight,double cargo,double max_cargo){
        super(sender,net_weight,cargo,max_cargo);
        this.size = size;
        this.is_priority = is_priority;
    }


    public void load(double amount) throws Exception{
        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit ładowności poczty wynoszący : " + this.getMaxCargo());
        }
        this.cargo += amount;
        this.gross_weight += amount;
    }

    @Override
    public boolean needElectricity(){
        return true;
    }

    @Override
    public String toString() {
        return "Wagon Pocztowy - " + super.toString() +
                ", rozmiar= " + this.size +
                ", priorytetowy= " + this.is_priority + 
                " }\n";
    }
}
