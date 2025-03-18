plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.foodplanner"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.foodplanner"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.lottie)
    implementation (libs.palette)

    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation (libs.glide)


    implementation (libs.rxandroid)
    implementation (libs.rxjava)
    implementation (libs.rxjava3.retrofit.adapter)
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    implementation (libs.room.rxjava3)
    implementation (libs.rxandroid)


    implementation (libs.rx.preferences)

    implementation (libs.dotsindicator)
    implementation (libs.navigation.fragment.ktx)
    implementation (libs.navigation.ui.ktx)
    implementation (libs.material)

    implementation (platform(libs.firebase.bom))
    implementation (libs.firebase.auth)
    implementation (libs.google.firebase.firestore)
    implementation (libs.firebase.database)
    implementation (libs.firebase.messaging)
    implementation (libs.play.services.auth.v2070)
    implementation (libs.facebook.android.sdk)

    implementation(libs.google.firebase.auth)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)

    //implementation (libs.work.runtime)
    implementation (libs.work.runtime.v290)
    implementation ("com.google.guava:guava:32.1.3-android")










}