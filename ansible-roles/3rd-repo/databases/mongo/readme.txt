mongodb
!! 集群内部是用keyfile信任, 先构建keyfile
openssl rand -base64 768 > keyfile.txt


!! 容器启几个实例
docker run -v /opt/app/mongodb:/opt --name mongodb01 -d -p 27017:27017 --restart=on-failure:6 --privileged --cpus 4 --memory 8192m -e "TZ=Asia/Shanghai" -e "LANG=en_US.UTF-8"  -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=123456 mongo --replSet "rs01" --keyFile /opt/keyfile.txt
docker run -v /opt/app/mongodb:/opt --name mongodb02 -d -p 27018:27017 --restart=on-failure:6 --privileged --cpus 4 --memory 8192m -e "TZ=Asia/Shanghai" -e "LANG=en_US.UTF-8"  -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=123456 mongo --replSet "rs01" --keyFile /opt/keyfile.txt
docker run -v /opt/app/mongodb:/opt --name mongodb03 -d -p 27019:27017 --restart=on-failure:6 --privileged --cpus 4 --memory 8192m -e "TZ=Asia/Shanghai" -e "LANG=en_US.UTF-8"  -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=123456 mongo --replSet "rs01" --keyFile /opt/keyfile.txt

!! 配置集群
mongosh -u root -p 123456

>> config = {
  	"_id" : "rs01",
  	"members" : [
  		{
  			"_id" : 0,
  			"host" : "192.168.0.181:27017"
  		},
  		{
  			"_id" : 1,
  			"host" : "192.168.0.181:27018"
  		},
  		{
  			"_id" : 2,
  			"host" : "192.168.0.181:27019"
  		}
  	]
  }

>> rs.initiate(config)
// 查看状态
>> rs.status()