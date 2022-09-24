// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0-alpha01")
    }

    extra.apply {

        //Dependencies versions
        set("core_ktx_version", "1.9.0")
        set("app_compat_version", "1.5.1")
        set("google_material_version", "1.6.1")
        set("constraint_layout_version", "2.1.4")
        set("jUnit_version", "4.13.2")
        set("androidx_test_junit_version", "1.1.3")
        set("espresso_core_version", "3.4.0")
        set("navigation_version", "2.6.0-alpha01")

        //Dependencies Libraries name
        set("core_ktx_libname", "androidx.core:core-ktx:")
        set("app_compat_libname", "androidx.appcompat:appcompat:")
        set("google_material_libname", "com.google.android.material:material:")
        set("constraint_layout_libname", "androidx.constraintlayout:constraintlayout:")
        set("jUnit_libname", "junit:junit:")
        set("androidx_test_junit_libname", "androidx.test.ext:junit:")
        set("espresso_core_libname", "androidx.test.espresso:espresso-core:")

        //Navigation libraries name
        set("navigation_fragment_ktx", "androidx.navigation:navigation-fragment-ktx:")
        set("navigation_ui_ktx", "androidx.navigation:navigation-ui-ktx:")

    }
}
plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}