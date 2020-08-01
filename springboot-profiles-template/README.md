打好包之后如果需要加入新的配置，比如application-prod.yml
只要把这个文件放在运行目录（比如当前目录，会被放入到classpath）
java -jar xxx.jar --spring.profiles.active=prod