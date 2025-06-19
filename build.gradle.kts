plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.graphstream:gs-core:2.0")
    implementation("org.graphstream:gs-algo:2.0")
    implementation("org.graphstream:gs-ui-swing:2.0")
    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.27.3")
}

application {
    mainClass.set("org.example.MainKt")  // Specify your main class
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "org.example.MainKt" // Replace with your main class
        )
    }
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") })
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}