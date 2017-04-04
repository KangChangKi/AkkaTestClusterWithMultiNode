#!/usr/bin/env bash
mv target/**/persistence-examples.jar .
java -DPORT=2552 \
	 -jar persistence-examples.jar
