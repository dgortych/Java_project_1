package pl.pja.s29611.threads;

import pl.pja.s29611.Locomotive;

public class ChangeSpeedRunnable implements Runnable{

    private final Locomotive locomotive;

    public ChangeSpeedRunnable(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    @Override
    public void run() {
        while (true) {
            try {
                locomotive.changeSpeed();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
