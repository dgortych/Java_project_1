package pl.pja.s29611.wagon;

import java.util.concurrent.ThreadLocalRandom;

public class PassengerWagon extends Wagon{

    private int seats;
    private int wagon_class;
    private int passengers_amount;

    public PassengerWagon(String sender){
        super(sender);
        this.seats = ThreadLocalRandom.current().nextInt(25, 100);
        this.wagon_class = ThreadLocalRandom.current().nextInt(1, 4);
        this.passengers_amount = ThreadLocalRandom.current().nextInt(0, this.seats + 1);
     }

    public PassengerWagon(String sender,int seats,int wagon_class,int passengers_amount){
       super(sender);
       this.seats = seats;
       this.wagon_class =  wagon_class;
       this.passengers_amount = passengers_amount;
    }

    public PassengerWagon(String sender,int seats,int wagon_class,int passengers_amount,double net_weight,double cargo,double max_cargo){
        super(sender,net_weight,cargo,max_cargo);
        this.seats = seats;
        this.wagon_class = wagon_class;
        this.passengers_amount = passengers_amount;
    }

     public void load(double amount) throws Exception{
        if( this.passengers_amount + amount > this.seats ){
            throw new Exception("Przekroczono limit osób, który wynosi: " + this.seats);
        }
        this.cargo += amount * 60;
        this.passengers_amount += amount;
        this.gross_weight += amount * 60;
    }

    @Override
    public boolean needElectricity(){
        return true;
    }

    @Override
    public String toString() {
        return "Wagon Pasażerski - " + super.toString() +
                ", miejsca_siedzące= " + this.seats +
                ", pasażerowie= " + this.passengers_amount + 
                ", klasa= " + this.wagon_class + " }\n";
    }
    
}