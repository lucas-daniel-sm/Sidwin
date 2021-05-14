plugins {
    application
    kotlin("jvm") version "1.4.10"
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("org.beryx.runtime") version "1.10.0"
}

group = "com.lucas.sidwin"
version = "1.0"
application.mainClassName = "$group.MainKt"

java.sourceCompatibility = JavaVersion.VERSION_14
java.targetCompatibility = JavaVersion.VERSION_14

repositories { mavenCentral() }

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.java.dev.jna:jna:5.8.0")

    val iKonliVersion = "12.0.0"
    val ikonliPackage = "org.kordamp.ikonli"
    implementation("$ikonliPackage:ikonli-javafx:$iKonliVersion")
    implementation("$ikonliPackage:ikonli-fontawesome5-pack:$iKonliVersion")
}

javafx {
    version = "15.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

runtime {
    options.set(
        listOf(
            "--strip-debug",
            "--compress",
            "2",
            "--no-header-files",
            "--no-man-pages"
        )
    )
    jpackage {
        imageName = "Sidwin"
        imageOptions = imageOptions + listOf(
            "--type", "msi",
            "--app-version", "1.0.0",
            "--copyright", "lucas_daniel",
            "--description", "Para salvar as imagens de destaque do Windows 10",
            "--vendor", "Lucas Daniel",
            "--win-menu",
            "--win-per-user-install",
            "--win-dir-chooser"
        )
        skipInstaller = true
    }
}
