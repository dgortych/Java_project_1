package pl.pja.s29611.wagon;


public class PassengerWagon extends Wagon{

    private int seats;

    protected int wagon_class;

    public PassengerWagon(int seats,int wagon_class){
       super();
       this.seats = seats;
       this.wagon_class =  wagon_class;
    }
    
     public void load(double amount) throws Exception{

        if( this.cargo + amount > max_cargo ){
            throw new Exception("Przekroczono limit osób (" + this.max_cargo + ')'); 
        }
        this.cargo += amount;
        this.gross_weight += amount * 60;
    }

    @Override
    public int needElectricity(){
        return 1;
    }

    
}