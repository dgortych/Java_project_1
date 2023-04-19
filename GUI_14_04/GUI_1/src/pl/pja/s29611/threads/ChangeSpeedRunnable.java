package pl.pja.s29611.threads;
import pl.pja.s29611.Locomotive;

public class ChangeSpeedRunnable implements Runnable{

    private final Locomotive locomotive;

    public ChangeSpeedRunnable(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    @Override
    public void run() {
        boolean throw_exception = true;
        while (true) {
            if(locomotive == null)
                Thread.currentThread().interrupt();
            try {
                locomotive.changeSpeed();
            } catch (Exception e) {
                if(throw_exception)
                    System.err.println(e.getMessage());
                throw_exception = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


