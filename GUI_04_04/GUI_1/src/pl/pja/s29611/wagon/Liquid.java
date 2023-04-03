package pl.pja.s29611.wagon;

public interface Liquid {

    public default void printWarning(){
        System.out.println("XD");
    };

    public void pour();

}
