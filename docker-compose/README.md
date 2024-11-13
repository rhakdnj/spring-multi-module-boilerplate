## Start all services

_**Because the network is shared, communication between containers is possible.**_

### directory structure

- dev: storage, volume
- etc: config

### util

## Start all services

```shell
$ docker-compose up
```

## Stop all services

```shell
$ docker-compose down
```

### Monitoring

- [prometheus](http://localhost:9090/targets)
- [grafana](http://localhost:9090/targets)

- http_server_requests_seconds_sum{job="simppl-api", uri="/login"} / http_server_requests_seconds_count{job="simppl-api", uri="/login"}
- http_server_requests_seconds_count{job="simppl-api", uri!="/actuator/prometheus"}
- jvm_memory_used_bytes{job="simppl-api", area="heap"}
- process_cpu_usage{job="simppl-api"}
