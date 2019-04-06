package studio.papercube.criticaltime.v2

import javafx.event.Event
import javafx.stage.Stage
import tornadofx.App

class CriticalTimeApp : App(MainView::class){
    private var mainStage: Stage? = null
    private val exitTimeout = 5000
    private var lastCloseAttempt = 0L

    private fun onClose(event: Event) {
        val stage = mainStage
        if (stage != null) {
            val now = System.currentTimeMillis()
            val delta = now - lastCloseAttempt
            lastCloseAttempt = now

            if(delta > exitTimeout){
                stage.isIconified = true
                event.consume()
            }
        }
    }

    override fun start(stage: Stage) {
        mainStage = stage
        super.start(stage)
        stage.sizeToScene()
        stage.setOnCloseRequest(this::onClose)
    }
}