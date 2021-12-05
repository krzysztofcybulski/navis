plugins {
    kotlin("jvm") version "1.6.0"
    groovy
}

group = "me.kcybulski"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":event-store"))
    implementation(kotlin("stdlib"))
    testImplementation("org.codehaus.groovy:groovy-all:3.0.9")
    testImplementation("org.spockframework:spock-core:2.1-M2-groovy-3.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
