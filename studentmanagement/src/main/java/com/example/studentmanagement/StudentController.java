package com.example.studentmanagement;

import com.example.studentmanagement.Student;
import com.example.studentmanagement.service.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {

    private final StudentService service;


    public StudentController(
            StudentService service) {

        this.service = service;

    }


    // ==========================================
    // GET ALL STUDENTS
    // GET /students
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Student>>
    getAllStudents() {

        return ResponseEntity.ok(
                service.getAllStudents()
        );

    }


    // ==========================================
    // GET ONE STUDENT
    // GET /students/{roll}
    // ==========================================

    @GetMapping("/{roll}")
    public ResponseEntity<?> getStudent(
            @PathVariable int roll) {

        Student student =
                service.getStudentByRoll(roll);


        if (student == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            "Student with roll " +
                                    roll +
                                    " not found."
                    );

        }


        return ResponseEntity.ok(student);

    }


    // ==========================================
    // ADD STUDENT
    // POST /students
    // ==========================================

    @PostMapping
    public ResponseEntity<?> addStudent(
            @RequestBody Student student) {


        // Backend validation

        if (student.getRoll() <= 0) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Roll number must be greater than 0."
                    );

        }


        if (
                student.getName() == null ||
                        student.getName().trim().isEmpty()
        ) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Name cannot be empty."
                    );

        }


        if (
                student.getBranch() == null ||
                        student.getBranch().trim().isEmpty()
        ) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Branch cannot be empty."
                    );

        }


        // Check duplicate roll

        Student existing =
                service.getStudentByRoll(
                        student.getRoll()
                );


        if (existing != null) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            "Student with roll " +
                                    student.getRoll() +
                                    " already exists."
                    );

        }


        student.setName(
                student.getName().trim()
        );

        student.setBranch(
                student.getBranch().trim()
        );


        service.addStudent(student);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(student);
    }


    // ==========================================
    // UPDATE STUDENT
    // PUT /students/{roll}
    // ==========================================

    @PutMapping("/{roll}")
    public ResponseEntity<?> updateStudent(
            @PathVariable int roll,
            @RequestBody Student student) {


        // Backend validation

        if (student.getName() == null ||
                student.getName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Name cannot be empty."
                    );

        }


        if (student.getBranch() == null ||
                student.getBranch().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Branch cannot be empty."
                    );

        }


        // Check student exists

        Student existing =
                service.getStudentByRoll(roll);


        if (existing == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            "Student with roll " +
                                    roll +
                                    " not found."
                    );

        }


        student.setName(
                student.getName().trim()
        );

        student.setBranch(
                student.getBranch().trim()
        );


        service.updateStudent(
                roll,
                student
        );


        // Return updated student

        Student updated =
                service.getStudentByRoll(roll);


        return ResponseEntity.ok(updated);
    }


    // ==========================================
    // DELETE STUDENT
    // DELETE /students/{roll}
    // ==========================================

    @DeleteMapping("/{roll}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable int roll) {


        Student existing =
                service.getStudentByRoll(roll);


        if (existing == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            "Student with roll " +
                                    roll +
                                    " not found."
                    );

        }


        service.deleteStudent(roll);


        return ResponseEntity.ok(
                "Student deleted successfully."
        );
    }
}