import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20"
    id("net.neoforged.moddev") version "2.0.146"
    `maven-publish`
    idea
}

fun prop(name: String): String = project.property(name) as String

val modId = prop("mod_id")
val modVersion = prop("mod_version")
val modGroupId = prop("mod_group_id")
val modName = prop("mod_name")
val modLicense = prop("mod_license")
val modAuthors = prop("mod_authors")
val modDescription = prop("mod_description")
val minecraftVersion = prop("minecraft_version")
val minecraftVersionRange = prop("minecraft_version_range")
val neoVersion = prop("neo_version")
val loaderVersionRange = prop("loader_version_range")
val parchmentMinecraftVersion = prop("parchment_minecraft_version")
val parchmentMappingsVersion = prop("parchment_mappings_version")
val kffVersion = prop("kff_version")
val kffVersionRange = prop("kff_version_range")
val chaosVersionRange = prop("chaos_version_range")
val bicVersionRange = prop("bic_version_range")
val geckolibVersionRange = prop("geckolib_version_range")

version = modVersion
group = modGroupId

base {
    archivesName.set(modId)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

kotlin {
    jvmToolchain(21)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

sourceSets.main {
    resources {
        srcDir("src/generated/resources")
        exclude("**/*.bbmodel")
        exclude("src/generated/**/.cache")
    }
}

repositories {
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
    mavenCentral()
}

neoForge {
    version = neoVersion

    parchment {
        mappingsVersion = parchmentMappingsVersion
        minecraftVersion = parchmentMinecraftVersion
    }

    runs {
        create("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        create("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        create("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        create("data") {
            data()
            programArguments.addAll(
                listOf(
                    "--mod", modId,
                    "--all",
                    "--output", file("src/generated/resources/").absolutePath,
                    "--existing", file("src/main/resources/").absolutePath,
                )
            )
        }
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel.set(org.slf4j.event.Level.DEBUG)
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    implementation("thedarkcolour:kotlinforforge-neoforge:$kffVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

// Обычный expand() гоняет TOML через Groovy-шаблоны и падает на скобках
// в комментариях. Здесь только подстроки ${key}.
val generateModMetadata by tasks.registering(ProcessResources::class) {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraftVersion,
        "minecraft_version_range" to minecraftVersionRange,
        "neo_version" to neoVersion,
        "loader_version_range" to loaderVersionRange,
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_license" to modLicense,
        "mod_version" to modVersion,
        "mod_authors" to modAuthors,
        "mod_description" to modDescription,
        "kff_version_range" to kffVersionRange,
        "chaos_version_range" to chaosVersionRange,
        "bic_version_range" to bicVersionRange,
        "geckolib_version_range" to geckolibVersionRange,
    )
    inputs.properties(replaceProperties)
    from("src/main/templates")
    into("build/generated/sources/modMetadata")
    filteringCharset = "UTF-8"
    filter { line ->
        var result = line
        for ((key, value) in replaceProperties) {
            result = result.replace("\${$key}", value)
        }
        result
    }
}

sourceSets.main.get().resources.srcDir(generateModMetadata)
neoForge.ideSyncTask(generateModMetadata)

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes(
            "Specification-Title" to modId,
            "Specification-Vendor" to modAuthors,
            "Specification-Version" to "1",
            "Implementation-Title" to modName,
            "Implementation-Version" to modVersion,
            "Implementation-Vendor" to modAuthors,
        )
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file://${project.projectDir}/repo")
        }
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
