import com.android.tools.r8.internal.li

plugins {
    alias(libs.plugins.android.library)
    kotlin("plugin.serialization") version "2.3.0"
}

android {
    namespace = "com.hungtm.sdk.sdk_base"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    //Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    //Square-Retrofit
    implementation(libs.square.retrofit)
    implementation(libs.square.retrofit.kotlinx.serialization)
    //Square-OkHttp
    implementation(libs.logging.interceptor)
    //Jetbrains
    implementation(libs.jetbrains.kotlinx.serialization.json)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}