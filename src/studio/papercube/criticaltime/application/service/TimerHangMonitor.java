package studio.papercube.criticaltime.application.service;

/**
 * Created by USER904 on 2016/3/22.
 */
public class TimerHangMonitor implements Runnable {

    private static Runnable onExceptionRaised;

    public static void invokeWhenExceptionRaised(Runnable r) {
        if (onExceptionRaised != null) throw new IllegalStateException("Monitor already launched");
        onExceptionRaised = r;
        Thread th = new Thread(new TimerHangMonitor());
        th.setDaemon(true);
        th.start();
    }

    private long lastCheck=0;

    @Override
    public void run() {
        for (;;) {
            try {
                long current = System.currentTimeMillis();
                if(current<lastCheck) onExceptionRaised.run();
                lastCheck = current;
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
