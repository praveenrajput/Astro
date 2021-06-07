plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlinx-serialization")
}

android {
    compileSdk = 30
    buildToolsVersion = "31.0.0 rc4"

    defaultConfig {
        applicationId = "com.praveen.astro"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dependencies {

        implementation("androidx.core:core-ktx:1.5.0")
        implementation("androidx.appcompat:appcompat:1.3.0")
        implementation("com.google.android.material:material:1.3.0")
        implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
        implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
        implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha06")
        implementation("androidx.compose.runtime:runtime-livedata:1.0.0-beta08")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
        implementation("androidx.activity:activity-compose:1.3.0-beta01")
        implementation("io.insert-koin:koin-android-viewmodel:2.2.3")
        implementation("io.ktor:ktor:1.6.0")
        implementation("io.ktor:ktor-client-android:1.6.0")
        implementation("io.ktor:ktor-client-serialization:1.6.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
        implementation("io.ktor:ktor-client-logging-jvm:1.6.0")

        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.2")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    }
}
