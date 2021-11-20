plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("koin")
    id("com.squareup.sqldelight")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "com.praveen.astro"
        minSdk = 21
        targetSdk = 31
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
        implementation(Google.android.material)
        implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
        implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
        implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
        implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc02")

        implementation(AndroidX.lifecycle.viewModelCompose)
        implementation("androidx.compose.runtime:runtime-livedata:${rootProject.extra["compose_version"]}")
        implementation("androidx.navigation:navigation-compose:2.4.0-beta02")
        implementation(AndroidX.lifecycle.runtimeKtx)
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
        implementation(AndroidX.activity.compose)
        implementation(Koin.android)
        implementation("io.ktor:ktor:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-android:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktor_version"]}")
        implementation(KotlinX.serialization.json)
        implementation("io.ktor:ktor-client-logging-jvm:${rootProject.extra["ktor_version"]}")
        implementation(Square.sqlDelight.drivers.android)
        implementation("com.squareup.sqldelight:coroutines-extensions-jvm:_")

        implementation("io.coil-kt:coil-compose:1.4.0")

        implementation("com.google.android.libraries.maps:maps:3.1.0-beta")
        implementation("com.google.maps.android:maps-v3-ktx:2.2.0")
        implementation("com.android.volley:volley:1.2.1")

        testImplementation("io.ktor:ktor-client-mock:${rootProject.extra["ktor_version"]}")
        testImplementation("io.insert-koin:koin-test-junit4:3.1.2")
        testImplementation("io.insert-koin:koin-test:3.1.2")

        testImplementation("com.squareup.sqldelight:sqlite-driver:1.5.2")

        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
        testImplementation("com.google.truth:truth:1.1.3")
        testImplementation("org.robolectric:robolectric:4.7-alpha-2")
        testImplementation("org.mockito:mockito-core:4.0.0")
        testImplementation("androidx.arch.core:core-testing:2.1.0")
        testImplementation("androidx.test:core:1.4.1-alpha03")

        testImplementation(Testing.junit4)
        androidTestImplementation(AndroidX.test.ext.junit)
        androidTestImplementation(AndroidX.test.espresso.core)
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")

        debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")
    }
}
