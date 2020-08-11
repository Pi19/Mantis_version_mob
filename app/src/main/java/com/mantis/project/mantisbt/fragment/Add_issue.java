package com.mantis.project.mantisbt.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mantis.project.mantisbt.Addnote;
import com.mantis.project.mantisbt.Class.Issue;
import com.mantis.project.mantisbt.Class.Project;
import com.mantis.project.mantisbt.MainActivity;
import com.mantis.project.mantisbt.R;
import com.mantis.project.mantisbt.controller.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Add_issue.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Add_issue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_issue extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Request request;
    private JsonObjectRequest requestAddIssue;
    private RequestQueue requestQueue;
    private Spinner spinProject;
    private Spinner spinReproductibity;
    private Spinner spinImpact;
    private Spinner spinPriority;
    private TextView summary;
    private TextView description;
    private Button btn_AddIssue;
    private Button btn_CancelIssue;
    public static final String IP = "ip" ;
    SharedPreferences sharedPreferences;
    ProgressBar simpleProgressBar ;


    private static Context ctx;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Add_issue() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_issue.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_issue newInstance(String param1, String param2) {
        Add_issue fragment = new Add_issue();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =inflater.inflate(R.layout.fragment_add_issue, container, false);
       ctx = v.getContext();
       spinProject = (Spinner) v.findViewById(R.id.project_name);
        summary = v.findViewById(R.id.summary_iss);
        description = v.findViewById(R.id.description);
        spinProject = (Spinner) v.findViewById(R.id.project_name);
        spinImpact = (Spinner) v.findViewById(R.id.impact_name);
        spinReproductibity = (Spinner) v.findViewById(R.id.reproducibility_label);
        spinPriority = (Spinner) v.findViewById(R.id.priorite_label);

        simpleProgressBar = (ProgressBar) v.findViewById(R.id.progress);
        simpleProgressBar.setVisibility(View.VISIBLE);

        sharedPreferences =  getContext().getSharedPreferences(IP,0);
        String ip = sharedPreferences.getString("IPaddr","");
        String URL = "http://"+ip+"/mantisbt/api/rest/projects/";
        Requestspin (URL);

        btn_AddIssue = (Button) v.findViewById(R.id.btn_addissue);
        btn_AddIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("MANTIS",summary.getText().toString());

                sharedPreferences =  getContext().getSharedPreferences(IP,0);
                String ip = sharedPreferences.getString("IPaddr","");
                RequestAddIssue("http://"+ip+"/mantisbt/api/rest/issues");
            }
        });
        btn_CancelIssue = (Button) v.findViewById(R.id.btn_cancelissue);
        btn_CancelIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Add_issue add_issue = new Add_issue();
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("issueTolist","R.id.bogue");
                v.getContext().startActivity(i);

            }
        });


       return v ;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

     private void Requestspin (String url){

        request = new StringRequest(Request.Method.GET,url,new Response.Listener<String>(){
     ArrayList<Project> proj = new ArrayList<Project>();
     ArrayList<String> proje = new ArrayList<>();
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                JSONArray array = null ;
                simpleProgressBar.setVisibility(View.INVISIBLE);

                try {

                    jsonObject = new JSONObject(response);
                    array = jsonObject.getJSONArray("projects");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<array.length();i++){

                    try {
                        jsonObject = array.getJSONObject(i);
                        Project project = new Project();
                        project.setName(jsonObject.getString("name"));
                        proj.add(project);
                        proje.add(project.getName());
                    }
                    catch (JSONException e){
                        e.printStackTrace();

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item,proje);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinProject.setAdapter(adapter);


                }

                Log.d( "MANTIS",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getContext();
                simpleProgressBar.setVisibility(View.INVISIBLE);
                CharSequence text = "Erreur connexion!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

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

    private void RequestAddIssue(String url){

        JSONObject object_issue = new JSONObject();
         try{

             Date date = new Date();
             SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd HH:mm");
             String datenow = dateFormatWithZone.format(date);

             object_issue.put("summary",summary.getText());
             object_issue.put("description",description.getText());
             object_issue.put("created_at",datenow);

             JSONObject object_severity = new JSONObject();
             object_severity.put("label",spinImpact.getSelectedItem().toString());

             JSONObject object_reproducibility = new JSONObject();
             object_reproducibility.put("label",spinReproductibity.getSelectedItem().toString());

             JSONObject object_priority = new JSONObject();
             object_priority.put("label",spinPriority.getSelectedItem().toString());

             JSONObject object_project= new JSONObject();
             object_project.put("name",spinProject.getSelectedItem().toString());

             JSONObject object_cat = new JSONObject();
             object_cat.put("id",1);



             object_issue.put("project",object_project);
             object_issue.put("priority",object_priority);
             object_issue.put("severity",object_severity);
             object_issue.put("reproducibility",object_reproducibility);
             object_issue.put("category",object_cat);




         }
         catch (Exception e){
             e.printStackTrace();
         }
        requestAddIssue = new JsonObjectRequest(Request.Method.POST, url, object_issue, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Context context = getContext();
                CharSequence text = "Ticket bien enregistrée!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                summary.setText("");
                description.setText("");
                Log.d("MANTIS",response.toString());

                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("issueTolist","R.id.bogue");
                context.startActivity(i);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getContext();
                CharSequence text = "Erreur connexion!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

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

        if(requestAddIssue == null){
            Log.d("MANTIS","REQUEST");
        }
        else VolleySingleton.getInstance().addToRequestQueue(requestAddIssue);


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
