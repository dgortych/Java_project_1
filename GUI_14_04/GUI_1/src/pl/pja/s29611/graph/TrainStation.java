package pl.pja.s29611.graph;

public class TrainStation {
    private static int nextId = 0;
    private final int id;
    private String name;

    public TrainStation(String name) {
        this.name = name;
        this.id = nextId++;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
