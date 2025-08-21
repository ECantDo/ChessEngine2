plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1" // Shadow plugin for fat jar
}

group = "org.ecando"
version = "1.2"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.github.bhlangonijr:chesslib:1.3.3")
    implementation("com.github.FrequentlyMissedDeadlines:Chess-UCI:1.32")
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass.set("org.ecando.Main")
}
