import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    targetSdk = 34
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                
                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
                
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") {
                    jvmTarget = "17"
                }

                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.5.8"
                }
            }

            dependencies {
                val bom = platform("androidx.compose:compose-bom:2024.02.00")
                add("implementation", bom)
                add("implementation", "androidx.compose.ui:ui")
                add("implementation", "androidx.compose.ui:ui-graphics")
                add("implementation", "androidx.compose.ui:ui-tooling-preview")
                add("implementation", "androidx.compose.material3:material3")
                add("implementation", "androidx.activity:activity-compose:1.8.2")

                // Testing
                add("testImplementation", "junit:junit:4.13.2")
                add("androidTestImplementation", "androidx.test.ext:junit:1.1.5")
                add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.5.1")
                add("androidTestImplementation", platform("androidx.compose:compose-bom:2024.02.00"))
                add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
                add("debugImplementation", "androidx.compose.ui:ui-tooling")
                add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
            }
        }
    }
} 