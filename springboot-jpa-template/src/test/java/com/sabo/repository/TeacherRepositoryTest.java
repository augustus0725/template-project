package com.sabo.repository;

import com.sabo.Application;
import com.sabo.entity.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/7/28 15:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, properties="spring.main.banner-mode=off")
public class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void testSave() {
        teacherRepository.save(Teacher.builder().id(1).name("Kafka").build());
    }

}