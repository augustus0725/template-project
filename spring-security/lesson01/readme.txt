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

