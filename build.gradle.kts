plugins {
    application
    kotlin("jvm") version "1.4.10"
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "com.lucas.sidwin"
version = "1.0"
application.mainClassName = "$group.MainKt"

repositories { mavenCentral() }

dependencies {
    implementation(kotlin("stdlib"))

    val iKonliVersion = "12.0.0"
    val ikonliPackage = "org.kordamp.ikonli"
    implementation("$ikonliPackage:ikonli-javafx:$iKonliVersion")
    implementation("$ikonliPackage:ikonli-fontawesome5-pack:$iKonliVersion")
}

javafx {
    version = "15.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}
