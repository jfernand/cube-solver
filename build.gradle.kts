
plugins {
    id("com.github.ben-manes.versions") version "0.31.0"
    id ("org.openjfx.javafxplugin") version "0.0.8"

    kotlin("jvm") version "1.6.21"
}
group ="es.casaroja"

repositories {
    mavenCentral()
}
dependencies {
//    implementation(files("/usr/local/Cellar/org.opencv/4.4.0_2/share/java/opencv4/org.opencv-440.jar"))
//    implementation("org.opencv:org.opencv:4.4.0-2")
    implementation(kotlin("stdlib-jdk8"))
    implementation ("com.github.ajalt:mordant:1.2.1")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "11"
    modules = listOf( "javafx.controls", "javafx.fxml" )
}
