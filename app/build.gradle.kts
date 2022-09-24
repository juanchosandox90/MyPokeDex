plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

//Dependencies Versions
val coreKtxVersion = rootProject.extra.get("core_ktx_version") as String
val appCompatVersion = rootProject.extra.get("app_compat_version") as String
val googleMaterialVersion = rootProject.extra.get("google_material_version") as String
val constraintLayoutVersion = rootProject.extra.get("constraint_layout_version") as String
val jUnitVersion = rootProject.extra.get("jUnit_version") as String
val androidxTestJunitVersion = rootProject.extra.get("androidx_test_junit_version") as String
val espressoCoreVersion = rootProject.extra.get("espresso_core_version") as String

//Navigation
val navigationVersion = rootProject.extra.get("navigation_version") as String

//Retrofit
val retrofit2Version = rootProject.extra.get("retrofit2_version") as String

//Hilt
val hiltVersion = rootProject.extra.get("hilt_version") as String
val hiltAndroidxVersion = rootProject.extra.get("hilt_androidx_version") as String

//Okhttp3
val okhttp3Version = rootProject.extra.get("okhttp3_version") as String
val okhttp3InterceptorVersion = rootProject.extra.get("okHttp3_interceptor_version") as String

//Databinding
val databindingCompilerVersion = rootProject.extra.get("databinding_compiler_version") as String
val databindingCommonVersion = rootProject.extra.get("databinding_commons_version") as String

//Dependencies libraries

val coreKtxLibName = rootProject.extra.get("core_ktx_libname") as String
val appCompatLibName = rootProject.extra.get("app_compat_libname") as String
val googleMaterialLibName = rootProject.extra.get("google_material_libname") as String
val constraintLayoutLibname = rootProject.extra.get("constraint_layout_libname") as String
val jUnitLibName = rootProject.extra.get("jUnit_libname") as String
val androidxTestJUnitLibName = rootProject.extra.get("androidx_test_junit_libname") as String
val espressoCoreLibName = rootProject.extra.get("espresso_core_libname") as String

//Navigation
val navigationKtxFragmentLibName =
    rootProject.extra.get("navigation_fragment_ktx_libname") as String
val navigationKtxUILibName = rootProject.extra.get("navigation_ui_ktx_libname") as String

//Retrofit
val retrofit2LibName = rootProject.extra.get("retrofit2_libname") as String
val retrofit2GsonLibName = rootProject.extra.get("retrofit2_gson_libname") as String

//Hilt
val hiltLibname = rootProject.extra.get("hilt_libname") as String
val hiltGoogleCompilerLibname = rootProject.extra.get("hilt_google_compiler_libname") as String
val hiltAndroidXCompilerLibname = rootProject.extra.get("hilt_androidx_compiler_libname") as String

//Okhttp3
val okhttp3Libname = rootProject.extra.get("okhttp3_libname") as String
val okhttp3LoggingLibname = rootProject.extra.get("okhttp3_logging_libname") as String

//Databinding
val databindingCompilerLibname = rootProject.extra.get("data_binding_compiler_libname") as String
val databindingCommonLibname = rootProject.extra.get("data_binding_common_libname") as String

android {
    namespace = "com.sandoval.mypokedex"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.sandoval.mypokedex"
        minSdk = 26
        targetSdk = 33
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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation("$coreKtxLibName$coreKtxVersion")
    implementation("$appCompatLibName$appCompatVersion")
    implementation("$googleMaterialLibName$googleMaterialVersion")
    implementation("$constraintLayoutLibname$constraintLayoutVersion")
    testImplementation("$jUnitLibName$jUnitVersion")
    androidTestImplementation("$androidxTestJUnitLibName$androidxTestJunitVersion")
    androidTestImplementation("$espressoCoreLibName$espressoCoreVersion")

    //Navigation
    implementation("$navigationKtxFragmentLibName$navigationVersion")
    implementation("$navigationKtxUILibName$navigationVersion")

    //Retrofit
    implementation("$retrofit2LibName$retrofit2Version")
    implementation("$retrofit2GsonLibName$retrofit2Version")

    //Hilt
    implementation("$hiltLibname$hiltVersion")
    kapt("$hiltGoogleCompilerLibname$hiltVersion")
    kapt("$hiltAndroidXCompilerLibname$hiltAndroidxVersion")

    //Okhttp3
    implementation("$okhttp3Libname$okhttp3Version")
    implementation("$okhttp3LoggingLibname$okhttp3InterceptorVersion")

    //Databinding
    kapt("$databindingCompilerLibname$databindingCompilerVersion")
    kapt("$databindingCommonLibname$databindingCommonVersion")
}