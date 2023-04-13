package pl.pja.s29611.threads;

import pl.pja.s29611.TrainSet;
import pl.pja.s29611.new_graph.TrainGraph;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Comparator;

public class FileWriteRunnable implements Runnable{

    private final List<TrainSet> train_sets;
    private final TrainGraph graph;

    public  FileWriteRunnable(List<TrainSet> train_sets, TrainGraph graph) {
        this.train_sets = train_sets;
        this.graph = graph;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter("AppState.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true){
            try {
                /*
                List<TrainSet> train_sets_copy = new ArrayList<>();
                for (TrainSet trainSet : train_sets) {
                    train_sets_copy.add(new TrainSet(trainSet));
                }
                train_sets_copy.sort( Comparator.comparing(TrainSet::getRemainingRouteLength));

                for(TrainSet ts : train_sets_copy) {
                  writer.write(ts.makeReport(this.graph, ts.getLocomotive()) + "\n");
                  writer.flush();
                }
                */

                train_sets.sort(Comparator.comparing(TrainSet::getRemainingRouteLength));

                for(TrainSet ts : train_sets) {
                    writer.write(ts.makeReport(this.graph) + "\n");
                }
                writer.write("---------------------------------------------------\n");
                writer.flush();
                Thread.sleep(5000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                System.out.println("Przerwano wątek: " + e.getMessage());
            }
        }
    }
}



