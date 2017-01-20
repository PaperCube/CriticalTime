package studio.papercube.criticaltime.application.localizations;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

/**
 * Created by imzhy on 2016/6/14.
 */
public class ChineseLocalization extends ChronoUnitLocalization {
    public ChineseLocalization(){
        define(DAYS, "天");
        define(HOURS, "小时");
    }
}
