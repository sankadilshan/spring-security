package com.sankadilshan.springsecurity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    private static HashMap<Integer,Student> hashMap= new HashMap<Integer, Student>();
    static {
        Student student1= new Student(1,"Smith");
        Student student2= new Student(2,"Anna");
        Student student3= new Student(3,"Krish");

        hashMap.put(1,student1); hashMap.put(2,student2); hashMap.put(3,student3);

    }

    @GetMapping("/health")
    public ResponseEntity health(){
        return  ResponseEntity.ok("up");
    }

//    @PreAuthorize("hasRole('ADMIN'),hasAnyRole('ADMIN,STUDENT'), hasAuthority('READ'), hasAnyAuthority('READ','WRITE')")
    @GetMapping("{id}/student")
    public ResponseEntity<Student> retrieveStudent(@PathVariable("id") int id){
        return new ResponseEntity<Student>(hashMap.get(id),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity allStudents(){
        return new ResponseEntity<>(hashMap,HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity initiateStudent(@RequestBody Student student){
        return ResponseEntity.ok(student);
    }

}
