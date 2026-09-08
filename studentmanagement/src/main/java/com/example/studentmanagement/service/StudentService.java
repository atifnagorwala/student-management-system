package com.example.studentmanagement.service;

import com.example.studentmanagement.Student;
import com.example.studentmanagement.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;


    public StudentService(
            StudentRepository repository) {

        this.repository = repository;

    }


    // ==========================================
    // GET ALL
    // ==========================================

    public List<Student> getAllStudents() {

        return repository.getAllStudents();

    }


    // ==========================================
    // GET ONE
    // ==========================================

    public Student getStudentByRoll(int roll) {

        return repository.getStudentByRoll(roll);

    }


    // ==========================================
    // ADD
    // ==========================================

    public int addStudent(Student student) {

        return repository.addStudent(student);

    }


    // ==========================================
    // UPDATE
    // ==========================================

    public int updateStudent(
            int roll,
            Student student) {

        return repository.updateStudent(
                roll,
                student
        );

    }


    // ==========================================
    // DELETE
    // ==========================================

    public int deleteStudent(int roll) {

        return repository.deleteStudent(roll);

    }
}