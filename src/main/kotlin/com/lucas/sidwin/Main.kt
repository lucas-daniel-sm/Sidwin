package com.lucas.sidwin

import com.lucas.sidwin.views.BaseViewController
import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle

fun main() = launch(App::class.java)

class App : Application() {
    override fun start(stage: Stage) {
        val baseViewLocation = "views/BaseView.fxml"
        val resource = javaClass.getResource(baseViewLocation)
        val fxmlLoader = FXMLLoader(resource)

        fxmlLoader.setControllerFactory { BaseViewController(stage) }

        val scene = Scene(fxmlLoader.load(), null)
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.scene = scene
        stage.minWidth = 900.0
        stage.minHeight = 700.0
        stage.show()
    }
}
