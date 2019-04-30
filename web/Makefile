deploy:
	docker ps -a | awk '{ print $$1,$$2 }' | grep tmsim:prod | awk '{print $$1 }' | xargs -I {} docker rm -f {}
	CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build
	docker build . --tag tmsim:prod | tail -n 2 | head -n 1 | awk '{print $$3}' | xargs -I {} docker tag {} smasher164/tmsim:prod
	docker push smasher164/tmsim:prod
	ssh core@akhil.cc "docker pull smasher164/tmsim:prod && sudo systemctl restart tmsim"

dev:
	go build && ./web --static static --simulator .
