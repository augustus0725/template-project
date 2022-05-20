约定:

1. JPA的目录结构
java
└── org
    └── example
        └── jpa
            ├── entity
            │         ├── User.java
            │         ├── base
            │         │         └── BaseEntity.java
            │         └── projections
            │             └── UserFieldsAge.java
            └── repository
                ├── r
                │         └── UserReadOnlyRepository.java
                └── rw
                    └── UserRepository.java

2. 运行的时候需要指定  spring.profiles.active

3. 配置文件里需要有 driverClassName 参数