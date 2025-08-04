package com.sms.studentmanagement.repository;

import com.sms.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContainingIgnoreCaseOrCourseContainingIgnoreCase(String name, String course);
}
