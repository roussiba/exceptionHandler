package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student student = new Student(
                    "Alex",
                    LocalDate.of(2000, Month.AUGUST, 20),
                    20,
                    "alex.first@gmail.com"
            );
            Student student1 = new Student(
                    "Mariam",
                    LocalDate.of(2000, Month.AUGUST, 20),
                    20,
                    "mariam.second@gmail.com"
            );

            studentRepository.saveAll(List.of(student, student1));
        };
    }
}

