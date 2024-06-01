> This image includes `Supervisor` for convenient management (background running + foreground interaction, automatic restart on failure), and packages common management scripts.

> PS: The Docker image is automatically updated using `GitHub Actions`. You can choose a specific version or select the latest development version (tag: `{last_release}-beta`). [Click here to view](https://github.com/RW-HPS/RW-HPS/pkgs/container/rw-hps).

## Docker

> Run in the background

```bash
docker run --name rw-hps -d -p 5123:5123 -v ~/rw-hps-data:/app/data ghcr.io/rw-hps/rw-hps
```

> Enter the container

```bash
docker exec -it rw-hps bash
```

> Check the current status to see if it is running

```bash
root@5616fb973882:/app# ./status.sh
rw-hps                           RUNNING   pid 9, uptime 0:26:01
```

> Enter foreground interaction / input RW-HPS commands

```bash
./connect.sh
```

> Restart the rw-hps service

```bash
./restart.sh
```

## Docker Compose

```yml
version: '3.4'

services:
  rw-hps:
    image: ghcr.io/rw-hps/rw-hps
    container_name: rw-hps
    ports:
      - "5123:5123"
    restart: always
    environment:
      - TZ=Asia/Shanghai
    volumes:
      - ./data:/app/data
    privileged: true
    user: root
```

> Run in the background

```bash
docker-compose up -d
```

> Other commands are the same as for Docker