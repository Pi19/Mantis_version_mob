package com.mantis.project.mantisbt.Class;

import java.util.Date;

import lombok.Data;

@Data
public class Attachment {

    public int id;
    public Reporter reporter;
    public Date created_at;
    public String filename;
    public int size;
    public String content_type;
}
