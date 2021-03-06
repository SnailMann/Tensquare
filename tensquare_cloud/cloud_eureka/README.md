## Cloud-Eureka

### 功能

#### 集群配置


```yaml
eureka:
  client:
    fetch-registry: true # Eureka显示自己的信息
    register-with-eureka: true # Eureka也注册到Eureka上
    registry-fetch-interval-seconds: 10 # 当前eureka client每隔10s就去注册中心获取一次最新注册资讯，默认30s
    serviceUrl:
      defaultZone: http://127.0.0.1:6868/eureka/,http://127.0.0.1:6869/eureka/,http://127.0.0.1:6870/eureka/
  instance:
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
    ip-address: ${spring.cloud.client.ip-address}
    lease-expiration-duration-in-seconds: 10 # 超过10s，没有收到客户端的心跳，就将其除去，默认90s
    lease-renewal-interval-in-seconds: 5 # 客户端每隔5秒先服务端发送一次心跳
    prefer-ip-address: true # 使用IP代替主机名在注册中心做唯一标识
    metadata-map:
      version: ${info.version}
```