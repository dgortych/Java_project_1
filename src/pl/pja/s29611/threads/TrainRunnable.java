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
            List<TrainStation> path = graph.findPath(locomotive.getStartStation(),locomotive.getFinalStation());

            locomotive.assignRoute(path,calculateRouteDistance(path));
            for (int i = 0; i < path.size() - 1; i++) {

                Thread rideBetweenStations = new Thread( () -> {
                    synchronized (locomotive.getCurrentConection(graph)) {
                        locomotive.rideBetweenStations(graph);
                    }
                });

                rideBetweenStations.start();

                try {
                    rideBetweenStations.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(locomotive);
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<TrainStation> path2 = graph.findPath(locomotive.getFinalStation(),locomotive.getStartStation());
            locomotive.assignRoute(path2,calculateRouteDistance(path2));
            for (int k = 0; k < path2.size() - 1; k++) {

                Thread rideBetweenStations = new Thread(() -> {
                    synchronized (locomotive.getCurrentConection(graph)) {
                        locomotive.rideBetweenStations(graph);
                    }
                });

                rideBetweenStations.start();

                try {
                    rideBetweenStations.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(locomotive);

            }
        }
    }

    public double calculateRouteDistance(List<TrainStation> path){
        double distance = 0;
        for(int i = 0; i < path.size() - 1; i++){
            TrainConnection connection = graph.getConnection(path.get(i),path.get(i+1));
            distance += connection.getDistance();
        }
        return distance;
    }

}
