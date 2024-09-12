plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.apollographql.apollo") version "4.0.0"
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
}

android {
    namespace = "daxo.the.anikat"
    compileSdk = 34

    defaultConfig {
        applicationId = "daxo.the.anikat"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

apollo {
    service("service") {
        packageName.set("daxo.the.anikat")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //dagger
    implementation(libs.dagger)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.android.processor)
    kapt(libs.dagger.compiler)

    //glide
    implementation(libs.glide)

    //coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.fragment.ktx)

    //apollo
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.normalized.cache)
    implementation(libs.apollo.normalized.cache.sqlite)

    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //blurry
    implementation("jp.wasabeef:blurry:4.0.1")

    // test

}