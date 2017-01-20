package studio.papercube.criticaltime.application.service;

import javafx.concurrent.Task;

/**
 * Created by imzhy on 2016/5/8.
 */
public class FxUpdateService extends Task<Void> {
    private Runnable r;
    public FxUpdateService(Runnable r) {
        this.r=r;
    }
    @Override
    protected Void call() throws Exception {
        r.run();
        return null;
    }
}
