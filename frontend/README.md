# Run frontend service into docker

```bash
docker run -d -p 8098:80 -p 8099:81 -name frontend skillboxteam32/front:latest
```

## Project setup

```
yarn install
```

### Compiles and hot-reloads for development

```
yarn serve
```

### Compiles and minifies for production

```
yarn build
```

### Lints and fixes files

```
yarn lint
```

### Customize configuration

See [Configuration Reference](https://cli.vuejs.org/config/).

### Docker Build
```
docker build -t skillgroup32/frontend .
```

### Docker push
```
docker push skillgroup32/frontend:latest

```### Docker stop and rm
```
### Docker stop and rm
```
docker stop frontend && docker rm frontend
```
### Docker stop and rm and start
```
docker stop frontend && docker rm frontend && docker run -d -p 8098:80 -p 8099:81 -name frontend skillboxteam32/front:latestt
```
### Docker sh
```
#!/bin/bash
echo "Begin restart front";
echo "Stop docker front";
docker stop frontend;
echo "rm docker front";
docker rm frontend;
echo "Pull image";
docker image pull skillgroup32/frontend:latest;
echo "Start new docker front";
docker run -d -p 8098:80 -p 8099:81 -name frontend skillboxteam32/front:latestt
```
