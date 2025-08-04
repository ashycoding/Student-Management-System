package com.sms.studentmanagement.controller;

import com.sms.studentmanagement.model.Student;
import com.sms.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String viewHomePage(@RequestParam(required = false) String sort,
                               Model model) {
        List<Student> students;

        if ("name".equalsIgnoreCase(sort)) {
            students = studentService.getStudentsSortedByName();
        } else if ("course".equalsIgnoreCase(sort)) {
            students = studentService.getStudentsSortedByCourse();
        } else {
            students = studentService.getAllStudents();
        }

        model.addAttribute("students", students);
        return "index";
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        long totalStudents = studentService.getTotalStudents();
        Map<String, Long> courseCounts = studentService.getStudentCountByCourse();

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("courseCounts", courseCounts);
        return "dashboard";
    }

    @GetMapping("/search")
    public String searchStudents(@RequestParam("keyword") String keyword, Model model) {
        List<Student> students = studentService.searchStudents(keyword);
        model.addAttribute("students", students);
        return "index";
    }

    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "new_student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {
        MultipartFile imageFile = student.getImageFile();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                String filePath = uploadDir + fileName;

                Path path = Paths.get(filePath);
                Files.createDirectories(path.getParent());
                Files.write(path, imageFile.getBytes());

                student.setProfilePicture("/" + filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        studentService.saveStudent(student);
        return "redirect:/";
    }

    @GetMapping("/editStudent/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "update_student";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {
        studentService.deleteStudentById(id);
        return "redirect:/";
    }
}
