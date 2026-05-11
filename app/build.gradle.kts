plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.parknow1"
    compileSdk = 36

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.parknow1"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.googleid)

    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.auth)
    implementation(libs.supabase.storage)

    implementation(libs.ktor.client.android)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.biometric)
    implementation(libs.coil)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // GOOGLE MAPS
    implementation("com.google.android.gms:play-services-maps:18.2.0")
// LOCATION (GPS)
    implementation("com.google.android.gms:play-services-location:21.3.0")
// COROUTINES TASKS (await())
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

}