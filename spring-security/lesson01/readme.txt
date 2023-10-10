# �û�
Spring Security��������ʾ�û�����UserDetails
�и�ʵ�� User

# PasswordEncoder
web/client -> service  ��������Ǵ�����password   (����������� �����ʹ��https, ��ȫ��ͨ��)
service save password ������
service read            ����
�Ƽ�: BCryptPasswordEncoder

 * String idForEncode = "bcrypt";
 * Map<String,PasswordEncoder> encoders = new HashMap<>();
 * encoders.put(idForEncode, new BCryptPasswordEncoder());
 * encoders.put("noop", NoOpPasswordEncoder.getInstance());
 * encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
 * encoders.put("scrypt", new SCryptPasswordEncoder());
 * encoders.put("sha256", new StandardPasswordEncoder());
 *
 * PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
 
DelegatingPasswordEncoder ����������ǰ�Ӹ�ǰ׺,ǰ׺���㷨����

# ��ȫ������ (SecurityContextHolder �����Ĺ�����)
��֤���֮��, һ��ʱ��Ͳ�Ӧ������Ҫ��֤, �����Ҫһ��������, �洢��֤����Ϣ
����:
1. �����ڴ��� �Ƕ�ʵ���������������
֪ʶ:
SecurityContextHolder ʹ����3�ֲ���������������
MODE_THREADLOCAL
MODE_INHERITABLETHREADLOCAL : �첽��ʱ�������ĸ��Ƶ���һ���߳�
MODE_GLOBAL �����߳̿�����ͬ��������  ==> �����֪ʶ��ûɶ��, ��������״


# ˼·
username ͨ�� UserDetailsService �����ҵ� UserDetails ����
UserDetailsService ���������ݴ��� Ldap/RDB/...

����Щ��
------------------------------
UserDetails ����Ҫ��һЩ�ֶ�
�Զ���ҵ������Ҫ��һЩ�ֶ�              ====>  ��Ҫһ��JPA entity һһ��Ӧ
------------------------------

���ʵ��:

@Entity  
class User {  // ����JPAְ��
}

class SecurityUser implements UserDetails { /* Spring Security ְ�� */
    private final User user;
	
	public SecurityUser(User user) {
		this.user = user;
	}
	
	// overrides
}



ʵ��UserDetailsManager ����user����Ĺ��ܸ�������  �����Ÿ�������ʵ�֣�
!! ����jpa, �ǿ��Լ�ʵ�� JdbcUserDetailsManager  ��Ϊ�û������ @Service ʵ��, ��ҪԤ�Ƚ��������ļ�����
JdbcUserDetailsManager
-> UserDetailsManager
-> GroupManager

����Լ�ȥʵ�����Manager, ����UsersRepository  AuthritiesRepository GroupsRepository
����ʵ�ֵ�ʱ�����ȥ���� JdbcUserDetailsManager

