plugins {
    kotlin("jvm") version "1.6.0"
}

group = "me.kcybulski.navis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
//    implementation("org.slf4j:slf4j-simple:2.0.0-alpha5")
}
