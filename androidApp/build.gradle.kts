plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "pl.msiwak.taxcalculator.android"
        minSdk = 27
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val lifecycle_version = "2.6.0-alpha01"
    val compose_version = "1.2.0"
    val ktorVersion = "2.1.0"

    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation ("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.compose.runtime:runtime:$compose_version")
    implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.compose.animation:animation:$compose_version")
    implementation ("androidx.compose.foundation:foundation:$compose_version")
    implementation ("androidx.compose.ui:ui-tooling:$compose_version")
    implementation ("androidx.compose.runtime:runtime-livedata:$compose_version")
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")
    implementation ("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")



}