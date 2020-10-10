apply(from = "../kotlin-module.gradle")

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":domain"))
    implementation(Dependencies.coroutines)
    implementation(Dependencies.kotlinxSerialization)

    StandaloneModule.main.forEach(::implementation)
    StandaloneModule.unitTesting.forEach(::testImplementation)

    NetworkingDependencies.main.forEach(::implementation)
    NetworkingDependencies.testing.forEach(::testImplementation)
}
