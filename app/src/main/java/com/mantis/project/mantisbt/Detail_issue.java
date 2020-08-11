package com.mantis.project.mantisbt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mantis.project.mantisbt.Class.Issue;
import com.mantis.project.mantisbt.Class.Note;
import com.mantis.project.mantisbt.Class.Priority;
import com.mantis.project.mantisbt.Class.Project;
import com.mantis.project.mantisbt.Class.Reporter;
import com.mantis.project.mantisbt.Class.Status;
import com.mantis.project.mantisbt.adapter.NoteAdapter;
import com.mantis.project.mantisbt.controller.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Detail_issue extends AppCompatActivity {

   String issues_id ;
    private Request request;
    private RequestQueue requestQueue;
    public static final String IP = "ip" ;
    SharedPreferences sharedPreferences;

    private ArrayList<Issue>lstissue;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    TextView issue_id;
    TextView issue_desc;
    TextView issue_update;
    TextView name_projet;
    Button addNote ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_issue);

        Intent i = getIntent();
        issues_id = i.getStringExtra("issues_id");

        ArrayList<Note> lstnote = new ArrayList<Note>();
        recyclerView = findViewById(R.id.recyclernote);
        layoutManager = new LinearLayoutManager(this);
       // String ip = "196.192.38.19";

        sharedPreferences =  getBaseContext().getSharedPreferences(IP,0);
        String ip = sharedPreferences.getString("IPaddr","");
        String url = "http://"+ip+"/mantisbt/api/rest/issues/";
        String Url = url+issues_id;
        Log.d("MANTIS",Url);
        jsonrequest(Url);


        //issue_id = findViewById(R.id.id_issue);
       // issue_update = findViewById(R.id.date_maj);
        issue_desc = findViewById(R.id.dt_description);
        name_projet = findViewById(R.id.dt_projet);
        issue_desc.setMovementMethod(new ScrollingMovementMethod());

        //name_projet = findViewById(R.id.project_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        addNote = findViewById(R.id.dt_addnote);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Addnote.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("dt_issues_id",issues_id);//envoye de l'issue id vers l'activité AddNote
                i.putExtra("detailsTonote","");//
                v.getContext().startActivity(i);
            }
        });

        noteAdapter = new NoteAdapter(lstnote);
        recyclerView.setAdapter(noteAdapter);
        }

    private void jsonrequest (String URL){

        request = new StringRequest(Request.Method.GET,URL,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                JSONArray array = null ;
                Issue issues =null;
                ArrayList<Note> not = new ArrayList<Note>();

                try {

                    jsonObject = new JSONObject(response);
                    array = jsonObject.getJSONArray("issues");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<array.length();i++){

                    try {
                        jsonObject = array.getJSONObject(i);
                        issues = new Issue();
                        issues.setDescription(jsonObject.getString("description"));
                        issues.setUpdated_at(jsonObject.getString("updated_at"));
                        issues.setId(jsonObject.getString("id"));

                        String projet = jsonObject.getString("project");
                        JSONObject prj = new JSONObject(projet);
                        Project project = new Project(prj.getString("name"));
                        issues.setProject(project);



                        JSONArray notearray  = jsonObject.getJSONArray("notes");
                          for(int a=0;a<notearray.length();a++)
                          {
                              Log.d("Array ",notearray.getString(a));
                              JSONObject jsonObject1 = new JSONObject(notearray.getString(a));
                              Note note = new Note();
                              note.setText(jsonObject1.getString("text"));
                              note.setCreated_at(jsonObject1.getString("created_at"));

                              String reporter = jsonObject.getString("reporter");
                              JSONObject rpt = new JSONObject(reporter);
                              Reporter report = new Reporter(rpt.getString("id"));
                              note.setReporter(report);
                             // Log.d("TEXT",note.getText());
                              not.add(note);
                          }

                        //issues.setNotes(lstnote);

                    }
                    catch (JSONException e){
                        e.printStackTrace();

                    }


                }
                updateUI(not,issues);
                Log.d( "MANTIS",response);
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

        if(request == null){
            Log.d("MANTIS","REQUEST");
        }
        else VolleySingleton.getInstance().addToRequestQueue(request);
    }

    private void updateUI(ArrayList<Note> note,Issue issue){

       // issue_id.setText(issue.getId());
        //issue_update.setText(issue.getUpdated_at());
        issue_desc.setText(issue.getDescription());
        name_projet.setText(issue.getProject().getName());
        //name_projet.setText(issue.getProject().getName());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if(note!= null) {
            noteAdapter = new NoteAdapter(note);
        }
        recyclerView.setAdapter(noteAdapter);
        Log.d("MANTIS","UpdateUi_Executed");


    }
}
