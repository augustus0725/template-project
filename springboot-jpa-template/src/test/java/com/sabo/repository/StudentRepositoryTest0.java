package com.sabo.repository;

import com.sabo.config.JpaConfiguration;
import com.sabo.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class) // junit 4 需要, junit 5不需要
@DataJpaTest(properties = "spring.main.banner-mode=off")
// 下面的配置注释掉之后就会用嵌入的h2数据库
 @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 不使用嵌入的测试库，用真实的库
 @Import(JpaConfiguration.class) // 不使用嵌入的测试库，用真实的库的时候就需要导入这个配置
public class StudentRepositoryTest0 {
    @Autowired private StudentRepository studentRepository;

    @Test
    public void testSave() {
        Student student = new Student();

        student.setFirstname("sabo");
        student.setLastname("zhang");
        student.setHisFather("bigsabo");
        student.setAge(99);
        Student addedStu = studentRepository.save(student);
        assertEquals("Student(id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99)", addedStu.toString());
    }
}
