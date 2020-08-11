package com.mantis.project.mantisbt.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mantis.project.mantisbt.Class.Issue;
import com.mantis.project.mantisbt.R;
import com.mantis.project.mantisbt.viewHolder.IssueHolder;

import java.text.ParseException;
import java.util.List;

public class IssueAdapter extends  RecyclerView.Adapter<IssueHolder> {

    private List<Issue> issues;

    public IssueAdapter(List<Issue> issuesList) {
        issues = issuesList;
    }


    @Override
    public IssueHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.issue_row_item, parent, false);
       /* Log.d("MANTIS","ISSUE ADAPTER");*/
        return new IssueHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueHolder issueHolder, int i) {
        Issue issue = issues.get(i);
        issueHolder.bindContact(issue);

    }

    @Override
    public int getItemCount() {
        return issues.size();
    }
}
