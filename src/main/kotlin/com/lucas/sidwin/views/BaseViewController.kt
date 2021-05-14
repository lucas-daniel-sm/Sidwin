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
import javafx.util.Callback
import java.net.URL
import java.util.*
import kotlin.system.exitProcess


class BaseViewController : Initializable {

    @FXML
    private lateinit var listView: ListView<Wallpaper>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        listView.items.addAll(FileHandler.wallpapers)
        listView.cellFactory = Callback {
            object : ListCell<Wallpaper>() {
                override fun updateItem(item: Wallpaper?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = null
                    graphic = if (empty || item == null) null else loadItem(item)
                }
            }
        }
    }

    private fun loadItem(wallpaper: Wallpaper): Node {
        val resource = javaClass.getResource("ListItem.fxml")
        val fxmlLoader = FXMLLoader(resource)
        fxmlLoader.controllerFactory = Callback { ListItemController(wallpaper) }
        return fxmlLoader.load()
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