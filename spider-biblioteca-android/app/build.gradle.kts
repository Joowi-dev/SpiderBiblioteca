plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.joel.spiderbiblioteca"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.joel.spiderbiblioteca"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM - gestiona versiones de todas las librerías Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navegación
    implementation(libs.androidx.navigation.compose)

    // ViewModel con Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Retrofit + Gson (llamadas a la API REST)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    // Coil (cargar imágenes desde URL)
    implementation(libs.coil.compose)

    // Splash screen nativo
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // DataStore (favoritos locales)
    implementation(libs.androidx.datastore.preferences)

    // Material Icons Extended (Sort, ViewList, GridView, Share...)
    implementation("androidx.compose.material:material-icons-extended")

    debugImplementation(libs.androidx.ui.tooling)
}
