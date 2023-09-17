plugins {
    id(BuildPlugin.ANDROID_APPLICATION).version(DependenciesVersion.ANDROID_APPLICATION_VERSION)
        .apply(false)
    id(BuildPlugin.ANDROID_LIBRARY).version(DependenciesVersion.ANDROID_LIBRARY_VERSION)
        .apply(false)
    id(BuildPlugin.KOTLIN_ANDROID).version(DependenciesVersion.KOTLIN_ANDROID_VERSION).apply(false)
    id(BuildPlugin.HILT_ANDROID).version(DependenciesVersion.HILT_ANDROID_VERSION).apply(false)
    id(BuildPlugin.DEV_TOOLS_KSP).version(DependenciesVersion.DEV_TOOLS_KSP_VERSION).apply(false)
}