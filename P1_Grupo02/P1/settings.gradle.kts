pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MasterMind"
include(":GameLogic")
include(":DesktopEngine")
include(":DesktopGame")
include(":AndroidEngine")
include(":androidgame")
include(":Engine")
