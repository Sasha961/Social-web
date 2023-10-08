# Run frontend service into docker

```bash
docker run -d -p 8098:80 -p 8099:81 --name frontend temo4kalebedev/front_soc:latest
```

```bash
docker run -d -p 8098:80 -p 8099:81 --name frontend front_soc:latest
```

```bash
docker stop frontend && docker rm frontend && docker image rm front_soc && docker build -t front_soc . && docker run -d -p 8098:80 -p 8099:81 --name frontend front_soc:latest
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
```bash
docker build -t front_soc .
```

### Docker TAG
```bash
docker tag front_soc temo4kalebedev/front_soc:latest
```

### Docker push
```bash
docker push temo4kalebedev/front_soc:latest
```
### Docker stop and rm
```
docker stop frontend && docker rm frontend
```
### Docker stop and rm and start
```
docker stop frontend && docker rm frontend && docker run -d -p 8098:80 -p 8099:81 -name frontend psmove/front_soc:latestt
```
### Docker sh
```bash
#!/bin/bash
echo "Begin restart front";
echo "Stop docker front";
docker stop frontend;
echo "rm docker front";
docker rm frontend;
echo "Pull image";
docker image pull psmove/front_soc:latest;
echo "Start new docker front";
docker run -d -p 8098:80 -p 8099:81 -name frontend psmove/front_soc:latestt
```
