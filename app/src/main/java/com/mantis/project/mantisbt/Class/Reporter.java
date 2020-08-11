package com.mantis.project.mantisbt.Class;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporter {
    private String id;
    private String name;
    private String real_name;
    private String email;

    public Reporter(String id) {
        this.id = id;
    }
}
