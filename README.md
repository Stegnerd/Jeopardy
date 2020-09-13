## Jeopardy

[![CircleCI](https://circleci.com/gh/Stegnerd/Jeopardy/tree/master.svg?style=shield)](https://circleci.com/gh/Stegnerd/Jeopardy/tree/master)
[![Codecov](https://codecov.io/gh/Stegnerd/Jeopardy/branch/master/graphs/badge.svg)](https://codecov.io/gh/Stegnerd/Jeopardy)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.4.10-blue.svg)](http://kotlinlang.org/)
[![Gradle](https://lv.binarybabel.org/catalog-api/gradle/latest.svg?v=6.1.1)](https://lv.binarybabel.org/catalog/gradle/latest)
[![API](https://img.shields.io/badge/API-30%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=30)

This is a project to learn several things of android development. It uses Kotlin with MVVM architecture and based on best practices by Android docs. The premise is a trivia app that uses old jeopardy questions to test your knowledge!

## Table of Contents

- [RoadMap](https://github.com/Stegnerd/Jeopardy#roadmap)
- [Setup](https://github.com/Stegnerd/Jeopardy#setup)
- [Architecture](https://github.com/Stegnerd/Jeopardy#architecture)
- [Technology](https://github.com/Stegnerd/Jeopardy#technology)
- [Sources](https://github.com/Stegnerd/Jeopardy#sources)

## RoadMap

These are goals for this project:

- [x] Consume remote datasource (external api) with Retrofit
- [x] Set up Circle CI build with Code Cov (current)
- [ ] Setup Firebase crasylytics and Test Lab
- [ ] Unit testing with Mockk and InstrumentedTesting with Robolectric (current)
- [ ] Material Design principles/ A11y

#### v2

- [ ] Add plugins to build: Detekt, Ktlint
- [ ] Add pre-git hook for auto running plugins
- [ ] Push notifications for questions and custom actions on notification (answer question from notification)
- [ ] Upgrade gradle to use Kotlin DSL and buildSrc
- [ ] Break functionality into modular approach
- [ ] Themes and Storing user preferences
- [ ] Room database with user scores and answered questions for analytics

## Setup

### Environment

There is nothing special to this build at the moment.

- Gradle -> 6.1.1 which is latest at this moment.
- Kotlin -> 1.4.10
- Android -> target SDK 30 (Android 10)

to build just clone and click build or from cli: .\gradlew build in root directory of project

## Architecture

This is a MVVM approach to our app. It also uses the concept of on Activity (Main) and navigates between fragments (Question and Category)

Currently there is only one module: App, which contains all content for the app.

## Technology

### Dependencies

- Jetpack:

  - Android KTX - kotlin jetpack
  - Data Binding - binding variable values from view model to ui
  - View Binding - creates instances of view so that you can avoid doing: findViewById
  - LiveData - data that is aware of the lifecycle of fragments and activities
  - Navigation - uses nav graph to simplify app flow

- Coroutines - used for 'async' and to manage background threads safely
- Dagger-hilt - DI replacement for Dagger 2. Simplifies it A LOT
- Moshi - json converter/parser
- Retrofit - http client

### Test Dependencies

- Junit - unit test framework.
- Mockk - alternative to Mockito that was made for Kotlin first

## Sources

- [View Binding](https://developer.android.com/topic/libraries/view-binding) how-to
- [Retrofit](https://square.github.io/retrofit/) how-to
- [Jeopardy api](http://jservice.io/?ref=apilist.fun) Docs for api
- [Moshi Codegen](https://stackoverflow.com/questions/58501918/whats-the-use-of-moshis-kotlin-codegen) Why to include moshi codegen
