plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")

}


android {
    namespace = "com.muratkorkmazoglu.movie_app"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.muratkorkmazoglu.movie_app"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        flavorDimensions("dev")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            versionNameSuffix = "a"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    productFlavors {
        create("dev") {
            dimension = "dev"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "Movies Dev")
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/discover/\"")

        }
        create("prod") {
            dimension = "prod"
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"
            resValue("string", "app_name", "Movies")
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/discover/\"")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.material)

    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    //Splash
    implementation(libs.androidx.core.splashscreen)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // lint
    lintChecks(libs.lint.checks)
    // compose state events
    implementation(libs.compose.state.events)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)


    testImplementation(libs.turbine)
    testImplementation(libs.truth)

    // To use the androidx.test.core APIs
    androidTestImplementation(libs.androidx.test.core)
    // Kotlin extensions for androidx.test.core
    androidTestImplementation(libs.androidx.test.ktx)

    // To use the JUnit Extension APIs
    testImplementation(libs.androidx.test.ext)
    // Kotlin extensions for androidx.test.ext.junit
    testImplementation(libs.androidx.test.ext.ktx)

    // To use the Truth Extension APIs
    testImplementation(libs.truth.ext)
    testImplementation(libs.jetbrains.kotlin.test)
    implementation(libs.androidx.navigation.testing)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)

    implementation(libs.dataStore)
    implementation(libs.dataStore.preferences)

    implementation(libs.libphonenumber.android)
    implementation(libs.lottie.compose)
    implementation(libs.androidx.runtime.livedata)

    implementation(libs.play.services.auth.api.phone)
    implementation(libs.play.services.auth)

    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.systemuicontroller)

    //Constraint Layout
    implementation(libs.constraintlayout)

    // Compression image
    implementation(libs.compressor)
    implementation(libs.coil.compose)

    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)

}