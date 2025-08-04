package com.sms.studentmanagement.service;

import com.sms.studentmanagement.model.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<Student> getAllStudents();

    void saveStudent(Student student);

    Student getStudentById(long id);

    void deleteStudentById(long id);

    List<Student> searchStudents(String keyword);

    List<Student> getStudentsSortedByName();

    List<Student> getStudentsSortedByCourse();

    long getTotalStudents();

    Map<String, Long> getStudentCountByCourse();
}
