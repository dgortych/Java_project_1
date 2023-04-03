package pl.pja.s29611.wagon;

public class LiquidToxicMatterWagon extends HeavyTruckWagon implements Liquid{

    private int xd1;
    private int xd2;

    public LiquidToxicMatterWagon(String sender,String protections,boolean reinforced_body,int xd1,int xd2) {
        super(sender,protections,reinforced_body);
        this.xd1 = xd1;
        this.xd2 = xd2;
    }

    @Override
    public void pour() {

    }

    @Override
    public void printWarning() {
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
                "xd1=" + xd1 +
                "xd2=" + xd2 ;
    }


}
