package pl.pja.s29611;

import java.util.List;
import java.util.concurrent.TimeUnit;
import pl.pja.s29611.new_graph.TrainGraph;
import pl.pja.s29611.new_graph.TrainStation;


public class TrainSimulation {
/*
    private TrainGraph graph;
    private List<Locomotive> locomotives;

    public TrainSimulation(TrainGraph graph,List<Locomotive> locomotives) {
        this.graph = graph;
        this.locomotives = locomotives;
    }

    public void runSimulation() {
        for (Locomotive locomotive : locomotives) {
            LocomotiveRunnable locomotiveRunnable = new LocomotiveRunnable(locomotive);
            new Thread(locomotiveRunnable).start();
        }
    }

    private class LocomotiveRunnable implements Runnable {

        private Locomotive locomotive;

        public LocomotiveRunnable(Locomotive locomotive) {
            this.locomotive = locomotive;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // Wait for a train to arrive at the station
                    synchronized (station) {
                        while (station.getTrain() == null) {
                            station.wait();
                        }
                    }

                    // Get the train and its current and next stations
                    Train train = station.getTrain();
                    TrainStation currentStation = train.getCurrentStation();
                    TrainStation nextStation = train.getNextStation();

                    // Travel to the next station
                    int distance = graph.getDistance(currentStation, nextStation);
                    train.travel(distance);

                    // Arrive at the next station and stop for 2 seconds
                    train.setCurrentStation(nextStation);
                    synchronized (nextStation) {
                        nextStation.setTrain(train);
                        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                        nextStation.setTrain(null);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

 */
}