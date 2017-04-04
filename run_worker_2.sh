#!/usr/bin/env bash
if [[ ! -f "words-node.jar" ]]
then
   cp target/**/words-node.jar .
fi

java -DPORT=2556 \
	 -Dconfig.resource=/worker.conf \
	 -jar words-node.jar
