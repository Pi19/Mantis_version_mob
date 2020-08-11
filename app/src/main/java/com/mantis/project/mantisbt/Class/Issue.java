package com.mantis.project.mantisbt.Class;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Issue {

    private String id;
    private String summary;
    private String description;
    private Project project;
    private Reporter reporter;
    private Reproductibility reproductibility;
    private Severity severity;
    private Priority priority;
    private Status status;
    private Resolution resolution ;
    public String updated_at;
    private List<Note> notes ;
    private List<Attachment> attachments;

}
