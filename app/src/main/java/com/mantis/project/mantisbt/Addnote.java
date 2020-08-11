package com.mantis.project.mantisbt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mantis.project.mantisbt.controller.VolleySingleton;
import com.mantis.project.mantisbt.fragment.List_issue;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Addnote extends AppCompatActivity {

    Button button_retour ;
    Button button_addnote;
    TextView note_text ;
    String issues_id ;
    String dt_issues_id;
    String listeTonote;
    String detailsTonote;
    private Request request;
    private JsonObjectRequest requestAddNote;
    public static final String IP = "ip" ;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        Intent i = getIntent();
        issues_id = i.getStringExtra("issues_id");//donnée qui vient de la liste des bogues
        listeTonote = i.getStringExtra("listeTonote");//donnée qui vient de la liste des bogues
        detailsTonote = i.getStringExtra("detailsTonote");//donnée qui vient du details
        dt_issues_id = i.getStringExtra("dt_issues_id");//donnée qui vient du details



    button_retour =  findViewById(R.id.retour_liste);
    button_addnote = findViewById(R.id.ajouter_note);

    button_retour.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(listeTonote!=null) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("noteTobogue", "R.id.bogue");
                v.getContext().startActivity(i);
            }
            else{
                Intent i = new Intent(v.getContext(), Detail_issue.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("detailsTonote", "note");
                i.putExtra("issues_id",dt_issues_id);
                v.getContext().startActivity(i);

            }

           // i.putExtra("back", List_issue.class);

            // Now start your activity
           // startActivity(i);
        }
    });
    button_addnote.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            if(listeTonote !=null){
                note_text = findViewById(R.id.note_text);
                sharedPreferences =  getBaseContext().getSharedPreferences(IP,0);
                String ip = sharedPreferences.getString("IPaddr","");
                RequestAddNote("http://"+ip+"/mantisbt/api/rest/issues/"+issues_id+"/notes");
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("noteTobogue","R.id.bogue");
                v.getContext().startActivity(i);
            }
            if(detailsTonote != null){
                note_text = findViewById(R.id.note_text);
                sharedPreferences =  getBaseContext().getSharedPreferences(IP,0);
                String ip = sharedPreferences.getString("IPaddr","");
                RequestAddNote("http://"+ip+"/mantisbt/api/rest/issues/"+dt_issues_id+"/notes");

                Intent i = new Intent(v.getContext(), Detail_issue.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("issues_id",dt_issues_id);
                v.getContext().startActivity(i);

            }



        }
    });


    }
    private void RequestAddNote(String url){

        JSONObject object_note = new JSONObject();
        try{


            object_note.put("text",note_text.getText());

            JSONObject object_view_state = new JSONObject();
            object_view_state.put("name","public");

            object_note.put("view_state",object_view_state);



        }
        catch (Exception e){
            e.printStackTrace();
        }
        requestAddNote = new JsonObjectRequest(Request.Method.POST, url, object_note, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Context context = getApplicationContext();
                CharSequence text = "Note bien enregistrée!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                 note_text.setText("");
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "44esDHRctQ-Jr-Yrt0jGpvJa6EHJ_Gw6");
                return params;
            }
        };

        if(requestAddNote == null){
            Log.d("MANTIS","REQUEST");
        }
        else VolleySingleton.getInstance().addToRequestQueue(requestAddNote);


    }
}
