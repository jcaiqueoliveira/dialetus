import io.gitlab.arturbosch.detekt.extensions.DetektExtension

buildscript {

    repositories {
        google()
        jcenter()
        mavenLocal()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.fabric.io/public")
        maven(url = "https://dl.bintray.com/android/android-tools")
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.kotlinxSerializiationPlugin)
        classpath(BuildPlugins.ktlint)
        classpath(BuildPlugins.detekt)
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    repositories {
        google()
        jcenter()
        mavenLocal()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://jitpack.io")
    }

    configure<DetektExtension> {
        config = files("$rootDir/detekt.yml")
    }

    configurations.all {
        resolutionStrategy {
            force("androidx.constraintlayout:constraintlayout:1.1.3")
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
