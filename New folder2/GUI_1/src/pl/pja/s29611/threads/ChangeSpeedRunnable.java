package pl.pja.s29611.threads;
import pl.pja.s29611.Locomotive;
import pl.pja.s29611.TrainSet;
import pl.pja.s29611.new_graph.TrainGraph;

public class ChangeSpeedRunnable implements Runnable{

    private final TrainSet train_set;
    private final TrainGraph train_graph;

    public ChangeSpeedRunnable(TrainSet train_set,TrainGraph train_graph) {
        this.train_set = train_set;
        this.train_graph = train_graph;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.train_set.getLocomotive().changeSpeed();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println(this.train_set.makeReport(train_graph));
            }
        }
    }
}
