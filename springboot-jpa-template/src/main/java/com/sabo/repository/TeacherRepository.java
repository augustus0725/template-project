package com.sabo.repository;

import com.sabo.entity.Teacher;
import com.sabo.entity.base.TeacherId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/7/28 15:51
 */
public interface TeacherRepository extends JpaRepository<Teacher, TeacherId> {
}
