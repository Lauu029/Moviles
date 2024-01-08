plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}
dependencies {
    implementation(project(mapOf("path" to ":Engine")))
    implementation ("com.google.code.gson:gson:2.8.8")

}
