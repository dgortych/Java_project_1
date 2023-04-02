package pl.pja.s29611.wagon;


public class PostalWagon extends Wagon{


    private int size;

    private boolean is_priority;

    public PostalWagon(String sender){
        super(sender);
     }

    public void load(double amount) throws Exception{

        if( this.cargo + amount > this.getMaxCargo() ){
            throw new Exception("Przekroczono limit ładowności (" + this.getMaxCargo() + ')');
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
        return super.toString() +
        "size=" + size +
        "is_priority=" + is_priority ;
    }
}
