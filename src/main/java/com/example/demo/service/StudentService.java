package com.example.demo.service;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService  {

    private final StudentRepository repository;

    public CompletableFuture<List<Student>> saveStudent(MultipartFile file){
        log.info(System.currentTimeMillis() +" Start");
        List<Student> students = parseCSVFile(file);
        List<Student> studentList = repository.saveAll(students);
        log.info(System.currentTimeMillis() +" End");

        return CompletableFuture.completedFuture(studentList);
    }

    public List<Student> parseCSVFile(final MultipartFile file){
        final List<Student> students = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream())
        )){
            while (reader.readLine() != null){
                String line = reader.readLine();
                final String[] data = line.split(",");
                Student student = new Student();
                student.setName(data[0]);
                student.setAge(Integer.parseInt(data[1]));
                student.setEmail(data[2]);
                students.add(student);
            }
            return students;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
