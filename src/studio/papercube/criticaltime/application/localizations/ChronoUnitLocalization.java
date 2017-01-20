package studio.papercube.criticaltime.application.localizations;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

/**
 * Created by imzhy on 2016/5/15.
 */
public abstract class ChronoUnitLocalization {
    private static ChronoUnitLocalization chronoUnitLocalization = new ChineseLocalization();
    private Map<ChronoUnit, String> config = new HashMap<>();


    /**
     * 获取对应的本地化
     *
     * @param unit 原始单位
     * @return
     */
    public static String get(ChronoUnit unit) {
        String res = chronoUnitLocalization.config.get(unit);
        return res != null ? res : unit.toString().toUpperCase();
    }

    protected void define(ChronoUnit unit, String name) {
        try {
            config.put(unit, name);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void setGlobalLocalization(ChronoUnitLocalization localization) {
        ChronoUnitLocalization.chronoUnitLocalization = localization;
    }
}
