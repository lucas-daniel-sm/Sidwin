package com.lucas.sidwin.views

import com.lucas.sidwin.files.FileHandler
import com.lucas.sidwin.models.Orientation
import com.lucas.sidwin.models.Wallpaper
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.util.Callback
import java.net.URL
import java.util.*
import kotlin.system.exitProcess

class BaseViewController(val stage: Stage) : Initializable {

    @FXML
    private lateinit var listView: ListView<Wallpaper>

    @FXML
    private lateinit var topBarUnusableArea: Pane

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        listView.items.addAll(FileHandler.wallpapers)

        val lastItem = listView.items.last()

        listView.cellFactory = Callback {
            object : ListCell<Wallpaper>() {
                override fun updateItem(item: Wallpaper?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = null
                    graphic = if (empty || item == null) null else loadItem(item)
                    if(item == lastItem) graphic.styleClass.add("last-item")
                }
            }
        }

        moveWindowListener()
    }

    private fun loadItem(wallpaper: Wallpaper): Node {
        val resource = javaClass.getResource("ListItem.fxml")
        val fxmlLoader = FXMLLoader(resource)
        fxmlLoader.controllerFactory = Callback { ListItemController(wallpaper) }
        return fxmlLoader.load()
    }

    private fun moveWindowListener() {
        var xOffset = 0.0
        var yOffset = 0.0

        topBarUnusableArea.setOnMousePressed {
            xOffset = stage.x - it.screenX
            yOffset = stage.y - it.screenY
        }

        topBarUnusableArea.setOnMouseDragged {
            stage.x = it.screenX + xOffset
            stage.y = it.screenY + yOffset
        }
    }

    @FXML
    private fun close(): Unit = exitProcess(0)

    @FXML
    private fun saveAll() = FileHandler.selectDirectoryAndSaveImages(listView.items)

    @FXML
    private fun saveSelected() =
        FileHandler.selectDirectoryAndSaveImages(listView.items.filter(Wallpaper::selected))

    @FXML
    private fun showAll() = filterItems(null)

    @FXML
    private fun showPortrait() = filterItems(Orientation.PORTRAIT)

    @FXML
    private fun showLandscape() = filterItems(Orientation.LANDSCAPE)

    private fun filterItems(orientation: Orientation?) {
        val wallpapers = if (orientation == null) {
            FileHandler.wallpapers
        } else {
            FileHandler.wallpapers.filter { it.orientation == orientation }
        }
        listView.items.clear()
        listView.items.addAll(wallpapers)
    }
}
