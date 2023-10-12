# 用户
Spring Security里用来表示用户的是UserDetails
有个实现 User

# PasswordEncoder
web/client -> service  这个过程是传明文password   (所以这个过程 最好是使用https, 安全的通道)
service save password 是密文
service read            密文
推荐: BCryptPasswordEncoder

 * String idForEncode = "bcrypt";
 * Map<String,PasswordEncoder> encoders = new HashMap<>();
 * encoders.put(idForEncode, new BCryptPasswordEncoder());
 * encoders.put("noop", NoOpPasswordEncoder.getInstance());
 * encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
 * encoders.put("scrypt", new SCryptPasswordEncoder());
 * encoders.put("sha256", new StandardPasswordEncoder());
 *
 * PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
 
DelegatingPasswordEncoder 可以在密文前加个前缀,前缀是算法名称

# 安全上下文 (SecurityContextHolder 上下文管理器)
认证完成之后, 一段时间就不应该再需要认证, 这就需要一个上下文, 存储认证的信息
问题:
1. 存在内存里 那多实例的情况就有问题
知识:
SecurityContextHolder 使用了3种策略来管理上下文
MODE_THREADLOCAL
MODE_INHERITABLETHREADLOCAL : 异步的时候将上下文复制到下一个线程
MODE_GLOBAL 所有线程看到相同的上下文  ==> 这里的知识都没啥用, 不符合现状

# Cross-Site Request Forgery (CSRF)
页面里有csrf字段  session 上下文里保存了服务器里生成的csrf, 对比一下, 保证是信任的网页发的修改数据的请求(POST/DELETE...)

# cors (跨域资源共享)
http.cors(c -> {})  // 配置允许跨域访问的域名  和 注解 @CrossOrigin({"exmaple1.com", "exmaple2.com"}) 功能一致

# AuthenticationProvider
可以在 自己实现的 Provider 里调用 AuthPermissionManager.authenticate() 这样就可以做链式的认证
-----
新建一个 xxToken (和具体的Provider一一关联)
manager.authenticate(xxToken)
----- 
!!! 认证职能的 Filter 里 通过Manager 调用 authenticate(),   manager根据token类型来选provider, 
通过 auth.authenticationProvider(provider).authenticationProvider(provider) 将多个provider装配到认证体系

继承OncePerRequestFilter的Filter也还是要显式注册，http.addFilterBefore(Filter01) // OncePerRequestFilter 可以实现shouldNotFilter来跳过一些path，比如/login
使用JWT之后就不需要再使用csrf

# oauth2

有好多授权类型, 下面的流程是授权码类型
1. 访问保护资源被重定向到 [授权服务器] 比如登录
2. 登录验证完成之后, 获取 【授权码】, 并被重定向回客户端
3. 客户端 用 【授权码】向【授权服务器】 申请令牌
4. 用 【令牌】 访问资源 access_token

>>> 使用oauth2认证, 配置就要修改
http.oauth2Login() ----> OAuth2LoginAuthenticationFilter 会被配置, 拦截请求验证其中的OAuth2中需要的逻辑。

使用jwt作为凭证的思路

a. 通过password的授权方式获取从授权服务器获取凭证
curl -u client:secret -XPOST http://localhost:8081/oauth/token?grant_type=password&username=user01&password=psd01&scope=read

{
	asscess_token, 
	refresh_token
}

b. 配置 资源服务器， 让access_token生效


# 思路
username 通过 UserDetailsService 可以找到 UserDetails 对象
UserDetailsService 依赖的数据存在 Ldap/RDB/...

存哪些？
------------------------------
UserDetails 里需要的一些字段
自定义业务里需要的一些字段              ====>  需要一个JPA entity 一一对应
------------------------------

最佳实践:

@Entity  
class User {  // 承载JPA职能
}

class SecurityUser implements UserDetails { /* Spring Security 职能 */
    private final User user;
	
	public SecurityUser(User user) {
		this.user = user;
	}
	
	// overrides
}



实现UserDetailsManager 这样user管理的功能更加完善  （开放给第三方实现）
!! 不用jpa, 是可以简单实现 JdbcUserDetailsManager  作为用户管理的 @Service 实现, 需要预先建好依赖的几个表
JdbcUserDetailsManager
-> UserDetailsManager
-> GroupManager

最好自己去实现这个Manager, 依赖UsersRepository  AuthritiesRepository GroupsRepository
具体实现的时候可以去参照 JdbcUserDetailsManager

