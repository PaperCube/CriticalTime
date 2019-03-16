package studio.papercube.criticaltime.v2

import javafx.animation.Timeline
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import resources.Resource
import tornadofx.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class SpellOfTime(
        val days: Long,
        val hours: Long,
        val minutes: Long,
        val seconds: Long
) {
    companion object {
        fun ofSeconds(seconds: Long): SpellOfTime {
            val s = seconds % 60
            val m = seconds / 60 % 60
            val hours = seconds / 3600 % 24
            val days = seconds / 3600 / 24
            return SpellOfTime(days, hours, m, s)
        }
    }
}

class MainView : View() {
    override val root
        get() = rootPane
    private val textPreciseDay: Text = text("-1")
    private val textPreciseHour: Text = text("-1")
    private val textPreciseMinute: Text = text("-1")
    private val textPreciseSecond: Text = text("-1")
    private val textRemainingDays:Text = text("-1")

    init {
        setStageIcon(Image(Resource.getResourceAsStream("icon.png")))
        startCounter()
    }

    private fun startCounter() {
        timeline {
            keyframe(1.seconds) {
                setOnFinished {
                    val now = LocalDateTime.now()
                    val secs = ChronoUnit.SECONDS.between(now, LocalDateTime.of(now.year, 6, 7, 9, 0, 0))
                    val (d, h, m, s) = SpellOfTime.ofSeconds(secs)
                    textPreciseDay.text = d.toString()
                    textPreciseHour.text = h.toString()
                    textPreciseMinute.text = m.toString()
                    textPreciseSecond.text = s.toString()
                    textRemainingDays.text = DateUtil.durationBetweenNowAndCertainTimeWithUnit(
                            ChronoUnit.DAYS,
                            LocalDate.of(now.year, 6, 7)).toString()
                }
            }
            cycleCount = Timeline.INDEFINITE
        }
    }

    private val rootPane = stackpane {
        applyBackgroundStyles()
        borderpane {
            val sizeLarge = 150.0
            val sizeSmall = 60.0
            vgrow = Priority.NEVER
            hgrow = Priority.ALWAYS
            center = gridpane{
                this@gridpane.alignment = Pos.CENTER
                row {
                    vbox {
                        alignment = Pos.BASELINE_CENTER

//                padding = Insets(3.0)
                        this += textRemainingDays.apply{
                            applyForegroundTextStyles()
                            font = loadNumberFont(sizeLarge)
                        }
                    }
                }

                row {
                    hbox outer@{
                        padding = Insets(15.0)
                        alignment = Pos.BASELINE_CENTER
                        arrayOf(
                                textPreciseDay to Text("d "),
                                textPreciseHour to Text("h "),
                                textPreciseMinute to Text("m "),
                                textPreciseSecond to Text("s ")
                        ).forEach { (num, unit) ->
                            num.applyForegroundTextStyles()
                            num.font = loadNumberFont(sizeSmall)
                            unit.applyForegroundTextStyles()
                            unit.font = loadNumberFont(sizeSmall * 0.38)
                            this += num
                            this += unit
                        }
                    }
                }
            }
            padding = Insets(20.0)
        }
    }

    private fun Pane.applyBackgroundStyles() {
        background = Background(
                BackgroundFill(
                        Color.rgb(50, 54, 57),
                        CornerRadii.EMPTY,
                        Insets.EMPTY))
    }

    private fun Text.applyForegroundTextStyles() {
        fill = Color.WHITE
    }

    private fun loadNumberFont(size: Double): Font {
        return Font.loadFont(Resource.getResourceAsStream(NUMBER_FONT_PATH), size)
    }
}

