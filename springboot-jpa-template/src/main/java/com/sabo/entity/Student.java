package com.sabo.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    /**
     * error01: ERROR: column "his_father" of relation "student" does not exist
     * to add: spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
     * Hibernate: insert into student (age, firstname, "hisFather", lastname) values (?, ?, ?, ?)
     * pg里不带引号的字段全变成小写，加引号之后会保留大小写
     */
    @Column(name = "\"hisFather\"")
    private String hisFather;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getHisFather() {
        return hisFather;
    }

    public void setHisFather(String hisFather) {
        this.hisFather = hisFather;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("hisFather", hisFather)
                .add("age", age)
                .toString();
    }
}
