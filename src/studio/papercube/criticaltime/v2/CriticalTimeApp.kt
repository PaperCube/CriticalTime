package studio.papercube.criticaltime.v2

import javafx.event.Event
import javafx.stage.Stage
import tornadofx.App

class CriticalTimeApp : App(MainView::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.sizeToScene()
        stage.setOnCloseRequest(Event::consume)
    }
}