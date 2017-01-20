package studio.papercube.criticaltime.application.service;

import java.util.TimerTask;

/**
 * Created by imzhy on 2016/5/2.
 */
public class FunctionalTimerTask extends TimerTask {
    private final Runnable runnable;

    public FunctionalTimerTask(Runnable runnable) {
        this.runnable=runnable;
    }
    @Override
    public void run() {
        runnable.run();
    }
}
