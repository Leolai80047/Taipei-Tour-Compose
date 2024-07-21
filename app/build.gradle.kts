import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.jetbrains.kotlin.plugin.parcelize)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.leodemo.taipei_tour_compose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.leodemo.taipei_tour_compose"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        val properties = Properties()
        val secretPropertiesFile = project.rootProject.file("secret.properties")
        if (secretPropertiesFile.canRead()) {
            properties.load(FileInputStream(secretPropertiesFile))
        }
        create("release") {
            storeFile = File("$rootDir/app/taipeitour.keystore")
            storePassword = properties["RELEASE_KEYSTORE_PASSWORD"].toString()
            keyAlias = properties["RELEASE_KEY_ALIAS"].toString()
            keyPassword = properties["RELEASE_KEY_PASSWORD"].toString()
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {

    // implement submodule
    implementation(project(path = ":taipeitour_core:app"))

    // Baseline Profile
    "baselineProfile"(project(":baselineprofile"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.profileinstaller)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.andoridx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.org.jetbrain.kotlinx.serialization.json)

    // hilt
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    // glide
    implementation(libs.com.github.bumptech.glide.compose)

    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // chrome custom tab
    implementation(libs.androidx.browser)
}