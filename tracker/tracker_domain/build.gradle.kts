plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "it.pierosilvestri.tracker_domain"
}

dependencies {
    implementation(project(Modules.core))
}