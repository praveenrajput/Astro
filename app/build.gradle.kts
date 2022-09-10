plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("koin")
    id("com.squareup.sqldelight")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk = 32
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "com.praveen.astro"
        minSdk = 21
        targetSdk = 32
        versionCode = 2
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    kotlin {
        sourceSets {
            all {
                languageSettings.optIn("kotlin.RequiresOptIn")
            }
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    packagingOptions {
        resources {
            excludes.add(
                "META-INF/**"
            )
        }
    }

    tasks.withType<Test> {
        testLogging {
            events("started", "passed", "skipped", "failed")
        }
    }

    dependencies {
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
        implementation(AndroidX.core.ktx)
        implementation(AndroidX.appCompat)
        implementation(AndroidX.compose.ui)
        implementation(Google.android.material)
        implementation(AndroidX.compose.material)
        implementation(AndroidX.compose.ui.tooling)
        implementation(AndroidX.constraintLayout.compose)

        implementation(AndroidX.lifecycle.viewModelCompose)
        implementation(AndroidX.compose.runtime.liveData)
        implementation(AndroidX.navigation.compose)
        implementation(AndroidX.lifecycle.runtimeKtx)
        implementation(AndroidX.lifecycle.liveDataKtx)
        implementation(AndroidX.activity.compose)
        implementation(Koin.android)
        implementation(Ktor.io)
        implementation(Ktor.client.okHttp)
        implementation(Ktor.client.serialization)
        implementation(KotlinX.serialization.json)
        implementation(Ktor.client.logging)
        implementation(Square.sqlDelight.drivers.android)
        implementation(Square.sqlDelight.extensions.coroutines)

        implementation(COIL.compose)

        implementation("com.google.android.libraries.maps:maps:3.1.0-beta")
        implementation("com.google.maps.android:maps-v3-ktx:2.2.0")
        implementation("com.android.volley:volley:1.2.1")

        testImplementation(Ktor.Client.mock)
        testImplementation(Koin.junit4)
        testImplementation(Koin.test)

        testImplementation(Square.sqlDelight.drivers.jdbcSqlite)

        testImplementation(KotlinX.coroutines.test)
        testImplementation("com.google.truth:truth:1.1.3")
        testImplementation(Testing.robolectric)
        testImplementation(Testing.mockito.core)
        testImplementation(AndroidX.archCore.testing)
        testImplementation(AndroidX.test.core)

        testImplementation(Testing.junit4)
        androidTestImplementation(AndroidX.test.ext.junit)
        androidTestImplementation(AndroidX.test.espresso.core)
        androidTestImplementation(AndroidX.compose.ui.testJunit4)

        debugImplementation(AndroidX.compose.ui.testManifest)
    }
}
