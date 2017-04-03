#!/usr/bin/env bash
java -DPORT=2554 \
	 -Dconfig.resource=/master.conf \
	 -jar target/words-node.jar
