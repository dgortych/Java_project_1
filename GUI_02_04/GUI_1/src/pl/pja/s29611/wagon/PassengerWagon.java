package pl.pja.s29611.wagon;


public class PassengerWagon extends Wagon{

    private int seats;
    private int wagon_class;

    public PassengerWagon(String sender,int seats,int wagon_class){
       super(sender);
       this.seats = seats;
       this.wagon_class =  wagon_class;
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
                "seats=" + seats +
                "wagon_class=" + wagon_class ;
    }
    
}