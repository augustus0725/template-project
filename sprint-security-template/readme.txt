SecurityContextHolder, to provide access to the SecurityContext.
SecurityContext, to hold the Authentication and possibly request-specific security information.
Authentication, to represent the principal in a Spring Security-specific manner.
GrantedAuthority, to reflect the application-wide permissions granted to a principal.
UserDetails, to provide the necessary information to build an Authentication object from your application’s DAOs or other source of security data.
UserDetailsService, to create a UserDetails when passed in a String-based username (or certificate ID or the like).


# 认证流程
You visit the home page, and click on a link.

A request goes to the server, and the server decides that you’ve asked for a protected resource.

As you’re not presently authenticated, the server sends back a response indicating that you must authenticate. The response will either be an HTTP response code, or a redirect to a particular web page.

Depending on the authentication mechanism, your browser will either redirect to the specific web page so that you can fill out the form, or the browser will somehow retrieve your identity (via a BASIC authentication dialogue box, a cookie, a X.509 certificate etc.).

The browser will send back a response to the server. This will either be an HTTP POST containing the contents of the form that you filled out, or an HTTP header containing your authentication details.

Next the server will decide whether or not the presented credentials are valid. If they’re valid, the next step will happen. If they’re invalid, usually your browser will be asked to try again (so you return to step two above).

The original request that you made to cause the authentication process will be retried. Hopefully you’ve authenticated with sufficient granted authorities to access the protected resource. If you have sufficient access, the request will be successful. Otherwise, you’ll receive back an HTTP error code 403, which means "forbidden".


Spring Security has distinct classes responsible for most of the steps described above. The main participants (in the order that they are used) are the ExceptionTranslationFilter, an AuthenticationEntryPoint and an "authentication mechanism", which is responsible for calling the AuthenticationManager which we saw in the previous section.

- ExceptionTranslationFilter
  监控Spring Security产生的异常，比如认证产生的异常。
  -> returning error code 403  如果用户已经认证了
  -> Launching an AuthenticationEntryPoint 还没有认证，开始认证流程

- AuthenticationEntryPoint
  开始认证流程
- Authentication Mechanism
Once your browser submits your authentication credentials (either as an HTTP form post or HTTP header) there needs to be something on the server that "collects" these authentication details. By now we’re at step six in the above list. In Spring Security we have a special name for the function of collecting authentication details from a user agent (usually a web browser), referring to it as the "authentication mechanism". Examples are form-base login and Basic authentication. Once the authentication details have been collected from the user agent, an Authentication "request" object is built and then presented to the AuthenticationManager.

After the authentication mechanism receives back the fully-populated Authentication object, it will deem the request valid, put the Authentication into the SecurityContextHolder, and cause the original request to be retried (step seven above). If, on the other hand, the AuthenticationManager rejected the request, the authentication mechanism will ask the user agent to retry (step two above).

- Storing the SecurityContext between requests
Depending on the type of application, there may need to be a strategy in place to store the security context between user operations. In a typical web application, a user logs in once and is subsequently identified by their session Id. The server caches the principal information for the duration session. In Spring Security, the responsibility for storing the SecurityContext between requests falls to the SecurityContextPersistenceFilter, which by default stores the context as an HttpSession attribute between HTTP requests. It restores the context to the SecurityContextHolder for each request and, crucially, clears the SecurityContextHolder when the request completes. You shouldn’t interact directly with the HttpSession for security purposes. There is simply no justification for doing so - always use the SecurityContextHolder instead. 

Many other types of application (for example, a stateless RESTful web service) do not use HTTP sessions and will re-authenticate on every request. However, it is still important that the SecurityContextPersistenceFilter is included in the chain to make sure that the SecurityContextHolder is cleared after each request. 

# 鉴权
- AccessDecisionManager


#认证 AuthenticationManager
## 管理了具体的ProviderManager(管理了具体的Provider)

CasAuthenticationProvider




# 一些细节
程序启动的时候, 过滤器chain会显示在日志里 o.s.s.web.DefaultSecurityFilterChain

# 几个重要的过滤器
SecurityContextPersistenceFilter
-> 默认实现的时候会从session里查找有没有 securitycontext, 有的话把上下文保存到
SecurityContextHolder.setContext(contextBeforeChainExecution);
-> 第一次会保存一个空的上下文, 基于session, 所以无状态的api是无效的
-> 继承一下这个类MySecurityContextPersistenceFilter，看会不会取代当前的filter ? 不会
-> http.addFilter(new MySecurityContextPersistenceFilter());  只是在SecurityContextPersistenceFilter前面注册了自定义的Filter
   改了HttpSecurity的默认设置  这个时候也没有指定 AuthenticationProvider

-> 在 configure(final AuthenticationManagerBuilder auth) 里面配置AuthenticationManager
   可以决定用户的存储 以及 加密方式


  
# 使用curl做的一些测试
curl -v -u user:b05dc999-da1d-42ee-bd88-92e2a190708d http://127.0.0.1:8080/hello
