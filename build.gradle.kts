plugins {
    kotlin("multiplatform") version "1.4.255-SNAPSHOT"
    kotlin("native.spm") version "1.4.255-SNAPSHOT"
}

group = "me.pagrom"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

kotlin {
    ios()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }

    spm {
        name = "Hello swift package manager plugin"

        platforms {
            macOS("11")
            iOS("12")
            tvOS("12")
            watchOS("12")
        }

        products {
            executable("productName") {
                +"myPackage1"
                +"myPackage2"
                +"myPackage3"
            }

            library("libraryName") {
                +"myLib"
            }
        }

        targets {
            // Regular target area
            target("targetName") {
                path = "./src/myPackage"
                publicHeadersPath = "./src/myPackage/headers"

                dependencies {
                    target("dependencyTargetName")
                    target(name = "dependencyTargetName")
                    target(name = "dependencyTargetName", condition = "TODO")

                    product("package", "package-kit")
                    product(name = "package", `package` = "package-kit")
                    product(name = "package", `package` = "package-kit", condition = "TODO")

                    // TODO: add `by name`
                    // TODO: fix condition
                }

                exclude {
                    +"foo.swift"
                    +"bar.swift"
                }

                sources {
                    +"main.swift"
                    +"utils.swift"
                }

                resources {
                    process("Resources/config.json")
                    copy("Resources/HTML")
                }
            }

            // Executable target area
            executableTarget("executableTargetName") {
                // Same as `regular target`
            }

            // Test target area
            testTarget("testTargetName") {
                // Same as `regular target`
            }

            // System library area
            systemLibrary("systemLibraryName") {
                path = "./src/lib"
            }

            binaryTarget(
                "SomeRemoteBinaryPackage",
                "https://url/to/some/remote/binary/package.zip",
                "The checksum of the XCFramework inside the ZIP archive."
            )

            binaryTarget(
                name = "SomeRemoteBinaryPackage",
                url = "https://url/to/some/remote/binary/package.zip",
                checksum = "The checksum of the XCFramework inside the ZIP archive."
            )

            binaryTarget(
                name = "SomeLocalBinaryPackage",
                path = "path/to/some.xcframework"
            )
        }
    }
}
