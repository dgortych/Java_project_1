package pl.pja.s29611.new_graph;


public class TrainStation {
    private static int nextId = 0;
    private int id;
    private String name;

    public TrainStation(String name) {
        this.name = name;
        this.id = nextId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return this.id;
    }
    @Override
    public String toString() {
        return name;
    }
}
