package com.mantis.project.mantisbt.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mantis.project.mantisbt.Class.Note;
import com.mantis.project.mantisbt.R;
import com.mantis.project.mantisbt.viewHolder.IssueHolder;
import com.mantis.project.mantisbt.viewHolder.NoteHolder;

import java.util.List;

public class NoteAdapter extends  RecyclerView.Adapter<NoteHolder> {

    private List<Note> notes;

    public NoteAdapter (List<Note> noteList) {
        notes = noteList;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_row_item, viewGroup, false);
        //Log.d("MANTIS","Erreur noteAdapter");
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
          Note note = notes.get(i);
          noteHolder.bindContact(note);


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
