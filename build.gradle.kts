import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
	id("fabric-loom") version "1.7-SNAPSHOT"
	id("maven-publish")
}

version = "${"mod_version"()}+${"minecraft_version"()}"
group = "maven_group"()

base {
	archivesName = "archives_base_name"()
}

val loom = project.extensions.getByType<LoomGradleExtensionAPI>()

loom.apply {
    // silentMojangMappingsLicense()

    runs.configureEach {
        vmArg("-XX:+AllowEnhancedClassRedefinition")
        vmArg("-XX:+IgnoreUnrecognizedVMOptions")
        vmArg("-Dmixin.debug.export=true")
        vmArg("-Dmixin.env.remapRefMap=true")
        vmArg("-Dmixin.env.refMapRemappingFile=${projectDir}/build/createSrgToMcp/output.srg")
    }
}

repositories {
	maven {
		name = "QuiltMC Maven"
		url = uri("https://maven.quiltmc.org/repository/release/")
	}
	maven {
		name = "ParchmentMC"
		url = uri("https://maven.parchmentmc.org")
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${"minecraft_version"()}")

	"mappings"(loom.layered {
        mappings("org.quiltmc:quilt-mappings:${"minecraft_version"()}+build.${"qm_version"()}:intermediary-v2")
        parchment("org.parchmentmc.data:parchment-${"parchment_minecraft_version"()}:${"parchment_version"()}@zip")
        officialMojangMappings { nameSyntheticMembers = false }
    })

	modImplementation("net.fabricmc:fabric-loader:${"loader_version"()}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${"fabric_version"()}")
	
}

tasks.processResources {
	val properties = mapOf(
		"version" to version
	)

	inputs.properties(properties)

	filesMatching("fabric.mod.json") {
		expand(properties)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 21
	options.encoding = "UTF-8"
}

java {
	withSourcesJar()

	// sourceCompatibility = JavaVersion.VERSION_21
	// targetCompatibility = JavaVersion.VERSION_21
}

operator fun String.invoke(): String {
    return rootProject.ext[this] as? String
        ?: throw IllegalStateException("Property $this is not defined")
}
