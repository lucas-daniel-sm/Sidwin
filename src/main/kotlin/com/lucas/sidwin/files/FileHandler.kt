package com.lucas.sidwin.files

import com.lucas.sidwin.models.Wallpaper
import com.lucas.sidwin.models.createWallpaper
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors.toList

object FileHandler {

    val wallpapers: List<Wallpaper>

    init {
        val assetsFolder = Paths.get(System.getProperty("user.home"))
            .resolve("AppData/Local/Packages/Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy/LocalState/Assets")
        wallpapers = Files.list(assetsFolder).filter {
            Files.size(it) > 350_000
        }.map(::createWallpaper).collect(toList())
    }

    fun selectDirectoryAndSaveImages(wallpapers: List<Wallpaper>) {
        val directoryChooser = DirectoryChooser()
        val outputDirectory: File? = directoryChooser.showDialog(Stage())

        outputDirectory ?: return
        saveImagesInDirectory(wallpapers, outputDirectory.toPath())

        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Sucesso!"
        alert.headerText = "Arquivos salvos com sucesso!"
        alert.contentText = "Arquivos salvos na pasta:\n$outputDirectory"

        alert.buttonTypes.clear()
        alert.buttonTypes.addAll(ButtonType.OK, ButtonType("Abrir pasta", ButtonBar.ButtonData.APPLY))

        alert.showAndWait().filter {
            it.buttonData == ButtonBar.ButtonData.APPLY
        }.ifPresent {
            val dir = outputDirectory.toString().replace("/", "\\")
            Runtime.getRuntime().exec("EXPLORER $dir")
        }
    }

    fun saveImagesInDirectory(wallpapers: List<Wallpaper>, outputDirectory: Path) {
        wallpapers.forEach {
            if (!it.imageName.endsWith(".jpg")) {
                it.imageName = "${it.imageName}.jpg"
            }
            val outFile = certifyThatFileIsUniqueAndResolve(it.imageName, outputDirectory)
            Files.copy(it.imageFile, outFile)
        }
    }

    private fun certifyThatFileIsUniqueAndResolve(fileName: String, location: Path): Path {
        val resolve = location.resolve(fileName)
        if(Files.exists(resolve)) {
            val newName = fileName.replace(Regex("\\.jpg\$"), "") + "(copy).jpg"
            return certifyThatFileIsUniqueAndResolve(newName, location)
        }
        return resolve
    }
}
