package studio.papercube.criticaltime.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * Created by imzhy on 2016/2/28.
 */
public class DateUpdateService {
    private final LocalDate targetDate;
    private Consumer<String> numberSetter;
    private Consumer<String> unitSetter;

    public DateUpdateService(LocalDate targetDate, Consumer<String> setText, Consumer<String> unit) {
        this.targetDate = targetDate;
        numberSetter = setText;
        Timer t = new Timer(this.getClass().getSimpleName(), true);
        t.schedule(new DateUpdateTask(), 0, 1000);
    }

    /**
     * 使用特定的单位，获得到特定日期的单位时间
     * 如果使用的时间单位大于半天（如使用的是天或者周），那么计算将使用基于日期的LocalDate，这样计算将会包含今天。
     * 否则（比如使用的是小时），那么计算将使用基于精确时间的LocalDateTime。
     *
     * @param unit        使用的单位.
     * @param destination 目标时间
     * @return 计算出的单位时间
     */
    public static long durationBetweenNowAndCertainTimeUsingCertainUnit(ChronoUnit unit, LocalDate destination) {
        boolean isUnitLongerThanDay = unit.ordinal() > ChronoUnit.HALF_DAYS.ordinal();
        return unit.between(
                isUnitLongerThanDay ? LocalDate.now() : LocalDateTime.now(),
                isUnitLongerThanDay ? destination : LocalDateTime.of(destination, LocalTime.of(9, 0, 0))
        );
    }

    private class DateUpdateTask extends TimerTask {
        @Override
        public void run() {
            numberSetter.accept(String.valueOf(durationBetweenNowAndCertainTimeUsingCertainUnit(ChronoUnit.DAYS, targetDate)));
        }
    }
}
