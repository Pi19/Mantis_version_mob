package com.mantis.project.mantisbt.Class;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private int id;
    private String name;

    public Project(String name) {
        this.name = name;
    }
}
