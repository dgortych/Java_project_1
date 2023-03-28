package pl.pja.s29611.wagon;


public class PostalWagon extends Wagon{


    private int size;

    private boolean is_priority;

    public PostalWagon(){
        super();
     }

    public void load(double amount) throws Exception{

        if( this.cargo + amount > max_cargo ){
            throw new Exception("Przekroczono limit ładowności (" + this.max_cargo + ')'); 
        }
        this.cargo += amount;
        this.gross_weight += amount;
    }

    @Override
    public int needElectricity(){
        return 1;
    }

}
