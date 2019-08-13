## tensquare common project

十次方公共包


### Utils

- IdWorker 雪花算法ID生成器
- JwtUtil JWT签发验证工具类



### 功能

#### FeignInvokeInterceptor


- 既浏览器调用第一个应用，第一个上游服务A可以获取到token，如果服务A还要调用服务B，那么服务B就无法拿到Token了。
- 通过实现RequestInterceptor接口，即可允许通过Feign调用，将Token从上有传递到下游服务
- 功能实际就是一个代理，在Feign调用时，将当前服务的token设置到底层的RestTemplate中，带给下游


#### TokenContext

- 使用ThreadLocal存储token，既为了避免每次获取token，都从requst中获取，我们就可以使用一个ThreadLocal来存储这个token。比较有的场景注入requst是比较违和的


#### TokenInterceptor

- 在服务每次被调用的时候，自动从request获取token信息，存储起来，以备不时之需
- 在恰当的场景，还可以配合FeignInvokeInterceptor，取出token, 设置header


