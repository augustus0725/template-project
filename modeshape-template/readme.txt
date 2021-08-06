ModeShape is a hierarchical, transactional and consistent data store with support for queries, full-text search, events, 
versioning, references, and flexible and dynamic schemas. It is 100% open source and written in Java. 
Clients use the JSR-283 standard Java API for content repositories (aka, JCR) or ModeShape's REST API, and can query content 
through JDBC and SQL.

Features
All data is organized in a hierarchical tree-like structure of nodes, single- and multi-valued properties, and children.
Data can be persisted either on the file system or in databases.
Implements the JSR-283 standard Java API for content repositories (aka, JCR 2.0)
Define a schema with node types and mixins that (optionally) limit the properties and children for various kinds of nodes, and evolve the schema over time without having to migrate the data.
Use multiple query languages, including SQL-like, XPath, and full-text search languages to find data.
Use sessions to create and validate large amounts of content transiently, and then save all changes with one call.
ModeShape can be configured to use and participate in JTA transactions. See the JCR 2.0 (JSR-283) specification to learn how to use the JTA and JCR APIs together.
Register to be notified with events when data is changed anywhere in the cluster, optionally filtered by custom criteria.
Segregate data into multiple repositories and workspaces.
Embed ModeShape into your Java SE, EE, or web applications.
Install into JBoss AS and applications to centrally configure, manage, and monitor repositories.

modeshape是一种树形的存储结构。
适合：
- 查询
- 全文检索
- 描述事件
- 版本数据管理
- 动态的元数据

提供了jcr的api

jcr是标准的内容管理API，为了统一不同cms（内容管理系统）接口。

# 配置

{
    "name" : "repo",
    # 存放的位置, 可以是内存/文件/数据库
    "storage" : {
            "persistence": {
                "type": "mem"
            }
    }
}

    "storage" : {
        "persistence": {
            "type": "file",
            "path" : "target/test_repository"
        },
    }

    "storage" : {
      "persistence" : {
            "type" : "db",
            "connectionUrl": "jdbc:h2:file:./target/test_repo/db;AUTO_SERVER=TRUE"
        }
    }