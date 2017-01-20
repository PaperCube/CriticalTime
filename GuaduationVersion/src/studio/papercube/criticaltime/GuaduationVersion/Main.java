package studio.papercube.criticaltime.GuaduationVersion;

import studio.papercube.criticaltime.application.CriticalTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by imzhy on 2016/4/17.
 */
public class Main {
    public static void main(String[] args) {
        CriticalTime.setArgs(args);
        CriticalTime.startWithFinalDateOf(LocalDate.of(CriticalTime.currentYear(),6,11),"$YEAR年中考");
    }
}
