package com.example.studentmanagement.repository;

import com.example.studentmanagement.Student;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;


    public StudentRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;

    }


    // ==========================================
    // GET ALL STUDENTS
    // ==========================================

    public List<Student> getAllStudents() {

        String sql =
                "SELECT roll, name, branch FROM students";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    Student student =
                            new Student();

                    student.setRoll(
                            rs.getInt("roll")
                    );

                    student.setName(
                            rs.getString("name")
                    );

                    student.setBranch(
                            rs.getString("branch")
                    );

                    return student;
                }
        );
    }


    // ==========================================
    // GET ONE STUDENT
    // ==========================================

    public Student getStudentByRoll(int roll) {

        String sql =
                "SELECT roll, name, branch " +
                        "FROM students WHERE roll = ?";

        List<Student> students =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> {

                            Student student =
                                    new Student();

                            student.setRoll(
                                    rs.getInt("roll")
                            );

                            student.setName(
                                    rs.getString("name")
                            );

                            student.setBranch(
                                    rs.getString("branch")
                            );

                            return student;
                        },
                        roll
                );


        if (students.isEmpty()) {

            return null;

        }


        return students.get(0);
    }


    // ==========================================
    // ADD STUDENT
    // ==========================================

    public int addStudent(Student student) {

        String sql =
                "INSERT INTO students " +
                        "(roll, name, branch) " +
                        "VALUES (?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                student.getRoll(),
                student.getName(),
                student.getBranch()
        );
    }


    // ==========================================
    // UPDATE STUDENT
    // ==========================================

    public int updateStudent(
            int roll,
            Student student) {

        String sql =
                "UPDATE students " +
                        "SET name = ?, branch = ? " +
                        "WHERE roll = ?";

        return jdbcTemplate.update(
                sql,
                student.getName(),
                student.getBranch(),
                roll
        );
    }


    // ==========================================
    // DELETE STUDENT
    // ==========================================

    public int deleteStudent(int roll) {

        String sql =
                "DELETE FROM students " +
                        "WHERE roll = ?";

        return jdbcTemplate.update(
                sql,
                roll
        );
    }
}