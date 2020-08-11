package com.mantis.project.mantisbt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button authentifier ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       authentifier = (Button)  findViewById(R.id.authentifier);
       authentifier.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(v.getContext(), MainActivity.class);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               v.getContext().startActivity(i);
           }
       });



    }
}
