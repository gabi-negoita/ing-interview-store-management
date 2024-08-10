# Configuration
IMAGE_NAME = ims-service
DOCKER_FILE = Dockerfile
PORT = 9090
DEBUG_PROFILE = debug

# Commands
.PHONY: run debug stop clean

# Run the container normally
run:
	mvn clean package
	docker build -t ims-service .
	docker run --name $(IMAGE_NAME) -d -p $(PORT):8080 $(IMAGE_NAME)

# Run the container in debug mode
debug:
	mvn clean package
	docker build -t ims-service .
	docker run --name $(IMAGE_NAME) -d -p $(PORT):8080 -e SPRING_PROFILES_ACTIVE=$(DEBUG_PROFILE) $(IMAGE_NAME)

stop:
	docker stop $(IMAGE_NAME)
	docker rm $(IMAGE_NAME)

clean:
	docker stop $(IMAGE_NAME)
	docker rm $(IMAGE_NAME)
	docker rmi $(IMAGE_NAME)
