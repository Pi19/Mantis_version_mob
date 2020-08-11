package com.mantis.project.mantisbt.Class;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Priority {

    private int id;
    private String name;
    private String label;

    public Priority(String label) {
        this.label = label;
    }
}
