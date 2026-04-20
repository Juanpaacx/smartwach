plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.caratula"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.caratula"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.tiles)
    implementation(libs.tiles.material)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.watchface.complications.data.source.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.places)
    implementation(libs.compose.navigation)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)



        implementation ("androidx.navigation:navigation-compose:2.4.0-alpha10")
        implementation ("androidx.compose.material3:material3:1.0.0-alpha01")
        implementation ("androidx.compose.ui:ui:1.0.5")
        implementation ("androidx.compose.ui:ui-tooling:1.0.5")
        implementation ("androidx.compose.foundation:foundation:1.0.5")
        implementation ("androidx.compose.runtime:runtime-livedata:1.0.5")


    // Wear Compose
    implementation ("androidx.wear.compose:compose-material:1.1.0" )// Asegúrate de usar la última versión
    implementation ("androidx.wear.compose:compose-foundation:1.1.0")

    // Watchface
    implementation ("androidx.wear.watchface:watchface:1.1.0")
    implementation ("androidx.wear.watchface:watchface-style:1.1.0")
    implementation ("androidx.wear.watchface:watchface-complications-data:1.1.0")
    implementation ("androidx.wear.watchface:watchface-complications-rendering:1.1.0")

    // DataStore (para almacenar preferencias)
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

     implementation ("androidx.wear:wear-tooling-preview:1.0.0")


    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.work:work-runtime-ktx:2.7.1")
}