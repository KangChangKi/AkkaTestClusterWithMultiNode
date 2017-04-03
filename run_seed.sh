#!/usr/bin/env bash
java -DPORT=2551 \
	 -Dconfig.resource=/seed.conf \
	 -jar target/words-node.jar
