#!/bin/bash

mvn spotless:apply

for dir in *; do
    if [[ -d $dir && $dir == *"-lambda" ]]; then
        echo "Running 'mvn clean package' in $dir"
        cd "$dir" || exit
        mvn clean package
        cd ..
    fi
done
