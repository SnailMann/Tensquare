## tensquare-user

用户模块微服务

### 注意

- 关于消息中间件的队列需要人为在MQ后台创建
- 仅仅使用Spring Security的BCrypt加密功能，对密码进行加密，认证鉴权方面，我们使用JWT去实现

### 要做的事情

- 用户分普通用户和管理员用户
- 用户可以被关注
- 使用短信来接收验证码
- 用户注册



### 技术实现

#### Spring Security

- 因为只使用Security进行加密，所以我们需要覆盖默认拦截配置，开放所有路径，不拦截，关闭csrf拦截
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * authorizeRequests是Security权限认证注解配置的开端，表明开启权限及说明执行什么路径所需的权限
         * 权限认证分为两个部分：
         * (1) 第一部分是，要拦截的路径
         * (2) 第二部分是，执行拦截路径所需要的是什么权限
         * 我们这里仅仅是想用Spring Security对密码进行加密，而不是使用Security进行认证和鉴权，所以前提就是要先让
         * Security对所有的路径保持开发，并且关闭csrf功能，允许跨域
         */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll() //所有路径，全允许通过，不需要权限
                .anyRequest().authenticated() //代表任何请求，都需要认证后，才能访问，既需要登录认证
                .and().csrf().disable();
    }
}
```

- 对密码进行BCrypt算法加密
```java
    /**
     * 用于对密码进行BCrypt算法加密
     *
     * @return
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
```
对同样的密码进行加密，最终的结果是不同的，因为salt也是随机的

- 对密码进行加密
```java
String password = bCryptPasswordEncoder.encode(user.getPassword());
```
- 密码原文和密文的匹配
```java
if (bCryptPasswordEncoder.matches(password,userdb.getPassword())){
    return userdb;
}
```


#### JWT权限认证

- 采用JJWT开源库进行JWT的签发和认证,引入Maven库

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
</dependency>
```

- 实用JJWT签发生成一个JWT字符串
```java
/**
 * 简单实用JJWT工具，签发JWT字符串
 * output : 
 * eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqdGkiOiI2NjYiLCJzdWIiOiJTbmFpbE1hbm4iLCJpYXQiOjE1NjUxMDE3NDl9.pHpqToaxie9mtJM775AaL5j_p_mKoIC9XtY4NJ59_bg
 * {"type":"jwt","alg":"HS256"}.{"jti":"666","sub":"SnailMann","iat":1565101749}.xxxxxxxx...
 */
public class CreateJwt {

    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666") //jti : JWT ID
                .setHeaderParam("type","jwt") // 头部参数，自定义
                .setSubject("SnailMann") // sub : 用户名
                .setIssuedAt(new Date()) // jat: 签发时间
                .signWith(SignatureAlgorithm.HS256,"tensquare");
        System.out.println(jwtBuilder.compact());

    }
}
```

- 对JWT Token进行解析验证
```java

/**
* JWT Token解析
*/
public class ParseJwt {

    public static void main(String[] args) {
        Claims claims = Jwts.parser( )
                .setSigningKey("tensquare") // 服务端设置盐, 下面是JWT完整字符串
                .parseClaimsJws("eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqdGkiOiI2NjYiLCJzdWIiOiJTbmFpbE1hbm4iLCJpYXQiOjE1NjUxMDIzMjh9.FBuviL-iEarBboNWTD6lg_XwMhDq5Q5-CQX8pBDw9OE")
                .getBody(); //获得Body
        System.out.println(claims);
        System.out.println("用户ID/JWT ID: " + claims.getId());
        System.out.println("用户名: " + claims.getSubject());
        System.out.println("登录时间/签发时间: " +claims.getIssuedAt());

    }
}
```


### 功能实现

#### 用户登录，成功返回Jwt

- 用户登录成功后，可以通过response的body返回，也可以通过header返回
- 前端获取token, 存储到local store中

#### 用户登录后执行需鉴权的请求

- 前端从local store取出token, 跟随请求发送，一般通过requset 的header去携带token
- 我们这里前后端的约定是，前端请求微服务需要添加头信息Authorization,内容为Bearer+空格+token
- 后端接收到请求后，从request中取出token, 对token进行验证


#### 用户A关注用户B

- 用户A关注了用户B，由Friend模块远程调用User模块，先更新UserA的关注数+1，在更新UserB的粉丝数+1
- 用户A取关了用户B，由Friend模块远程调用User模块，先更新UserA的关注数-1，再更新UserB的粉丝数-1
- User操作是由本地事务控制

 