package com.lucas.sidwin.views

import com.lucas.sidwin.files.FileHandler
import com.lucas.sidwin.models.Wallpaper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.net.URL
import java.util.*

class ListItemController(private val wallpaper: Wallpaper) : Initializable {

    @FXML
    private lateinit var imageView: ImageView

    @FXML
    private lateinit var lblImageSize: Label

    @FXML
    private lateinit var lblImageBytes: Label

    @FXML
    private lateinit var lblOrientation: Label

    @FXML
    private lateinit var checkBox: CheckBox

    @FXML
    private lateinit var tfImageName: TextField

    val isSelected get() = wallpaper.selected

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        lblImageBytes.text = wallpaper.size.toString()
        lblImageSize.text = "${wallpaper.height}x${wallpaper.width}"
        lblOrientation.text = wallpaper.orientation.toString()
        tfImageName.text = wallpaper.imageName
        imageView.image = Image(wallpaper.imageFile.toUri().toURL().toExternalForm())
        checkBox.selectedProperty().addListener { _, _, value -> wallpaper.selected = value }
    }

    @FXML
    private fun save() = FileHandler.selectDirectoryAndSaveImages(listOf(wallpaper))
}
