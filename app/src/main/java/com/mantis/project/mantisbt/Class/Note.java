package com.mantis.project.mantisbt.Class;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note  {
    public int id;
    public Reporter reporter;
    public String text;
    public ViewState view_state;
    public String type;
    public String created_at;
    public String updated_at;

}
