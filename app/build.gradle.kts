plugins {
    id("com.android.application")
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

//Dependencies libraries

val coreKtxLibName = rootProject.extra.get("core_ktx_libname") as String
val appCompatLibName = rootProject.extra.get("app_compat_libname") as String
val googleMaterialLibName = rootProject.extra.get("google_material_libname") as String
val constraintLayoutLibname = rootProject.extra.get("constraint_layout_libname") as String
val jUnitLibName = rootProject.extra.get("jUnit_libname") as String
val androidxTestJUnitLibName = rootProject.extra.get("androidx_test_junit_libname") as String
val espressoCoreLibName = rootProject.extra.get("espresso_core_libname") as String

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

}