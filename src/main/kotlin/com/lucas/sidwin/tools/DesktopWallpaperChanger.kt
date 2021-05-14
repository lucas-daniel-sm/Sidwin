package com.lucas.sidwin.tools

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.win32.W32APIOptions
import java.nio.file.Path
import java.nio.file.Paths

object DesktopWallpaperChanger {
    fun setWallpaper(path: Path) {
        User32Library.INSTANCE.SystemParametersInfo(
            0x14,
            0,
            path.toAbsolutePath().toString(),
            1
        )
    }
}

fun main() {
    DesktopWallpaperChanger.setWallpaper(Paths.get("I:\\lucas\\Desktop\\a.jpg"))
}

@Suppress("FunctionName")
private interface User32Library : Library {
    fun SystemParametersInfo(one: Int, two: Int, s: String?, three: Int): Boolean

    companion object {
        val INSTANCE = Native.load("user32", User32Library::class.java, W32APIOptions.DEFAULT_OPTIONS) as User32Library
    }
}
