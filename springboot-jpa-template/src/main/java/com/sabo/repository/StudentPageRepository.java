package com.sabo.repository;

import com.sabo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPageRepository extends JpaRepository<Student, Long> {
}
