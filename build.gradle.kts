plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = "fr.tolc"
version = "3.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Include the generated files in the source set
sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.6")
    type.set("IC") // Target IDE Platform
//    type.set("IU") // Target IDE Platform

    /* Plugin Dependencies */
    plugins.set(listOf("com.intellij.java", "org.jetbrains.idea.maven", "PsiViewer:232.2-SNAPSHOT"))
}

tasks {
    generateLexer {
        sourceFile.set(file("src/main/grammar/Cnd.flex"))
        targetOutputDir.set(file("src/main/gen/fr/tolc/jahia/language/cnd"))
        purgeOldFiles.set(true)
    }
    generateParser {
        dependsOn(generateLexer)
        sourceFile.set(file("src/main/grammar/Cnd.bnf"))
        targetRootOutputDir.set(file("src/main/gen"))
        pathToParser.set("fr/tolc/jahia/language/cnd/CndParser.java")
        pathToPsiRoot.set("fr/tolc/jahia/language/cnd/psi")
        purgeOldFiles.set(true)
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        dependsOn(generateLexer, generateParser)
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
