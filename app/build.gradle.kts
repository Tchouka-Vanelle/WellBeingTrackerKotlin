plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}
repositories {
    google() // Assurez-vous que ce référentiel est inclus
    mavenCentral()
    gradlePluginPortal() // Ajoutez-le si ce n'est pas déjà fait
}
android {
    namespace = "edu.ufp.wellbeingtracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.ufp.wellbeingtracker"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //add by me
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.recyclerview)

    // on ajoute Room ktx
    // rend les requêtes et opérations avec la base de données plus lisibles et plus efficaces en Kotlin
    /*he Room KTX library provides coroutine and Flow
     support for Room, allowing you to use suspend functions*/
    implementation(libs.androidx.room.ktx)
    // on ajoute  Room Compiler KSP
    // particulier le code qui convertit les entités en objets et vice versa, l'annotation
    ksp(libs.androidx.room.compiler)
    // on ajoute Room Runtime
    //Ce module contient les classes qui gèrent l'accès à la base de données (connexion, transactions, etc.), ainsi que l'implémentation des objets Entity et DAO
    implementation(libs.androidx.room.runtime)

    //to encrypt password
    implementation(libs.bcrypt)

    //for the local dateTime
    implementation(libs.threetenabp)

}