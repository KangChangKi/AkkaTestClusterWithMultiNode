#!/usr/bin/env bash
if [[ ! -f "persistence-examples.jar" ]]
then
   cp target/**/persistence-examples.jar .   
fi

java -DPORT=2552 \
	 -jar persistence-examples.jar
