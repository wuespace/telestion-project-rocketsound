# Telestion Project Application

The application part of a Telestion Project.

## Prerequisites

You will need Java 16 and Docker with `docker-compose` set up and ready to go.
A text editor or IDE with auto-completion and gradle support is beneficial.

## Setup

To fetch the dependencies locally you need a Github personal access token with `packages:read` rights.
Either add your github user name and the token as `GITHUB_ACTOR` and `GITHUB_TOKEN` to your environment variables
or make a copy of the `gradle.properties.example` and name it `gradle.properties`.
Then insert your username and token into this file.
No worries, this file should not be committed. (it's git ignored)

Finally, resync gradle to download and set up the Telestion Core dependencies.

## Usage and Testing

### Native

Start the application with gradle:

```shell
./gradlew run
```

or:

```
.\gradle.bat run
```

### In Docker

If you want to test your changes directly in docker, you can package it and run it.

1. Please shutdown any running containers and services:

```
docker-compose --profile prod down
```

2. Create a distribution build with gradle:

```shell
./gradlew assembleDist
```

or:

```
.\gradle.bat assembleDist
```

3. Build the local image with `docker-compose`:

```
docker-compose --profile devel-docker build
```

4. And create and start the containers:

```
docker-compose --profile devel-docker up -d
```

That's it!

### Production mode

If you want to run the application in production mode using the latest release of the project, then you simply need to call:

```
docker-compose --profile prod up -d
```

to pull and start the required docker containers.

> Attention:
>
> Your local `config.json` will be used to configure the started application!

## Project Structure

The application structure looks like:

```
.
├── .gradle (gradle specific setup; should not be commited)
├── build (build files containing meta-information and binaries from the build process)
├── conf (application configurations)
│   └── config.json (the main configuration file the application uses to start the Vert.x verticles)
├── src (the source files of the application)
├── build.gradle (the "main" configuration file for gradle)
├── docker-compose.yml (the definition how to set up and connect the different docker containers and profiles)
├── Dockerfile (the definition to successfully build a Docker image from the compiled Application sources)
├── gradle.properties (gradle configuration containing your personal access token)
└── README.md (you're here :P)
```

## Learn More

To get started with the application development, please take a look at the Vert.x documentation which is "the" library in Telestion:

https://vertx.io/docs/

Also take a look at the Telestion Core packages containing different helpers and services that (hopefully) make the development much easier:

https://github.com/wuespace/telestion-core

If you need some inspiration "how" to write your first verticle, you can look at other verticles source code in this project.
