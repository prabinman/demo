package com.example.demo.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //@Query("select s from student s where s.email = ?1")
    Optional<Student>findStudentByEmail(String email);

}
