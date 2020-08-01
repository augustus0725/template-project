package com.sabo.repository;

import com.sabo.Application;
import com.sabo.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, properties="spring.main.banner-mode=off")
public class StudentRepositoryTest {
    @Autowired private StudentRepository studentRepository;

    @Test
    public void testSave() {
        Student student = new Student();

        student.setFirstname("sabo");
        student.setLastname("zhang");
        student.setHisFather("bigsabo");
        student.setAge(99);
        student.setId(2);
        Student addedStu = studentRepository.save(student);
        assertEquals("Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}", addedStu.toString());
    }

    @Test
    public void testFindById() {
        Optional<Student> student = studentRepository.findById(2L);
        assertEquals("Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}", student.get().toString());
    }

    @Test
    public void testFindByIdNot() {
        Iterable<Student> students = studentRepository.findByIdNot(2L);
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByIdIn() {
        Iterable<Student> students = studentRepository.findByIdIn(Arrays.asList(1L, 3L));
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByIdLessThan() {
        Iterable<Student> students = studentRepository.findByIdLessThan(2L);
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByIdGreaterThan() {
        Iterable<Student> students = studentRepository.findByIdGreaterThan(1L);
        assertEquals("[Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByIdAndAge() {
        Iterable<Student> students = studentRepository.findByIdAndAge(1L, 99);
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByHisFather() {
        Iterable<Student> students = studentRepository.findByHisFather("bigsabo");
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByHisFatherIgnoreCase() {
        Iterable<Student> students = studentRepository.findByHisFatherIgnoreCase("BIGSABO");
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByIdOrAge() {
        Iterable<Student> students = studentRepository.findByIdOrAge(1L, 99);
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByHisFatherLike() {
        Iterable<Student> students = studentRepository.findByHisFatherLike("b_g%");
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByHisFatherStartingWith() {
        Iterable<Student> students = studentRepository.findByHisFatherStartingWith("big");
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByHisFatherEndingWith() {
        Iterable<Student> students = studentRepository.findByHisFatherEndingWith("sabo");
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByHisFatherContaining() {
        Iterable<Student> students = studentRepository.findByHisFatherContaining("sabo");
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindyHisFatherOrderByIdDesc() {
        Iterable<Student> students = studentRepository.findByHisFatherOrderByIdDesc("bigsabo");
        assertEquals("[Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindyHisFatherOrderById() {
        Iterable<Student> students = studentRepository.findByHisFatherOrderById("bigsabo");
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}, Student{id=2, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

    @Test
    public void testFindByHisFatherNull() {
        Iterable<Student> students = studentRepository.findByHisFatherNull();
        assertEquals("[]", students.toString());
    }

    @Test
    public void testFindByHisFatherNotNullAndId() {
        Iterable<Student> students = studentRepository.findByHisFatherNotNullAndId(1L);
        assertEquals("[Student{id=1, firstname=sabo, lastname=zhang, hisFather=bigsabo, age=99}]", students.toString());
    }

}