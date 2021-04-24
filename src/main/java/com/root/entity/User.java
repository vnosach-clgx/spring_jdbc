package com.root.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class User {

    private Long id;
    private String name;
    private String surname;
    private LocalDate dob;
}
