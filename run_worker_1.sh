#!/usr/bin/env bash
java -DPORT=2555 \
	 -Dconfig.resource=/worker.conf \
	 -jar target/words-node.jar
