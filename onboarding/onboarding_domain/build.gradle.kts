plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "it.pierosilvestri.onboarding_domain"
}

dependencies {
    implementation(project(Modules.core))
}