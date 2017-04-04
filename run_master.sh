#!/usr/bin/env bash
if [[ ! -f "words-node.jar" ]]
then
   cp target/**/words-node.jar .
fi

java -DPORT=2554 \
	 -Dconfig.resource=/master.conf \
	 -jar words-node.jar
