plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    kotlin("kapt") version "1.8.10"
    id("com.google.dagger.hilt.android") version "2.44" apply false
}
true