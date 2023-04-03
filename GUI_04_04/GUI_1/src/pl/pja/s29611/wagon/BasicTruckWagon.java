package pl.pja.s29611.wagon;

public class BasicTruckWagon extends Wagon{

    private String transported_material;
    private  int length;

    public BasicTruckWagon(String sender,String transported_material,int length) {
        super(sender);
        this.transported_material = transported_material;
        this.length = length;
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
                "transported_materialt=" + transported_material +
                "length=" + length ;
    }

}



