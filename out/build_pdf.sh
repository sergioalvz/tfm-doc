#!/bin/bash

MY_PATH="`( cd \"$MY_PATH\" && pwd )`"

fopub -t $MY_PATH/docbook-xsl main.xml
