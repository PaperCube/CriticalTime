package studio.papercube.criticaltime.v2

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

object DateUtil {
    fun durationBetweenNowAndCertainTimeWithUnit(unit: ChronoUnit, destination: LocalDate): Long {
        val isUnitLongerThanDay = unit.ordinal > ChronoUnit.HALF_DAYS.ordinal
        return unit.between(
                if (isUnitLongerThanDay) LocalDate.now() else LocalDateTime.now(),
                if (isUnitLongerThanDay) destination else LocalDateTime.of(destination, LocalTime.of(9, 0, 0))
        )
    }
}