plugins {
    id("java")
    id("application")
}

group = "com.github.lory24"
version = "1.0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")
    compileOnly("org.jetbrains:annotations:26.1.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.github.lory24.officinaio.Program")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}