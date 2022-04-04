package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
    	Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email is taken");
        }
        studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentID, String name, String email) {
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentID + " does not exist"));

        if (name != null &&
        name.length() > 0 &&
        !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email !=null &&
        email.length() > 0 &&
        !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}
