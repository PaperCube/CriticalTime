package studio.papercube.criticaltime.eighthGradeVersion;

import studio.papercube.criticaltime.application.CriticalTime;

import java.time.LocalDate;

/**
 * Created by imzhy on 2016/4/17.
 */
public class Main {
    public static void main(String[] args) {
        CriticalTime.setArgs(args);
        CriticalTime.startWithFinalDateOf(LocalDate.of(CriticalTime.currentYear(),6,14),"$YEAR年会考");
    }
}
