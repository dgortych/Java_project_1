package pl.pja.s29611.threads;

import pl.pja.s29611.Locomotive;
import pl.pja.s29611.new_graph.TrainConnection;
import pl.pja.s29611.new_graph.TrainGraph;
import pl.pja.s29611.new_graph.TrainStation;
import java.util.List;

public class TrainRunnable extends Thread{

    private final Locomotive locomotive;
    private final TrainGraph graph;

    public TrainRunnable(Locomotive locomotive,TrainGraph graph) {
        this.locomotive = locomotive;
        this.graph = graph;
    }

    @Override
    public void run() {
        while(true) {
            List<TrainStation> path = this.graph.findPath(this.locomotive.getStartStation(),this.locomotive.getFinalStation());

            this.locomotive.assignRoute(path,calculateRouteDistance(path));
            for (int i = 0; i < path.size() - 1; i++) {
                Thread rideBetweenStations = new Thread( () -> {
                    synchronized (this.locomotive.getCurrentConection(this.graph)) {
                        this.locomotive.rideBetweenStations(this.graph);
                    }
                });
                rideBetweenStations.start();
                try {
                    rideBetweenStations.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

              // System.out.println(this.locomotive);  // do wywalenia
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<TrainStation> path2 = this.graph.findPath(this.locomotive.getFinalStation(),this.locomotive.getStartStation());
            this.locomotive.assignRoute(path2,calculateRouteDistance(path2));
            for (int k = 0; k < path2.size() - 1; k++) {

                Thread rideBetweenStations = new Thread(() -> {
                    synchronized (this.locomotive.getCurrentConection(this.graph)) {
                        this.locomotive.rideBetweenStations(this.graph);
                    }
                });

                rideBetweenStations.start();

                try {
                    rideBetweenStations.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

              //  System.out.println(this.locomotive);  // do wywalenia

            }
        }
    }

    public double calculateRouteDistance(List<TrainStation> path){
        double distance = 0;
        for(int i = 0; i < path.size() - 1; i++){
            TrainConnection connection = this.graph.getConnection(path.get(i),path.get(i+1));
            distance += connection.getDistance();
        }
        return distance;
    }

}
