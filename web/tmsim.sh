#!/bin/bash
docker rmi -f smasher164/tmsim:prod
docker rm -f tmsim
docker run --name="tmsim" -p 8084:8080 smasher164/tmsim:prod
