plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)

}

android {
    namespace = "com.app.prodiatest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.prodiatest"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
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
    implementation (libs.lifecycle.livedata.ktx)
    implementation (libs.lifecycle.viewmodel.ktx)

    // retrofit
    implementation (libs.retrofit)

    // GSON
    implementation (libs.retrofit2.converter.gson)
    implementation (libs.gson)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glideCompiler)

    //Swipe Refresh Layout
    implementation (libs.androidx.swiperefreshlayout)

    // Paging
    implementation (libs.androidx.paging.runtime)

    // Room
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation (libs.kotlinx.coroutines.core) // Versi terbaru saat ini
    implementation (libs.kotlinx.coroutines.android) // Versi terbaru saat ini


}