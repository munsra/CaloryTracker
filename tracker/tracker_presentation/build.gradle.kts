plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "it.pierosilvestri.tracker_presentation"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.trackerDomain))
    implementation(Coil.coilCompose)
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.2")
}