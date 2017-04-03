#!/usr/bin/env bash
java -DPORT=2556 \
	 -Dconfig.resource=/worker.conf \
	 -jar target/words-node.jar
