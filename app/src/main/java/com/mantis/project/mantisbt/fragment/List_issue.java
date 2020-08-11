package com.mantis.project.mantisbt.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mantis.project.mantisbt.Class.Issue;
import com.mantis.project.mantisbt.Class.Priority;
import com.mantis.project.mantisbt.Class.Project;
import com.mantis.project.mantisbt.Class.Status;
import com.mantis.project.mantisbt.Class.Url;
import com.mantis.project.mantisbt.R;
import com.mantis.project.mantisbt.adapter.IssueAdapter;
import com.mantis.project.mantisbt.controller.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link List_issue.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link List_issue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class List_issue extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //**************************************************


    private Request request;
    private RequestQueue requestQueue;
    private ArrayList<Issue> lstIssue;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    public static final String IP = "ip" ;
     ProgressBar simpleProgressBar ;
    SwipeRefreshLayout mSwipeRefreshLayout;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public List_issue() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment List_issue.
     */
    // TODO: Rename and change types and number of parameters
    public static List_issue newInstance(String param1, String param2) {
        List_issue fragment = new List_issue();
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
        View v = inflater.inflate(R.layout.fragment_list_issue, container, false);
        lstIssue = new ArrayList<>();
        //mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh);
        recyclerView = v.findViewById(R.id.recyclerissue);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //new LongOperation().execute(URL);
        simpleProgressBar = (ProgressBar) v.findViewById(R.id.simpleProgressBar);
        simpleProgressBar.setVisibility(View.VISIBLE);
        sharedPreferences =  getContext().getSharedPreferences(IP,0);
        String ip = sharedPreferences.getString("IPaddr","");
        jsonrequest("http://"+ip+"/mantisbt/api/rest/issues?filter_id=assigned");
        IssueAdapter iss = new IssueAdapter(lstIssue);
        recyclerView.setAdapter(iss);


//  mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//      @Override
//      public void onRefresh() {
//          sharedPreferences =  getContext().getSharedPreferences(IP,0);
//          String ip = sharedPreferences.getString("IPaddr","");
//          jsonrequest("http://"+ip+"/mantisbt/api/rest/issues?filter_id=assigned");
//          IssueAdapter iss = new IssueAdapter(lstIssue);
//          recyclerView.setAdapter(iss);
//          mSwipeRefreshLayout.setRefreshing(false);
//      }
//  });


        return v;
    }


  private void jsonrequest (String url){

     request = new StringRequest(Request.Method.GET,url,new Response.Listener<String>(){

         @Override
         public void onResponse(String response) {
             JSONObject jsonObject = null;
             JSONArray array = null ;
             simpleProgressBar.setVisibility(View.INVISIBLE);

             try {

                 jsonObject = new JSONObject(response);
                 array = jsonObject.getJSONArray("issues");

             } catch (JSONException e) {
                 e.printStackTrace();
             }

             for(int i=0;i<array.length();i++){

                 try {
                     jsonObject = array.getJSONObject(i);
                     Issue issues = new Issue();
                     issues.setSummary(jsonObject.getString("summary"));
                     issues.setUpdated_at(jsonObject.getString("updated_at"));
                     issues.setId(jsonObject.getString("id"));

                     String projet = jsonObject.getString("project");
                     JSONObject prj = new JSONObject(projet);
                     Project project = new Project(prj.getString("name"));
                     issues.setProject(project);

                     String priorité = jsonObject.getString("priority");
                     JSONObject prt = new JSONObject(priorité);
                     Priority priority = new Priority(prt.getString("label"));
                     issues.setPriority(priority);

                     String statut = jsonObject.getString("status");
                     JSONObject stt = new JSONObject(statut);
                     Status status = new Status(stt.getString("color"));
                     issues.setStatus(status);

                     lstIssue.add(issues);

                 }
                 catch (JSONException e){
                     e.printStackTrace();

                 }


             }
             
             updateUI(lstIssue);
             //Log.d( "MANTIS",response);
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {

             Context context = getContext();
             simpleProgressBar.setVisibility(View.INVISIBLE);
             CharSequence text = "Erreur Connexion!";
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
    private void updateUI(ArrayList<Issue> issue){
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        IssueAdapter issueAdapter = new IssueAdapter(issue);
        recyclerView.setAdapter(issueAdapter);
        Log.d("MANTIS","Executed");


    }
}
