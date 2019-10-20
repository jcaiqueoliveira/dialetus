# Dialetus

Android repository for [Dialetus](https://github.com/mvfsillva/dialetus)

This project is used to experiment kotlin Coroutines and flow. Applying some abstractions
created by (Ubiratan)[https://github.com/ubiratansoares] and the nice sample project [Norris](https://github.com/dotanuki-labs/norris)

[Api Documentation](https://github.com/mvfsillva/dialetus-service#-api)

## Building and Running

### Running from IDE

- Ensure you have Android Studio 3.5.1 or newer
- Is recommend to install Kotlinx.Serialization plugin on your IDE ([instructions](https://github.com/Kotlin/kotlinx.serialization))

### Building from CLI

To run all unit tests and build a APK, execute

```
./gradlew build
```

### Running integration tests

To run acceptance tests powered by Instrumentation + Espresso, execute

```
./gradlew connectedCheck
```
## Knowledge Stack

This project leverages on

- Kotlin
- Coroutines for Threading
- Kodein for Dependency Injection
- Kotlinx.Serialization for automatic JSON handling
- OkHttp4 + Retrofit for networking over HTTP

## Following the project
(Issues)[https://github.com/jcaiqueoliveira/dialetus/issues/]
(Roadmap)[https://github.com/jcaiqueoliveira/dialetus/projects/]
(Contributing)[../semantic_git.md]
