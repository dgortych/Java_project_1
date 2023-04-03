package pl.pja.s29611.threads;

import pl.pja.s29611.Locomotive;
import pl.pja.s29611.TrainSet;
import pl.pja.s29611.new_graph.TrainGraph;

import javax.swing.plaf.basic.BasicTreeUI;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Comparator;

public class FileWriteRunnable implements Runnable{

    private final List<TrainSet> train_sets;
    private TrainGraph graph;

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

                train_sets.sort( Comparator.comparing(TrainSet::getRemainingRouteLength));

                for(TrainSet ts : train_sets) {
                    writer.write(ts.makeReport(this.graph) + "\n");
                }
                writer.write("\n");
                writer.flush(); // flush the writer to ensure data is written to file
                Thread.sleep(5000); // Pause for 5 seconds
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                System.out.println("Przerwano wątek: " + e.getMessage());
            }
        }
    }
}



