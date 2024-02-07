package com.example.demo.student;

import com.example.demo.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        studentRepository.save(studentOptional.get());
    }

    public void deleteStudent(Long studentId){
        boolean exists = studentRepository.existsById(studentId);
        if (exists){
            throw new IllegalStateException("student with id "+studentId+" does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email){
        Student updateStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("student with id "+studentId+" does not exists")
        );
        if(name != null && name.length() > 0 && !Objects.equals(updateStudent.getName(), name)){
            updateStudent.setName(name);
        }

        if (!Objects.isNull(email) && email.length()>0 && !Objects.equals(updateStudent.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new ApiRequestException("Email Taken");
            }
            updateStudent.setEmail(email);
        }

        studentRepository.save(updateStudent);
    }
}
