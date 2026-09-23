# Contributing

## Build

You need JDK 21 and Gradle 8.10 (the GitHub Actions install it; locally you can use the same versions).

```bash
gradle test --stacktrace
gradle build --stacktrace
```

`test` is a plain JUnit suite: spawn multipliers and structure spacing. It does not start Minecraft.

`build` downloads NeoForge, Minecraft and Kotlin for Forge, then produces `build/libs/uiborninconfiguration-*.jar`.

The repository does not commit `gradle-wrapper.jar`. CI uses `gradle/actions/setup-gradle` with Gradle 8.10. If you generate the wrapper locally and commit the jar, you can switch the workflows to `./gradlew`.

## Layout

- `src/main/kotlin/com/uiborninconfiguration/mod/logic` — rules that JUnit can run without the game
- `src/main/kotlin/com/uiborninconfiguration/mod` — NeoForge wiring and the config screen
- `src/main/java/com/uiborninconfiguration/mod/mixin` — mixins into structure placement and the config-button registration
- `src/test/kotlin` — JUnit 5

## Config

Spawn and structure rates are a NeoForge `SERVER` config (`ServerConfig`). In multiplayer only the server file matters. The same screen also edits Born in Configuration. See the README.
