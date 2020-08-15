package com.sabo.repository;

import com.sabo.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
    Iterable<Student> findByIdLessThan(long id);
    Iterable<Student> findByIdGreaterThan(long id);
    Iterable<Student> findByIdAndAge(long id, int age);
    Iterable<Student> findByIdOrAge(long id, int age);
    Iterable<Student> findByIdNot(long id);
    Iterable<Student> findByIdIn(Collection<Long> ids);
    Iterable<Student> findByHisFather(String hisFather);
    Iterable<Student> findByHisFatherIgnoreCase(String hisFather);
    Iterable<Student> findByHisFatherLike(String hisFather);
    Iterable<Student> findByHisFatherStartingWith(String hisFather);
    Iterable<Student> findByHisFatherEndingWith(String hisFather);
    Iterable<Student> findByHisFatherContaining(String hisFather);
    Iterable<Student> findByHisFatherOrderByIdDesc(String hisFather);
    Iterable<Student> findByHisFatherOrderById(String hisFather);
    Iterable<Student> findByHisFatherNull();
    Iterable<Student> findByHisFatherNotNullAndId(long id);


    @Modifying
    @Query(nativeQuery = true, value = "update student set firstname = :firstname where id = :id")
    int updateByIdWithFirstname(@Param("id") long id, @Param("firstname") String firstname);
}
