### 编译
mvn assembly:assembly

### 服务器运行
/home/vagrant/storm/bin/storm jar storm-trident-1.0-SNAPSHOT-jar-with-dependencies.jar com.sabo.storm.TridentExample word-count