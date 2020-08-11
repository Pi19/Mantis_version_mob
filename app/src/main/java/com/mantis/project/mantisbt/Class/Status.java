package com.mantis.project.mantisbt.Class;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    private String id ;
    private String color ;

    public Status(String color) {
        this.color = color;
    }
}
