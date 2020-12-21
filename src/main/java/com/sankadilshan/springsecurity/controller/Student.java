package com.sankadilshan.springsecurity.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;

@Data
@AllArgsConstructor
public class Student {
    @Id
    private int id;
    private String name;
}
