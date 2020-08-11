package com.mantis.project.mantisbt.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mantis.project.mantisbt.Class.Note;
import com.mantis.project.mantisbt.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteHolder extends RecyclerView.ViewHolder {
    private Note notes ;
    TextView note_text;
    TextView reporter_name;
    TextView created_date;

    public NoteHolder(@NonNull View itemView) {

        super(itemView);
        note_text = itemView.findViewById(R.id.detnote_text);
        reporter_name = itemView.findViewById(R.id.detreporter_name);
        created_date = itemView.findViewById(R.id.detdate_note);


    }

    public void bindContact(Note note) {
        notes = note;
        if(notes == null){
            return;
        }
        note_text.setText(notes.getText());
        reporter_name.setText(notes.getReporter().getId());


        String inputString = notes.getCreated_at();

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            Date dateFromUser = fromUser.parse(inputString); // Parse it to the exisitng date pattern and return Date type
            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
            //System.out.println(dateMyFormat);
            created_date.setText(dateMyFormat);; // outputs : 2009-05-19

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}
