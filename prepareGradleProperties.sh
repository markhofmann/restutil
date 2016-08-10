#!/usr/bin/env bash

GRADLE_PROPERTIES=$HOME"/.gradle/gradle.properties"
export GRADLE_PROPERTIES
echo "Gradle Properties should exist at $GRADLE_PROPERTIES"

if [ ! -f "$GRADLE_PROPERTIES" ]; then
    echo "Gradle Properties does not exist"
    echo "Creating $HOME/.gradle directory"
    mkdir -pv $HOME/.gradle
    echo "Creating $GRADLE_PROPERTIES"
    touch $GRADLE_PROPERTIES

    echo "Writing AWS keys to gradle.properties..."
    echo "AWS_ACCESS_KEY=$AWS_ACCESS_KEY" >> $GRADLE_PROPERTIES
    echo "AWS_SECRET_KEY=$AWS_SECRET_KEY" >> $GRADLE_PROPERTIES
fi
