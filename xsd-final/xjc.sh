#!/bin/bash
export LANG="en_US.UTF-8"
export LANGUAGE="en_US:"
xjc -p com.soapboxrace.jaxb.http -npa -no-header \
   Main.xsd

cp -r com ../src/main/java
rm -rf com
