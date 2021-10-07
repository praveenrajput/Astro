val sourceCompatibility by extra(JavaVersion.VERSION_1_8)
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val compose_version by extra("1.1.0-alpha04")
    val ktor_version by extra("1.6.3")
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.31")
        classpath("io.insert-koin:koin-gradle-plugin:2.2.3")
        classpath(Square.sqlDelight.gradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
