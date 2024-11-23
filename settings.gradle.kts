pluginManagement {
    repositories {
        google()
//        mavenCentral()
        jcenter();
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
//        mavenCentral()
        jcenter();
        //momo
        maven { url = uri("https://www.jitpack.io" ) }
        //thong ke
        //maven { url= uri("https://jitpack.io") }
    }
}

rootProject.name = "Hamstore"
include(":app")
 