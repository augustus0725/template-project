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

# Cross-Site Request Forgery (CSRF)
ҳ������csrf�ֶ�  session �������ﱣ���˷����������ɵ�csrf, �Ա�һ��, ��֤�����ε���ҳ�����޸����ݵ�����(POST/DELETE...)

# cors (������Դ����)
http.cors(c -> {})  // �������������ʵ�����  �� ע�� @CrossOrigin({"exmaple1.com", "exmaple2.com"}) ����һ��

# AuthenticationProvider
������ �Լ�ʵ�ֵ� Provider ����� AuthPermissionManager.authenticate() �����Ϳ�������ʽ����֤
-----
�½�һ�� xxToken (�;����Providerһһ����)
manager.authenticate(xxToken)
----- 
!!! ��ְ֤�ܵ� Filter �� ͨ��Manager ���� authenticate(),   manager����token������ѡprovider, 
ͨ�� auth.authenticationProvider(provider).authenticationProvider(provider) �����providerװ�䵽��֤��ϵ

�̳�OncePerRequestFilter��FilterҲ����Ҫ��ʽע�ᣬhttp.addFilterBefore(Filter01) // OncePerRequestFilter ����ʵ��shouldNotFilter������һЩpath������/login
ʹ��JWT֮��Ͳ���Ҫ��ʹ��csrf

# oauth2

�кö���Ȩ����, �������������Ȩ������
1. ���ʱ�����Դ���ض��� [��Ȩ������] �����¼
2. ��¼��֤���֮��, ��ȡ ����Ȩ�롿, �����ض���ؿͻ���
3. �ͻ��� �� ����Ȩ�롿����Ȩ�������� ��������
4. �� �����ơ� ������Դ access_token

>>> ʹ��oauth2��֤, ���þ�Ҫ�޸�
http.oauth2Login() ----> OAuth2LoginAuthenticationFilter �ᱻ����, ����������֤���е�OAuth2����Ҫ���߼���

ʹ��jwt��Ϊƾ֤��˼·

a. ͨ��password����Ȩ��ʽ��ȡ����Ȩ��������ȡƾ֤
curl -u client:secret -XPOST http://localhost:8081/oauth/token?grant_type=password&username=user01&password=psd01&scope=read

{
	asscess_token, 
	refresh_token
}

b. ���� ��Դ�������� ��access_token��Ч


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

