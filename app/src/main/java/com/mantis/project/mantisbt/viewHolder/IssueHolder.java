package com.mantis.project.mantisbt.viewHolder;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.*;
import android.widget.Button;
import android.widget.TextView;

import com.mantis.project.mantisbt.Addnote;
import com.mantis.project.mantisbt.Detail_issue;
import com.mantis.project.mantisbt.R;

import com.mantis.project.mantisbt.Class.Issue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueHolder extends RecyclerView.ViewHolder {

   TextView project_name;
   TextView summary;
   TextView date_update;
   TextView id;
   TextView status ;
   TextView priority;
   Button buttonNote;
   Button buttonDetails;

   private Issue issues ;

    public IssueHolder(@NonNull View itemView) {
        super(itemView);
        project_name =  itemView.findViewById(R.id.project_name);
        summary =  itemView.findViewById(R.id.summary);
        date_update =  itemView.findViewById(R.id.date_maj);
        id = itemView.findViewById(R.id.issueid);
        priority = itemView.findViewById(R.id.priorite_label);
        status = itemView.findViewById(R.id.status);
        buttonNote = itemView.findViewById(R.id.addnote);
        buttonDetails = itemView.findViewById(R.id.description_issue);

        buttonNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Addnote.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("issues_id",""+issues.getId());
                i.putExtra("listeTonote","");
                v.getContext().startActivity(i);
            }
        });

         buttonDetails.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(v.getContext(), Detail_issue.class);
                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 i.putExtra("issues_id",""+issues.getId());
                 v.getContext().startActivity(i);
             }
         });





    }

    public void bindContact(Issue issue)  {
        issues =issue;
        if(issues == null){
            return;
        }
        project_name.setText(issues.getProject().getName());
        summary.setText(issues.getSummary());

        String inputString = issues.getUpdated_at();

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            Date dateFromUser = fromUser.parse(inputString); // Parse it to the exisitng date pattern and return Date type
            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
            date_update.setText(dateMyFormat);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String id1 = "00"+issues.getId() ;
        id.setText(id1);
        priority.setText(issues.getPriority().getLabel());
        status.setBackgroundColor(Color.parseColor(issues.getStatus().getColor()));



    }
}
