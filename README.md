# MissionControl
A JavaFX GUI command centre for a Mars rover

# Dependencies
- java-json library (should be linked with .jar)
- jinput (jinput.jar, and jinput-natives-all.jar)
- Java 1.8.0, JavaFX in Java SE 8
- [Gradle 4+](https://gradle.org/) 

# Building and Running:

Build & Run:
```bash
$ gradle build
$ gradle jfxRun
```

Build Natives:
```bas
$ gradle jfxJar
$ gradle jfxNative
```

Builds are then located in build/jfx/native