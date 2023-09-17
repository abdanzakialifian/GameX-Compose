plugins {
    id(BuildPlugin.ANDROID_APPLICATION)
    id(BuildPlugin.KOTLIN_ANDROID)
    id(BuildPlugin.HILT_ANDROID)
    id(BuildPlugin.DEV_TOOLS_KSP)
}

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk = AppConfig.SDK

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DependenciesVersion.COMPOSE_COMPILER_VERSION
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation(AppDependencies.ANDROIDX_CORE)
    implementation(AppDependencies.ANDROIDX_LIFECYCLE_RUNTIME)
    testImplementation(AppDependencies.JUNIT)
    androidTestImplementation(AppDependencies.ANDROIDX_TEST_JUNIT)
    androidTestImplementation(AppDependencies.ANDROIDX_TEST_ESPRESSO)

    // jetpack compose
    implementation(AppDependencies.ANDROIDX_COMPOSE_MATERIAL)
    implementation(AppDependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(AppDependencies.ANDROIDX_COMPOSE_UI)
    implementation(AppDependencies.ANDROIDX_COMPOSE_PREVIEW)
    androidTestImplementation(AppDependencies.ANDROIDX_COMPOSE_UI_TEST_JUNIT4)
    debugImplementation(AppDependencies.ANDROIDX_COMPOSE_UI_TOOLING)
    debugImplementation(AppDependencies.ANDROIDX_COMPOSE_UI_TEST_MANIFEST)
    implementation(AppDependencies.ANDROIDX_LIFECYCLE_VIEW_MODEL_COMPOSE)
    implementation(AppDependencies.ANDROIDX_NAVIGATION_COMPOSE)
    implementation(AppDependencies.ANDROIDX_HILT_NAVIGATION_COMPOSE)
    implementation(AppDependencies.LOTTIE_COMPOSE)
    implementation(AppDependencies.ANDROIDX_PAGING_COMPOSE)
    implementation(AppDependencies.COIL_COMPOSE)
    implementation(AppDependencies.ANDROIDX_LIFECYCLE_RUNTIME_COMPOSE)

    // networking
    implementation(AppDependencies.RETROFIT)
    implementation(AppDependencies.RETROFIT_CONVERTER_GSON)
    implementation(AppDependencies.OKHTTP)
    implementation(AppDependencies.OKHTTP_LOGGING_INTERCEPTOR)

    // dependency injection
    implementation(AppDependencies.HILT_ANDROID)
    ksp(AppDependencies.HILT_COMPILER)

    // kotlin flow
    implementation(AppDependencies.COROUTINES_ANDROID)
    implementation(AppDependencies.COROUTINES_CORE)

    // timber logging
    implementation(AppDependencies.TIMBER_LOGCAT)

    // paging 3
    implementation(AppDependencies.ANDROIDX_PAGING_RUNTIME)

    // rating bar
    implementation(AppDependencies.COMPOSE_RATING_BAR)

    // system bars customization
    implementation(AppDependencies.SYSTEM_UI_CONTROLLER)

    // palette
    implementation(AppDependencies.ANDROIDX_PALETTE)
}