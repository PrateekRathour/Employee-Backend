#!/usr/bin/env bash
# Standalone, zero-dependency Maven wrapper runner

set -e

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
MAVEN_VERSION="3.9.9"
WRAPPER_DIR="$DIR/.mvn/wrapper"
MAVEN_HOME="$WRAPPER_DIR/apache-maven-$MAVEN_VERSION"

if [ ! -d "$MAVEN_HOME" ]; then
    echo "Downloading Apache Maven $MAVEN_VERSION..."
    mkdir -p "$WRAPPER_DIR"
    TAR_FILE="$WRAPPER_DIR/apache-maven-$MAVEN_VERSION-bin.tar.gz"
    
    if command -v curl >/dev/null 2>&1; then
        curl -fsSL "https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz" -o "$TAR_FILE"
    elif command -v wget >/dev/null 2>&1; then
        wget -q -O "$TAR_FILE" "https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz"
    else
        echo "Error: curl or wget is required to download Maven."
        exit 1
    fi
    
    tar -xzf "$TAR_FILE" -C "$WRAPPER_DIR"
    rm -f "$TAR_FILE"
    echo "Maven $MAVEN_VERSION installed to $MAVEN_HOME"
fi

exec "$MAVEN_HOME/bin/mvn" "$@"
