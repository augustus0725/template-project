package com.sabo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
@Entity
@Table(name = "student")
@Data
public class Student {
    @Id
    // GenerationType.SEQUENCE 使用sequence
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "db_source_id_seq")
    @SequenceGenerator(name="db_source_id_seq", allocationSize = 1)
    // 支持 auto increment的库
    // @GenerationType.IDENTITY
    private long id;
    private String firstname;
    private String lastname;
    /**
     * error01: ERROR: column "his_father" of relation "student" does not exist
     * to add: spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
     * Hibernate: insert into student (age, firstname, "hisFather", lastname) values (?, ?, ?, ?)
     * pg里不带引号的字段全变成小写，加引号之后会保留大小写
     */
//    @Column(name = "\"hisFather\"")
    @Column(name = "his_father")
    private String hisFather;
    private int age;
}
/**
 * JPA的主键生成策略
 *
 * GenerationType.TABLE
 * GenerationType.SEQUENCE
 * GenerationType.IDENTITY
 * GenerationType.AUTO
 */
