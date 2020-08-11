package com.mantis.project.mantisbt.Class;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public  class Url  {

     public    String ip ;
     public Url(String ip) {
          this.ip = ip;
     }
}

