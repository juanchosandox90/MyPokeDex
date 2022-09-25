// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0-alpha01")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
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
        set("retrofit2_version", "2.9.0")
        set("hilt_version", "2.42")
        set("hilt_androidx_version", "1.0.0")
        set("okhttp3_version", "5.0.0-alpha.2")
        set("okHttp3_interceptor_version", "5.0.0-alpha.2")
        set("databinding_compiler_version", "3.2.0-alpha10")
        set("databinding_commons_version", "7.3.0")
        /*
        Tests Versions of Tests Libraries
        Android Arch
        MockK
        CoroutinesTest
        Truth of Google
         */
        set("android_arch_test_version", "2.1.0")
        set("mockk_version", "1.12.3")
        set("coroutines_test_version", "1.6.4")
        set("truth_test_version", "1.1.2")


        //Dependencies Libraries name
        set("core_ktx_libname", "androidx.core:core-ktx:")
        set("app_compat_libname", "androidx.appcompat:appcompat:")
        set("google_material_libname", "com.google.android.material:material:")
        set("constraint_layout_libname", "androidx.constraintlayout:constraintlayout:")
        set("jUnit_libname", "junit:junit:")
        set("androidx_test_junit_libname", "androidx.test.ext:junit:")
        set("espresso_core_libname", "androidx.test.espresso:espresso-core:")

        //Navigation libraries name
        set("navigation_fragment_ktx_libname", "androidx.navigation:navigation-fragment-ktx:")
        set("navigation_ui_ktx_libname", "androidx.navigation:navigation-ui-ktx:")

        //Retrofit Libraries name
        set("retrofit2_libname", "com.squareup.retrofit2:retrofit:")
        set("retrofit2_gson_libname", "com.squareup.retrofit2:converter-gson:")

        //Hilt Libraries name
        set("hilt_libname", "com.google.dagger:hilt-android:")
        set("hilt_google_compiler_libname", "com.google.dagger:hilt-android-compiler:")
        set("hilt_androidx_compiler_libname", "androidx.hilt:hilt-compiler:")

        //Okhttp3 Libraries name
        set("okhttp3_libname", "com.squareup.okhttp3:okhttp:")
        set("okhttp3_logging_libname", "com.squareup.okhttp3:logging-interceptor:")

        //Databinding Libraries name
        set("data_binding_compiler_libname", "com.android.databinding:compiler:")
        set("data_binding_common_libname", "androidx.databinding:databinding-common:")

        //Test Libraries for Unit tests
        set("android_core_arch_test_libname","androidx.arch.core:core-testing:")
        set("mockk_test_libname","io.mockk:mockk:")
        set("coroutines_test_libname","org.jetbrains.kotlinx:kotlinx-coroutines-test:")
        set("truth_libname","com.google.truth:truth:")

    }
}
plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}