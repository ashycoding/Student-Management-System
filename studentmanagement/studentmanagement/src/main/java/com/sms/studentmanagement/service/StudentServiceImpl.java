package com.sms.studentmanagement.service;

import com.sms.studentmanagement.model.Student;
import com.sms.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(long id) {
        Optional<Student> optional = studentRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deleteStudentById(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        return studentRepository.findByNameContainingIgnoreCaseOrCourseContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Student> getStudentsSortedByName() {
        return studentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> getStudentsSortedByCourse() {
        return studentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Student::getCourse, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalStudents() {
        return studentRepository.count();
    }

    @Override
    public Map<String, Long> getStudentCountByCourse() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));
    }
}
