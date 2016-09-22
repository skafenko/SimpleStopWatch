package stopwatch;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mykhailo on 18.09.2016.
 */
public class StopWatch extends Thread {

    private AtomicBoolean isPaused = new AtomicBoolean(false);
    private TimerData timerData;

    public StopWatch(TimerData timerData) {
        this.timerData = timerData;
    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long time = 0;
        try {
            while (!interrupted()) {
                if (this.isPaused.get()) {
                    startTime = System.currentTimeMillis() - time;
                }

                time = System.currentTimeMillis() - startTime;
                timerData.setTime(time);

                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        } finally {
            timerData.stop();
        }
    }

    public boolean isPaused() {
        return isPaused.get();
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused.set(isPaused);
    }
}
