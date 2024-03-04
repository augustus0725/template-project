docker volume create --name sonarqube_data
docker volume create --name sonarqube_logs
docker volume create --name sonarqube_extensions

docker run -d --name sonarqube \
   --restart=always \
   -p 8000:9000 \
   -e SONAR_JDBC_URL=jdbc:postgresql://192.168.0.xx/sonar \
   -e SONAR_JDBC_USERNAME=pgadmin \
   -e SONAR_JDBC_PASSWORD=xxxxx \
   -v sonarqube_data:/home/sonar/data \
   -v sonarqube_extensions:/home/sonar/extensions \
   -v sonarqube_logs:/home/sonar/logs \
   sonarqube:8.6.1
#  配置 maven setting
       <profile>
            <id>sonar</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <!-- Optional URL to server. Default value is http://localhost:9000 -->
                <sonar.host.url>
                    http://192.168.0.185:8000
                </sonar.host.url>
            </properties>
        </profile>
# 配置maven 项目
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>${jacoco.version}</version>
                <executions>
                    <execution>
                        <id>default-prepare-agent</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>default-report</id>
                        <goals>
                            <goal>report</goal>
                        </goals>
                        <phase>test</phase>
                    </execution>
                </executions>
            </plugin>
# 构建的时候
mvn $MAVEN_CLI_OPTS package -DskipTests -Dsonar.login=xxxxxxxxxxxxxxxx -s ci_settings.xml
# 上面的token  生成方式
# 【Administration】 -> 【Security】-> [tokens] == generate
xxxxxxxxxxxxxxxx