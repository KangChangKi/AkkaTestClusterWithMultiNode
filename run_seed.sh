#!/usr/bin/env bash
if [[ ! -f "words-node.jar" ]]
then
   cp target/**/words-node.jar .
fi

java -DPORT=2551 \
	 -Dconfig.resource=/seed.conf \
	 -jar words-node.jar
