### 产生一个keystore

```
darwin@darwin-pc:/mnt/d/tmp/ssl$ keytool -genkeypair -alias baeldung -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore baeldung.p12 -validity 3650
Enter keystore password: 123456
Re-enter new password: 123456
What is your first and last name?
  [Unknown]:  sabo zhang
What is the name of your organizational unit?
  [Unknown]:  github
What is the name of your organization?
  [Unknown]:  github
What is the name of your City or Locality?
  [Unknown]:  nj
What is the name of your State or Province?
  [Unknown]:  js
What is the two-letter country code for this unit?
  [Unknown]:  cn
Is CN=sabo zhang, OU=github, O=github, L=nj, ST=js, C=cn correct?
  [no]:  yes
```

### 配置springboot

```
# 配置application.properties 就可以

# The format used for the keystore. It could be set to JKS in case it is a JKS file
server.ssl.key-store-type=PKCS12
# The path to the keystore containing the certificate
server.ssl.key-store=classpath:keystore/baeldung.p12
# The password used to generate the certificate
server.ssl.key-store-password=sabo_001
# The alias mapped to the certificate
server.ssl.key-alias=baeldung
server.ssl.enabled=true
```

