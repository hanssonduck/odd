import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application") version "8.1.0-beta03" apply false
    id("com.android.library") version "8.1.0-beta03" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-10" apply false
    kotlin("android") version "1.8.21" apply false
    kotlin("multiplatform") version "1.8.21" apply false
    kotlin("plugin.serialization") version "1.8.21" apply false
}

allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
