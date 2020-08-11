package com.mantis.project.mantisbt.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mantis.project.mantisbt.Class.Issue;
import com.mantis.project.mantisbt.Class.Priority;
import com.mantis.project.mantisbt.Class.Project;
import com.mantis.project.mantisbt.Class.Status;
import com.mantis.project.mantisbt.Class.User;
import com.mantis.project.mantisbt.R;
import com.mantis.project.mantisbt.controller.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView user_id;
    TextView user_name;
    TextView user_realname;
    TextView user_email;
    private Request request;
    public static final String IP = "ip" ;
    SharedPreferences sharedPreferences;

    private OnFragmentInteractionListener mListener;

    public user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user.
     */
    // TODO: Rename and change types and number of parameters
    public static user newInstance(String param1, String param2) {
        user fragment = new user();
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
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        sharedPreferences =  getContext().getSharedPreferences(IP,0);
        String ip = sharedPreferences.getString("IPaddr","");
        String URL = "http://"+ip+"/mantisbt/api/rest/users/me";

        jsonrequest(URL);
        user_id = v.findViewById(R.id.user_id);
        user_name= v.findViewById(R.id.user_name);
        user_email = v.findViewById(R.id.user_email);
        user_realname = v.findViewById(R.id.user_realname);



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
    private void jsonrequest (String URL){

        request = new StringRequest(Request.Method.GET,URL,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                User user = null;
                try {
                        jsonObject = new JSONObject(response);
                        user = new User(jsonObject.getString("id"),jsonObject.getString("name"),
                                jsonObject.getString("real_name"),jsonObject.getString("email"));


                } catch (JSONException e) {
                    Log.d("MANTIS", e.getMessage());
                }
                if( user != null){
                    updateUI(user);
                }
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

    private void updateUI(User user){

        user_id.setText(user.getId());
        user_name.setText(user.getName());
        user_realname.setText(user.getReal_name());
        user_email.setText(user.getEmail());

    }
}
