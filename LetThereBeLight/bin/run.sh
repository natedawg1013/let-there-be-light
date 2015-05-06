#!/bin/bash

OSTYPE=$(uname -m)
DIRNAME="./lib/Linux/$OSTYPE-unknown-linux-gnu"
java -Djava.library.path=$DIRNAME -jar 'Let There Be Light.jar'
