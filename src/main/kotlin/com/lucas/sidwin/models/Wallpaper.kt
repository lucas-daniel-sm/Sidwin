package com.lucas.sidwin.models

import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

data class Wallpaper(
    val imageFile: Path,
    val orientation: Orientation,
    val height: Int,
    val width: Int,
    val size: Long,
    var imageName: String,
    var selected: Boolean = false,
)

fun createWallpaper(imageFile: Path): Wallpaper {
    val image = ImageIO.read(imageFile.toFile())
    val height = image.height
    val width = image.width

    return Wallpaper(
        imageFile = imageFile,
        orientation = if (width > height) Orientation.LANDSCAPE else Orientation.PORTRAIT,
        height = height,
        width = width,
        size = Files.size(imageFile),
        imageName = imageFile.fileName.toString(),
    )
}

enum class Orientation {
    LANDSCAPE, PORTRAIT;

    override fun toString(): String {
        return when(this) {
            LANDSCAPE -> "Paisagem"
            PORTRAIT -> "Retrato"
        }
    }
}
