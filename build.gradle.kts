val sourceCompatibility by extra(JavaVersion.VERSION_1_8)
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val compose_version by extra("1.3.1")
    val ktor_version by extra("1.6.3")
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.7.10")
        classpath("io.insert-koin:koin-gradle-plugin:3.2.0")
        classpath(Square.sqlDelight.gradlePlugin)
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
