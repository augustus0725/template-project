1. 创建好webservice
2. 启动webservice
3. 生成客户端能用的代码
   wsimport -s . http://127.0.0.1:9999/helloworld?wsdl
4. 新建client项目，导入上面生成的java代码
   创建HelloWorld的实例