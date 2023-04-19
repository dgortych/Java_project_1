package pl.pja.s29611.threads;

import pl.pja.s29611.Locomotive;
import pl.pja.s29611.graph.TrainConnection;
import pl.pja.s29611.graph.TrainGraph;
import pl.pja.s29611.graph.TrainStation;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
            if(this.locomotive == null)
                Thread.currentThread().interrupt();

            List<TrainStation> path = this.graph.findPath(this.locomotive.getStartStation(), this.locomotive.getFinalStation());
            while(path == null) {
                this.locomotive.setFinalStation(graph.getStations().get(ThreadLocalRandom.current().nextInt(0, graph.getStations().size())));
                path = this.graph.findPath(this.locomotive.getStartStation(), this.locomotive.getFinalStation());
            }

            this.locomotive.assignRoute(path,calculateRouteDistance(path));
            this.locomotive.setSpeed(ThreadLocalRandom.current().nextInt(80, 180));
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

            }
            TrainStation temp = this.locomotive.getStartStation();
            this.locomotive.setStartStation(this.locomotive.getFinalStation());
            this.locomotive.setFinalStation(temp);
            this.locomotive.setTrainRouteStationId(0);
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
