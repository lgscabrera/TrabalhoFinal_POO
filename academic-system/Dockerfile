# TUS-2381 — Criação do Dockerfile para build interno (Maven) e execução
# interativa via CLI
#
# Multi-stage build: stage 1 compiles the project with Maven (so the final
# image doesn't ship the JDK + Maven + build cache), stage 2 just runs the
# resulting jar on a slim JRE.
#
# The app is a command-line menu (keyboard input), so it must be run with
# `docker run -it` — without -it/-t there's no TTY for the menu to read
# from and the container will just hang or exit immediately.

# ---- Stage 1: build -------------------------------------------------------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /build

# Copy the POM first and pre-fetch dependencies so this layer is cached
# across builds as long as pom.xml doesn't change.
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Stage 2: run (interactive CLI) ---------------------------------------
FROM eclipse-temurin:25-jre-alpine AS runtime
WORKDIR /app

# Runs as a non-root user inside the container.
RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=build /build/target/academic-system.jar app.jar

# `-it` is required at `docker run` time for the interactive CLI menu.
ENTRYPOINT ["java", "-jar", "app.jar"]
